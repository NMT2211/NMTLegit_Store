// DonHangController.java
package com.shop.Controller;

import com.jpa.Entity.ChiTietDonHangEntity;
import com.jpa.Entity.DonHangEntity;
import com.jpa.Entity.GioHangEntity;
import com.jpa.Entity.NguoiDungEntity;
import com.jpa.Service.ChiTietDonHangService;
import com.jpa.Service.DanhMucService;
import com.jpa.Service.DonHangService;
import com.jpa.Service.GioHangService;
import com.jpa.Service.NguoiDungService;
import com.jpa.Service.SanPhamService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/don-hang")
public class DonHangController {

    @Autowired
    private DonHangService donHangService;

    @Autowired
    private ChiTietDonHangService chiTietDonHangService;

    @Autowired
    private GioHangService gioHangService;

    @Autowired
    private NguoiDungService nguoiDungService;
    
	@Autowired
    private DanhMucService danhMucService;
	@Autowired
	private SanPhamService sanPhamService;
	

    // Đặt hàng
    @PostMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("manguoidung");
        if (userId == null) {
            return "redirect:/login"; // Kiểm tra đăng nhập
        }

        NguoiDungEntity user = nguoiDungService.findById(userId).orElse(null);
        if (user == null) {
            model.addAttribute("error", "Người dùng không tồn tại.");
            return "redirect:/gio-hang";
        }

        // Lấy giỏ hàng
        List<GioHangEntity> cartItems = gioHangService.getCartByUser(userId);

        if (cartItems.isEmpty()) {
            model.addAttribute("error", "Giỏ hàng của bạn đang trống.");
            return "redirect:/gio-hang";
        }

        BigDecimal tongTien = cartItems.stream()
            .map(item -> item.getMaSanPham().getGiaKhuyenMai().multiply(BigDecimal.valueOf(item.getSoLuong())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Tạo đơn hàng
        DonHangEntity donHang = DonHangEntity.builder()
            .nguoiDung(user)
            .tongTien(tongTien)
            .phuongThucThanhToan("TheTinDung")
            .trangThai("ChoXuLy")
            .ngayDatHang(new Date())
            .build();

        DonHangEntity savedDonHang = donHangService.save(donHang);

        // Tạo chi tiết đơn hàng
        cartItems.forEach(item -> {
            ChiTietDonHangEntity chiTiet = ChiTietDonHangEntity.builder()
                .donHang(savedDonHang)
                .sanPham(item.getMaSanPham())
                .soLuong(item.getSoLuong())
                .Gia(item.getMaSanPham().getGiaKhuyenMai())
                .build();

            chiTietDonHangService.save(chiTiet);
        });

        // Xoá giỏ hàng sau khi đặt hàng thành công
        gioHangService.clearCart(userId);

        model.addAttribute("success", "Bạn đã đặt hàng thành công!");
        return "redirect:/don-hang/my-orders";
    }

    // Liệt kê đơn hàng đã đặt
    @GetMapping("/my-orders")
    public String listOrders(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("manguoidung");
        if (userId == null) {
            return "redirect:/login";
        }
        

        if (userId != null) {
            int cartCount = gioHangService.countProductsInCart(userId);
            model.addAttribute("cartCount", cartCount);
        } else {
            model.addAttribute("cartCount", 0);
        }

        List<DonHangEntity> orders = donHangService.getDonHangByNguoiDung(userId);
        model.addAttribute("orders", orders);
        model.addAttribute("products", sanPhamService.getAllSanPham());
        model.addAttribute("categories", danhMucService.findAll());

        return "shop/my-orders";
    }

    // Xem chi tiết đơn hàng
    @GetMapping("/detail/{id}")
    public String orderDetail(@PathVariable("id") Integer id, Model model,HttpSession session) {
        DonHangEntity donHang = donHangService.getDonHangById(id).orElse(null);
        List<ChiTietDonHangEntity> chiTiet = chiTietDonHangService.getChiTietByDonHang(id);

        Integer userId = (Integer) session.getAttribute("manguoidung");

        if (userId != null) {
            int cartCount = gioHangService.countProductsInCart(userId);
            model.addAttribute("cartCount", cartCount);
        } else {
            model.addAttribute("cartCount", 0);
        }
        model.addAttribute("donHang", donHang);
        model.addAttribute("chiTiet", chiTiet);
        model.addAttribute("products", sanPhamService.getAllSanPham());
        model.addAttribute("categories", danhMucService.findAll());

        return "shop/order-detail";
    }
}

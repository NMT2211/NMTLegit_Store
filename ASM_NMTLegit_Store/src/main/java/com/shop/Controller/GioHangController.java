package com.shop.Controller;

import com.jpa.Entity.GioHangEntity;
import com.jpa.Entity.NguoiDungEntity;
import com.jpa.Entity.SanPhamEntity;
import com.jpa.Service.DanhMucService;
import com.jpa.Service.GioHangService;
import com.jpa.Service.NguoiDungService;
import com.jpa.Service.SanPhamService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/gio-hang")
public class GioHangController {

    @Autowired
    private GioHangService gioHangService;
	@Autowired
    private DanhMucService danhMucService;
	@Autowired
	private SanPhamService sanPhamService;
	  @Autowired
	    private NguoiDungService nguoiDungService;
    /**
     * Hiển thị giỏ hàng của người dùng từ session
     */
    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        // Lấy userId từ session
        Integer userId = (Integer) session.getAttribute("manguoidung");
        if (userId == null) {
            return "redirect:/login"; // Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập
        }
        if (userId != null) {
            int cartCount = gioHangService.countProductsInCart(userId);
            model.addAttribute("cartCount", cartCount);
        } else {
            model.addAttribute("cartCount", 0);
        }

        List<GioHangEntity> cartItems = gioHangService.getCartByUser(userId);
        BigDecimal cartTotal = cartItems.stream()
                .map(item -> item.getMaSanPham().getGiaKhuyenMai()
                        .multiply(BigDecimal.valueOf(item.getSoLuong())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("cart", cartItems);
        model.addAttribute("cartTotal", cartTotal);
        model.addAttribute("products", sanPhamService.getAllSanPham());
        model.addAttribute("categories", danhMucService.findAll());
        return "shop/giohang";
    }
    
    
    @GetMapping("/count")
    public ResponseEntity<Map<String, Integer>> getCartCount(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("manguoidung");
        int count = (userId != null) ? gioHangService.countProductsInCart(userId) : 0;

        // Tạo JSON response
        Map<String, Integer> response = new HashMap<>();
        response.put("count", count);

        return ResponseEntity.ok(response);
    }

    
    
    @PostMapping("/add")
    @ResponseBody // Trả về JSON thay vì chuyển hướng trang
    public Map<String, String> addToCart(HttpSession session, 
                                         @RequestParam("productId") String productId,
                                         @RequestParam(value = "quantity", defaultValue = "1") Integer quantity) {
        Map<String, String> response = new HashMap<>();

        // Lấy mã người dùng từ session
        Integer userId = (Integer) session.getAttribute("manguoidung");
        if (userId == null) {
            response.put("status", "error");
            response.put("message", "Vui lòng đăng nhập trước khi thêm vào giỏ hàng.");
            return response;
        }

        // Tìm thông tin người dùng
        NguoiDungEntity nguoiDung = nguoiDungService.findById(userId)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));

        // Tìm sản phẩm theo productId
        SanPhamEntity sanPham = sanPhamService.getSanPhamById(productId);
        if (sanPham == null) {
            response.put("status", "error");
            response.put("message", "Sản phẩm không tồn tại.");
            return response;
        }

        // Kiểm tra sản phẩm đã có trong giỏ hàng chưa
        List<GioHangEntity> cartItems = gioHangService.getCartByUser(userId);
        Optional<GioHangEntity> existingCartItem = cartItems.stream()
                .filter(item -> item.getMaSanPham().getMaSanPham().equals(productId))
                .findFirst();

        if (existingCartItem.isPresent()) {
            // Nếu sản phẩm đã có trong giỏ hàng, cập nhật số lượng
            GioHangEntity cartItem = existingCartItem.get();
            cartItem.setSoLuong(cartItem.getSoLuong() + quantity);
            gioHangService.updateQuantity(cartItem.getMaGioHang(), cartItem.getSoLuong());
            response.put("message", "Cập nhật số lượng sản phẩm trong giỏ hàng thành công!");
        } else {
            // Nếu sản phẩm chưa có trong giỏ hàng, tạo mới
            GioHangEntity newCartItem = GioHangEntity.builder()
                    .MaNguoiDung(nguoiDung)
                    .MaSanPham(sanPham)
                    .SoLuong(quantity)
                    .build();
            gioHangService.addToCart(newCartItem);
            response.put("message", "Sản phẩm đã được thêm vào giỏ hàng!");
        }

        response.put("status", "success");
        return response;
    }




    

    /**
     * Cập nhật số lượng sản phẩm trong giỏ hàng
     */
    @PostMapping("/update")
    public String updateQuantity(HttpSession session,
                                 @RequestParam("cartId") Integer cartId,
                                 @RequestParam("quantity") Integer quantity) {
        Integer userId = (Integer) session.getAttribute("manguoidung");
        if (userId == null) {
            return "redirect:/login"; // Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập
        }

        gioHangService.updateQuantity(cartId, quantity);
        return "redirect:/gio-hang";
    }

    /**
     * Xóa sản phẩm khỏi giỏ hàng
     */
    @PostMapping("/remove")
    public String removeFromCart(HttpSession session,
                                 @RequestParam("cartId") Integer cartId) {
        Integer userId = (Integer) session.getAttribute("manguoidung");
        if (userId == null) {
            return "redirect:/login";
        }

        gioHangService.removeFromCart(cartId);
        return "redirect:/gio-hang";
    }

    /**
     * Xóa toàn bộ giỏ hàng của người dùng
     */
    @PostMapping("/clear")
    public String clearCart(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("manguoidung");
        if (userId == null) {
            return "redirect:/login";
        }

        gioHangService.clearCart(userId);
        return "redirect:/gio-hang";
    }
}

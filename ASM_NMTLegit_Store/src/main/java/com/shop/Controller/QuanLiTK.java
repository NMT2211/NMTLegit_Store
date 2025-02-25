package com.shop.Controller;

import com.jpa.Entity.NguoiDungEntity;
import com.jpa.Service.DanhMucService;
import com.jpa.Service.GioHangService;
import com.jpa.Service.NguoiDungService;
import com.jpa.Service.SanPhamService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/quan-li-tai-khoan")
public class QuanLiTK {

    @Autowired
    private NguoiDungService nguoiDungService;
    @Autowired
    private DanhMucService danhMucService;
	@Autowired
	private SanPhamService sanPhamService;
	@Autowired
    private GioHangService gioHangService;

    @GetMapping
    public String getAccountPage(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("manguoidung");
        
        if (userId == null) {
            return "redirect:/login";
        }

        nguoiDungService.findById(userId).ifPresent(user -> model.addAttribute("user", user));
        


        if (userId != null) {
            int cartCount = gioHangService.countProductsInCart(userId);
            model.addAttribute("cartCount", cartCount);
        } else {
            model.addAttribute("cartCount", 0);
        }

        
        model.addAttribute("categories", danhMucService.findAll());

        return "/shop/quanlitk";
    }

    @PostMapping("/cap-nhat")
    public String updateAccount(NguoiDungEntity updatedUser, HttpSession session,  RedirectAttributes redirectAttributes) {
        Integer userId = (Integer) session.getAttribute("manguoidung");
        
        if (userId == null) {
            return "redirect:/login";
        }

        nguoiDungService.findById(userId).ifPresent(user -> {
            user.setHoTen(updatedUser.getHoTen());
            user.setSoDienThoai(updatedUser.getSoDienThoai());
            user.setDiaChi(updatedUser.getDiaChi());
            user.setGioiTinh(updatedUser.getGioiTinh());

            nguoiDungService.save(user);
        });
        
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thông tin thành công!");

        return "redirect:/quan-li-tai-khoan";
    }
}
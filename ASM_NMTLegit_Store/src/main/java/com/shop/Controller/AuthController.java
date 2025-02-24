package com.shop.Controller;

import com.jpa.Entity.NguoiDungEntity;
import com.jpa.Service.NguoiDungService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Controller
public class AuthController {

    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("isLogin", true);
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("isLogin", false);
        return "auth/login";
    }

    @PostMapping("/auth/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes redirectAttributes) {
        Optional<NguoiDungEntity> optionalUser = nguoiDungService.findByUsername(username);
        
        if (!optionalUser.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Tài khoản không tồn tại");
            return "redirect:/login";
        }
        
        NguoiDungEntity existingUser = optionalUser.get();
        System.out.println("Mật khẩu nhập vào: " + password);
        System.out.println("Mật khẩu trong DB: " + existingUser.getMatKhau());
        System.out.println("Kết quả kiểm tra BCrypt: " + passwordEncoder.matches(password, existingUser.getMatKhau()));
        
        
        if (!passwordEncoder.matches(password, existingUser.getMatKhau())) {
            redirectAttributes.addFlashAttribute("error", "Sai mật khẩu");
            return "redirect:/login";
        }
        
        session.setAttribute("username", existingUser.getUsername());
        session.setAttribute("manguoidung", existingUser.getMaNguoiDung());
        session.setAttribute("role", existingUser.getVaiTro());
        session.setAttribute("gt", existingUser.getGioiTinh());
        
        System.out.println("Giới tính set vào session: " + existingUser.getGioiTinh());
        
        String previousUrl = (String) session.getAttribute("previousUrl");
        if (previousUrl != null) {
            session.removeAttribute("previousUrl"); // Xóa để tránh redirect lại lần sau
            return "redirect:" + previousUrl;
        }
        
        return "redirect:/NMTLegit_Store/index";
    }

    @PostMapping("/auth/user-register")
    public String registerUser(@RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes) {
        if (nguoiDungService.existsByUsername(username)) {
            redirectAttributes.addFlashAttribute("error", "Tên đăng nhập đã tồn tại");
            return "redirect:/register";
        }

        // Mã hóa mật khẩu trước khi lưu vào database
        String encodedPassword = passwordEncoder.encode(password);

        NguoiDungEntity newUser = new NguoiDungEntity();
        newUser.setUsername(username);
        newUser.setMatKhau(encodedPassword);
        newUser.setVaiTro("KhachHang");
        newUser.setTrangThai("HoatDong");
        nguoiDungService.save(newUser);

        redirectAttributes.addFlashAttribute("success", "Đăng ký thành công! Hãy đăng nhập.");
        return "redirect:/login";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate(); // Xóa toàn bộ session
        redirectAttributes.addFlashAttribute("success", "Bạn đã đăng xuất thành công!");
        return "redirect:/login";
    }
}

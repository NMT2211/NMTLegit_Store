package com.admin.Controller;

import com.jpa.Entity.NguoiDungEntity;
import com.jpa.Service.NguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/admin/users")
public class NguoiDungController {

    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping
    public String viewUsers(Model model) {
        model.addAttribute("users", nguoiDungService.findAll()); // Danh sách người dùng
        model.addAttribute("nguoiDung", new NguoiDungEntity()); // Để thêm mới
        return "admin/quanLy/nguoiDungAdmin"; // Trả về trang quản lý người dùng
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("nguoiDung") NguoiDungEntity nguoiDung) {
        // Kiểm tra nếu email hoặc username đã tồn tại
        if (nguoiDungService.existsByEmail(nguoiDung.getEmail())) {
            return "redirect:/admin/users?error=email";
        }
        if (nguoiDungService.existsByUsername(nguoiDung.getUsername())) {
            return "redirect:/admin/users?error=username";
        }
        
        // Mã hóa mật khẩu trước khi lưu
        nguoiDung.setMatKhau(passwordEncoder.encode(nguoiDung.getMatKhau()));
        
        nguoiDungService.save(nguoiDung);
        return "redirect:/admin/users";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("nguoiDung") NguoiDungEntity nguoiDung) {
        

        
        
        
        
        Optional<NguoiDungEntity> existingUser = nguoiDungService.findById(nguoiDung.getMaNguoiDung());
        if (existingUser.isPresent()) {
            NguoiDungEntity userToUpdate = existingUser.get();

            // Cập nhật thông tin, giữ nguyên mật khẩu nếu không nhập mới
            userToUpdate.setHoTen(nguoiDung.getHoTen());
            userToUpdate.setEmail(nguoiDung.getEmail());
            userToUpdate.setUsername(nguoiDung.getUsername());
            userToUpdate.setGioiTinh(nguoiDung.getGioiTinh());
            userToUpdate.setSoDienThoai(nguoiDung.getSoDienThoai());
            userToUpdate.setDiaChi(nguoiDung.getDiaChi());
            userToUpdate.setVaiTro(nguoiDung.getVaiTro());
            userToUpdate.setTrangThai(nguoiDung.getTrangThai());

            if (nguoiDung.getMatKhau() != null && !nguoiDung.getMatKhau().isEmpty()) {
                userToUpdate.setMatKhau(passwordEncoder.encode(nguoiDung.getMatKhau()));
            }

            nguoiDungService.save(userToUpdate);
        }

        return "redirect:/admin/users";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Integer id, Model model) {
        Optional<NguoiDungEntity> user = nguoiDungService.findById(id);
        user.ifPresent(nguoiDung -> model.addAttribute("nguoiDung", nguoiDung));
        model.addAttribute("users", nguoiDungService.findAll()); // Hiển thị lại danh sách
        return "admin/quanLy/nguoiDungAdmin";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Integer id) {
        nguoiDungService.deleteById(id);
        return "redirect:/admin/users";
    }
}

package com.admin.Controller;

import com.jpa.Entity.NguoiDungEntity;
import com.jpa.Service.NguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    // Danh sách người dùng với phân trang + tìm kiếm
    @GetMapping
    public String viewUsers(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            @RequestParam(value = "search", required = false) String search,
                            @RequestParam(value = "role", required = false) String role,
                            @RequestParam(value = "tab", defaultValue = "list-tab-pane") String activeTab,
                            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<NguoiDungEntity> users;

        if (search != null && !search.isEmpty()) {
            users = nguoiDungService.findByUsernameOrEmail(search, pageable);
        } else if (role != null && !role.isEmpty()) {
            users = nguoiDungService.findByVaiTro(role, pageable);
        } else {
            users = nguoiDungService.findAll(pageable);
        }

        model.addAttribute("users", users);
        model.addAttribute("nguoiDung", new NguoiDungEntity());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("searchKeyword", search);
        model.addAttribute("selectedRole", role);
        model.addAttribute("activeTab", activeTab);

        return "admin/quanLy/nguoiDungAdmin";
    }


    // Thêm mới người dùng
    @PostMapping("/save")
    public String saveUser(@ModelAttribute("nguoiDung") NguoiDungEntity nguoiDung) {
        if (nguoiDungService.existsByEmail(nguoiDung.getEmail())) {
            return "redirect:/admin/users?error=email";
        }
        if (nguoiDungService.existsByUsername(nguoiDung.getUsername())) {
            return "redirect:/admin/users?error=username";
        }

        nguoiDung.setMatKhau(passwordEncoder.encode(nguoiDung.getMatKhau()));
        nguoiDungService.save(nguoiDung);
        return "redirect:/admin/users";
    }

    // Cập nhật người dùng
    @PostMapping("/update")
    public String updateUser(@ModelAttribute("nguoiDung") NguoiDungEntity nguoiDung) {
        Optional<NguoiDungEntity> existingUser = nguoiDungService.findById(nguoiDung.getMaNguoiDung());

        if (existingUser.isPresent()) {
            NguoiDungEntity userToUpdate = existingUser.get();
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

    // Sửa thông tin người dùng
    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Integer id, Model model) {
        Optional<NguoiDungEntity> user = nguoiDungService.findById(id);
        user.ifPresent(nguoiDung -> model.addAttribute("nguoiDung", nguoiDung));
        model.addAttribute("users", nguoiDungService.findAll(PageRequest.of(0, 10))); // Phân trang mặc định
        return "admin/quanLy/nguoiDungAdmin";
    }

    // Xóa người dùng
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Integer id) {
        nguoiDungService.deleteById(id);
        return "redirect:/admin/users";
    }
    

}

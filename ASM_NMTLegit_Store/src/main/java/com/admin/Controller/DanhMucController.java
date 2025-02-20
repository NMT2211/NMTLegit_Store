package com.admin.Controller;

import com.jpa.Entity.DanhMucEntity;
import com.jpa.Service.DanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@Controller
@RequestMapping("/admin/category")
public class DanhMucController {

    @Autowired
    private DanhMucService danhMucService;
    
    

    private static final String IMAGE_DIR = "src/main/resources/static/images/categories/";

    @GetMapping
    public String viewCategories(Model model) {
        model.addAttribute("categories", danhMucService.findAll());
        model.addAttribute("danhMuc", new DanhMucEntity()); // Để thêm mới
        return "admin/quanLy/danhMucAdmin"; // Trả về view Thymeleaf
    }

    @PostMapping("/save")
    public String saveCategory(@ModelAttribute("danhMuc") DanhMucEntity danhMuc, 
                               @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            // Lấy thông tin danh mục cũ nếu đang chỉnh sửa
            DanhMucEntity existingCategory = danhMucService.findById(danhMuc.getMaDanhMuc()).orElse(null);

            if (imageFile != null && !imageFile.isEmpty()) {
                // Nếu có ảnh mới, lưu ảnh vào thư mục
                String fileName = imageFile.getOriginalFilename();
                Path filePath = Paths.get(IMAGE_DIR + fileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                
                // Lưu tên file mới vào danh mục
                danhMuc.setHinhAnh(fileName);
            } else if (existingCategory != null) {
                // Nếu không chọn ảnh mới, giữ nguyên ảnh cũ
                danhMuc.setHinhAnh(existingCategory.getHinhAnh());
            }

            // Lưu danh mục vào database
            danhMucService.save(danhMuc);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/admin/category";
    }

    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable String id, Model model) {
        model.addAttribute("danhMuc", danhMucService.findById(id).orElse(new DanhMucEntity()));
        model.addAttribute("categories", danhMucService.findAll());
        return "admin/quanLy/danhMucAdmin";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable String id) {
        danhMucService.deleteById(id);
        return "redirect:/admin/category";
    }
}

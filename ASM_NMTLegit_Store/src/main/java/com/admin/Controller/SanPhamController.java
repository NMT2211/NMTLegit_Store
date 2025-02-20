package com.admin.Controller;

import com.jpa.Entity.HinhAnhSanPhamEntity;
import com.jpa.Entity.SanPhamEntity;
import com.jpa.Service.DanhMucService;
import com.jpa.Service.HinhAnhSanPhamService;
import com.jpa.Service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;

@Controller
@RequestMapping("/admin/product")
public class SanPhamController {

    @Autowired
    private SanPhamService sanPhamService;
    
    @Autowired
    private DanhMucService danhMucService; // Thêm service danh mục
    
    @Autowired
    private HinhAnhSanPhamService hinhAnhSanPhamService; // Thêm service danh mục

    private static final String IMAGE_DIR = "src/main/resources/static/images/products/";

    @GetMapping
    public String viewProducts(Model model) {
        model.addAttribute("products", sanPhamService.getAllSanPham());
        model.addAttribute("sanPham", new SanPhamEntity()); // Để thêm mới
        model.addAttribute("categories", danhMucService.findAll());
        return "admin/quanLy/sanPhamAdmin";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("sanPham") SanPhamEntity sanPham,
                              @RequestParam(value = "imageFiles", required = false) MultipartFile[] imageFiles) {
        try {
            // Kiểm tra nếu sản phẩm đã tồn tại
            SanPhamEntity existingProduct = sanPhamService.getSanPhamById(sanPham.getMaSanPham());
            
            if (existingProduct != null) {
                sanPham.setHinhAnhs(existingProduct.getHinhAnhs()); // Giữ nguyên ảnh cũ
            }

            SanPhamEntity savedProduct = sanPhamService.saveSanPham(sanPham);

            // Kiểm tra nếu có ảnh mới tải lên
            if (imageFiles != null && imageFiles.length > 0) {
                for (MultipartFile imageFile : imageFiles) {
                    if (!imageFile.isEmpty()) {
                        String fileName = imageFile.getOriginalFilename();
                        Path filePath = Paths.get(IMAGE_DIR + fileName);
                        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                        // Lưu ảnh mới vào DB mà không xóa ảnh cũ
                        HinhAnhSanPhamEntity hinhAnh = HinhAnhSanPhamEntity.builder()
                                .sanPham(savedProduct)
                                .duongDanHinh(fileName)
                                .build();
                        hinhAnhSanPhamService.saveHinhAnh(hinhAnh);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/product";
    }



    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable String id, Model model) {
        model.addAttribute("sanPham", sanPhamService.getSanPhamById(id));
        model.addAttribute("products", sanPhamService.getAllSanPham());
        model.addAttribute("categories", danhMucService.findAll());
        return "admin/quanLy/sanPhamAdmin";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable String id) {
        sanPhamService.deleteSanPham(id);
        return "redirect:/admin/product";
    }
}

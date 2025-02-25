package com.admin.Controller;

import com.jpa.Entity.HinhAnhSanPhamEntity;
import com.jpa.Entity.SanPhamEntity;
import com.jpa.Service.DanhMucService;
import com.jpa.Service.HinhAnhSanPhamService;
import com.jpa.Service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.*;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/admin/product")
public class SanPhamController {

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private DanhMucService danhMucService;

    @Autowired
    private HinhAnhSanPhamService hinhAnhSanPhamService;

    private static final String IMAGE_DIR = "src/main/resources/static/images/products/";


  

    @GetMapping
    public String viewProducts(@RequestParam(value = "categoryId", required = false) String categoryId,
                               @RequestParam(value = "search", required = false) String search,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "20") int size,
                               @RequestParam(value = "tab", defaultValue = "list-tab-pane") String activeTab,
                               Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<SanPhamEntity> products;

        if (search != null && !search.isEmpty()) {
            products = sanPhamService.searchSanPhamByKeyword(search, pageable); // Tìm trên toàn bộ dữ liệu
        } else if (categoryId != null && !categoryId.isEmpty()) {
            products = sanPhamService.getSanPhamByCategory(categoryId, pageable);
        } else {
            products = sanPhamService.getAllSanPham(pageable);
        }

        model.addAttribute("products", products);
        model.addAttribute("sanPham", new SanPhamEntity());
        model.addAttribute("categories", danhMucService.findAll());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("selectedCategory", categoryId);
        model.addAttribute("searchKeyword", search);
        model.addAttribute("activeTab", activeTab);

        return "admin/quanLy/sanPhamAdmin";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("sanPham") SanPhamEntity sanPham,
                              @RequestParam(value = "imageFiles", required = false) MultipartFile[] imageFiles) {
        try {
            SanPhamEntity existingProduct = sanPhamService.getSanPhamById(sanPham.getMaSanPham());

            if (existingProduct != null) {
                sanPham.setHinhAnhs(existingProduct.getHinhAnhs());
            }

            SanPhamEntity savedProduct = sanPhamService.saveSanPham(sanPham);

            if (imageFiles != null && imageFiles.length > 0) {
                for (MultipartFile imageFile : imageFiles) {
                    if (!imageFile.isEmpty()) {
                        String fileName = imageFile.getOriginalFilename();
                        Path filePath = Paths.get(IMAGE_DIR + fileName);
                        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

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
    
    @GetMapping("/filter")
    @ResponseBody
    public List<SanPhamEntity> filterProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String sortByStock) {

        List<SanPhamEntity> products;

        if (category != null && !category.isEmpty()) {
            products = sanPhamService.getSanPhamByCategory(category);
        } else {
            products = sanPhamService.getAllSanPham();
        }

        if ("asc".equals(sortByStock)) {
            products.sort(Comparator.comparing(SanPhamEntity::getTonKho)); // Sắp xếp tăng dần
        } else if ("desc".equals(sortByStock)) {
            products.sort(Comparator.comparing(SanPhamEntity::getTonKho).reversed()); // Sắp xếp giảm dần
        }

        return products;
    }

}

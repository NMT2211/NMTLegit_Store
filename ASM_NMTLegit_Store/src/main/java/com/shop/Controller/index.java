package com.shop.Controller;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.jpa.Entity.DanhMucEntity;
import com.jpa.Entity.SanPhamEntity;
import com.jpa.Service.DanhMucService;
import com.jpa.Service.GioHangService;
import com.jpa.Service.HinhAnhSanPhamService;
import com.jpa.Service.SanPhamService;

import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("/NMTLegit_Store")
public class index {
	@Autowired
    private DanhMucService danhMucService;
	@Autowired
	private SanPhamService sanPhamService;
	@Autowired
    private HinhAnhSanPhamService hinhAnhSanPhamService;
	@Autowired
    private GioHangService gioHangService;
	
	@RequestMapping("/index")
    public String getShopPage(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("manguoidung");

        if (userId != null) {
            int cartCount = gioHangService.countProductsInCart(userId);
            model.addAttribute("cartCount", cartCount);
        } else {
            model.addAttribute("cartCount", 0);
        }

        
        
        // Lấy danh sách sản phẩm và danh mục từ database
        
        model.addAttribute("products", sanPhamService.getAllSanPham());
        model.addAttribute("bestSellers", sanPhamService.getSanPhamBanChay());
        model.addAttribute("categories", danhMucService.findAll());
        
        List<DanhMucEntity> categories = danhMucService.findAll();
        model.addAttribute("categories", categories);

        System.out.println("==== Danh sách danh mục ====");
        for (DanhMucEntity category : categories) {
            System.out.println("Danh mục: " + category.getTenDanhMuc());
        }

        // Lấy sản phẩm theo từng danh mục và in ra
        Map<DanhMucEntity, List<SanPhamEntity>> productsByCategory = new LinkedHashMap<>();
        System.out.println("==== Sản phẩm theo từng danh mục ====");
        for (DanhMucEntity category : categories) {
            List<SanPhamEntity> products = sanPhamService.getSanPhamByCategory(category.getMaDanhMuc());
            productsByCategory.put(category, products);

            System.out.println("Danh mục: " + category.getTenDanhMuc());
            for (SanPhamEntity product : products) {
                System.out.println("  - Sản phẩm: " + product.getTenSanPham() 
                    + ", Giá: " + product.getGia());
            }
        }
        model.addAttribute("productsByCategory", productsByCategory);

        return "/shop/index";
    }
    
    
    
    @RequestMapping("/chitietsp/{id}")
    public String getProductDetail(@PathVariable("id") String id, Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("manguoidung");

        if (userId != null) {
            int cartCount = gioHangService.countProductsInCart(userId);
            model.addAttribute("cartCount", cartCount);
        } else {
            model.addAttribute("cartCount", 0);
        }

        // Lấy thông tin sản phẩm
        
        model.addAttribute("product", sanPhamService.getSanPhamById(id));
        try {
            // Gọi service để lấy sản phẩm, nếu không có sẽ ném lỗi
            SanPhamEntity product = sanPhamService.getSanPhamById(id);
            model.addAttribute("product", product);

            // Lấy danh sách sản phẩm liên quan (cùng danh mục, nhưng loại trừ sản phẩm hiện tại)
            List<SanPhamEntity> relatedProducts = sanPhamService.getSanPhamByCategory(product.getDanhMuc().getMaDanhMuc())
                    .stream()
                    .filter(p -> !p.getMaSanPham().equals(id)) // Loại trừ chính sản phẩm đang xem
                    .limit(6) // Giới hạn số sản phẩm liên quan
                    .toList();

            model.addAttribute("relatedProducts", relatedProducts);
        } catch (RuntimeException e) {
            return "redirect:/NMTLegit_Store/index"; // Nếu không tìm thấy sản phẩm, chuyển về trang chủ
        }
        model.addAttribute("categories", danhMucService.findAll());
        return "/shop/chitietsp";
    }

    
    @RequestMapping("/category/{id}")
    public String getProductsByCategory(@PathVariable("id") String maDanhMuc, Model model,  HttpSession session) {
    	Integer userId = (Integer) session.getAttribute("manguoidung");

        if (userId != null) {
            int cartCount = gioHangService.countProductsInCart(userId);
            model.addAttribute("cartCount", cartCount);
        } else {
            model.addAttribute("cartCount", 0);
        }
       
        model.addAttribute("products", sanPhamService.getSanPhamByCategory(maDanhMuc));
        model.addAttribute("categories", danhMucService.findAll());
        Optional<DanhMucEntity> categoryOpt = danhMucService.findById(maDanhMuc);
        if (categoryOpt.isPresent()) {
            model.addAttribute("category", categoryOpt.get());
        }         
        return "/shop/category";
    }
    @RequestMapping("/filter")
    public String filterProductsByPrice(
            @RequestParam("minPrice") BigDecimal minPrice,
            @RequestParam("maxPrice") BigDecimal maxPrice,
            @RequestParam("categoryId") String categoryId,
            Model model) {

        // Lá»�c sáº£n pháº©m theo danh má»¥c vĂ  khoáº£ng giĂ¡
        List<SanPhamEntity> filteredProducts = sanPhamService.getSanPhamByCategoryAndPrice(categoryId, minPrice, maxPrice);
        
        model.addAttribute("products", filteredProducts);
        model.addAttribute("categories", danhMucService.findAll());

        // Láº¥y thĂ´ng tin danh má»¥c Ä‘á»ƒ hiá»ƒn thá»‹
        Optional<DanhMucEntity> categoryOpt = danhMucService.findById(categoryId);
        categoryOpt.ifPresent(category -> model.addAttribute("category", category));

        return "/shop/category";
    }
    @RequestMapping("/searchByCategory")
    public String searchProductsByCategoryAndName(
            @RequestParam("searchTerm") String searchTerm,
            @RequestParam("categoryId") String categoryId,
            Model model) {
        
        List<SanPhamEntity> searchResults = sanPhamService.getSanPhamByCategoryAndName(categoryId, searchTerm);
        model.addAttribute("products", searchResults);
        model.addAttribute("categories", danhMucService.findAll());
        
        Optional<DanhMucEntity> categoryOpt = danhMucService.findById(categoryId);
        categoryOpt.ifPresent(category -> model.addAttribute("category", category));
        
        model.addAttribute("searchTerm", searchTerm);
        
        return "/shop/category";
    }

    
    @RequestMapping("/search")
    public String searchProducts(@RequestParam("keyword") String keyword, Model model, HttpSession session) {

        Integer userId = (Integer) session.getAttribute("manguoidung");

        if (userId != null) {
            int cartCount = gioHangService.countProductsInCart(userId);
            model.addAttribute("cartCount", cartCount);
        } else {
            model.addAttribute("cartCount", 0);
        }

        List<SanPhamEntity> searchResults = sanPhamService.searchSanPhamByKeyword(keyword);

        model.addAttribute("products", searchResults);
        model.addAttribute("categories", danhMucService.findAll());
        model.addAttribute("searchTerm", keyword);

        return "/shop/searchResults";
    }
    
    @RequestMapping("/filter/sp")
    public String filterProducts(
        @RequestParam(value = "categoryId", required = false) String categoryId,
        
        @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
        @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
        @RequestParam(value = "sortBy", required = false, defaultValue = "default") String sortBy,
        Model model, HttpSession session) {

        Integer userId = (Integer) session.getAttribute("manguoidung");
        if (userId != null) {
            int cartCount = gioHangService.countProductsInCart(userId);
            model.addAttribute("cartCount", cartCount);
        } else {
            model.addAttribute("cartCount", 0);
        }

        // Xử lý lọc sản phẩm
        List<SanPhamEntity> filteredProducts = sanPhamService.filterSanPham(categoryId, minPrice, maxPrice, sortBy);

        model.addAttribute("products", filteredProducts);
        model.addAttribute("categories", danhMucService.findAll());

        // Trả lại thông tin filter để hiển thị lại trên giao diện
        model.addAttribute("selectedCategory", categoryId);
       
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("sortBy", sortBy);

        return "/shop/searchResults";
    }
    
   



}
package com.shop.Controller;

import java.math.BigDecimal;
import java.util.List;
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
        model.addAttribute("categories", danhMucService.findAll());

        return "/shop/index";
    }
    
    @RequestMapping("/chitietsp/{id}")
    public String getProductDetail(@PathVariable("id") String id, Model model,  HttpSession session) {
    	
    	Integer userId = (Integer) session.getAttribute("manguoidung");

        if (userId != null) {
            int cartCount = gioHangService.countProductsInCart(userId);
            model.addAttribute("cartCount", cartCount);
        } else {
            model.addAttribute("cartCount", 0);
        }
        model.addAttribute("product", sanPhamService.getSanPhamById(id));
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


}
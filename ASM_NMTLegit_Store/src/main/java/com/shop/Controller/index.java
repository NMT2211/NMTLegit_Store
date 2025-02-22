package com.shop.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.jpa.Entity.DanhMucEntity;
import com.jpa.Service.DanhMucService;
import com.jpa.Service.HinhAnhSanPhamService;
import com.jpa.Service.SanPhamService;



@Controller
@RequestMapping("/NMTLegit_Store")
public class index {
	@Autowired
    private DanhMucService danhMucService;
	@Autowired
	private SanPhamService sanPhamService;
	@Autowired
    private HinhAnhSanPhamService hinhAnhSanPhamService;
	
	
    @RequestMapping("/index")
    public String getShopPage(Model model) {
        // Lấy danh sách danh mục từ database
    	model.addAttribute("products", sanPhamService.getAllSanPham());
        model.addAttribute("categories", danhMucService.findAll());
        return "/shop/index";
    }
    
    @RequestMapping("/chitietsp/{id}")
    public String getProductDetail(@PathVariable("id") String id, Model model) {
        model.addAttribute("product", sanPhamService.getSanPhamById(id));
        model.addAttribute("categories", danhMucService.findAll());
        return "/shop/chitietsp";
    }


}
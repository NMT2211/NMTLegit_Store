package com.shop.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.jpa.Entity.DanhMucEntity;
import com.jpa.Service.DanhMucService;
import com.jpa.Service.HinhAnhSanPhamService;
import com.jpa.Service.SanPhamService;



@Controller
public class test {
	@Autowired
    private DanhMucService danhMucService;
	@Autowired
	private SanPhamService sanPhamService;
	@Autowired
    private HinhAnhSanPhamService hinhAnhSanPhamService;
	
	
    @RequestMapping("/view/test")
    public String getShopPage(Model model) {
        // Lấy danh sách danh mục từ database
    	model.addAttribute("products", sanPhamService.getAllSanPham());
        model.addAttribute("categories", danhMucService.findAll());
        return "/shop/index";
    }
    
    @RequestMapping("/view/testhome")
    public String getS(Model model) {
        // Lấy danh sách danh mục từ database
        model.addAttribute("categories", danhMucService.findAll());
        return "/view/index";
    }
    
   

//    @RequestMapping("/lab4_bai1/save")
//    public String createSave(Model model, @ModelAttribute("staff") staffEntity staff,
//                             @RequestPart("photo_file") MultipartFile photoFile) {
//        // Xử lý file upload
//        if (!photoFile.isEmpty()) {
//        	staff.setPhoto(photoFile.getName());
//        }
//        model.addAttribute("message", "Xin chào " + "<b>"+staff.getFullname() +"<b>");
//        return "/lab4/lab4_bai1";
//    }
}
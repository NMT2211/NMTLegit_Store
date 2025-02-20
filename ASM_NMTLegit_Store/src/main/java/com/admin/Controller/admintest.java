package com.admin.Controller;

import com.jpa.Service.DanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.jpa.Entity.DanhMucEntity;

@Controller
@RequestMapping("/admin")
public class admintest {

    @Autowired
    private DanhMucService danhMucService;

    @GetMapping
    public String viewAdminDashboard(Model model) {
        model.addAttribute("categories", danhMucService.findAll());
        model.addAttribute("danhMuc", new DanhMucEntity());
        return "admin/home/index";
    }
}

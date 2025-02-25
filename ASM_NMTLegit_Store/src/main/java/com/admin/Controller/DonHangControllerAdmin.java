package com.admin.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jpa.Entity.DonHangEntity;
import com.jpa.Service.DonHangService;



@Controller
@RequestMapping("/admin/orders")
public class DonHangControllerAdmin {

    @Autowired
    private DonHangService donHangService;

    @GetMapping
    public String xemDanhSachDonHang(Model model) {
        List<DonHangEntity> orders = donHangService.getAllDonHang();
        model.addAttribute("orders", orders);
        return "admin/quanLy/donHangAdmin";
    }

    @PostMapping("/capnhat")
    public String capNhatTrangThai(@RequestParam Integer maDonHang, 
                                   @RequestParam String trangThai,
                                   RedirectAttributes redirectAttributes) {
        donHangService.updateTrangThai(maDonHang, trangThai);
        redirectAttributes.addFlashAttribute("message", "Cập nhật trạng thái thành công!");
        return "redirect:/admin/orders";
    }
}

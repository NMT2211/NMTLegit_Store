package com.admin.Controller;

import com.jpa.Service.DonHangService;
import com.jpa.Service.NguoiDungService;
import com.jpa.Service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminDashboardController {

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private DonHangService donHangService;

    @Autowired
    private NguoiDungService nguoiDungService;

    @GetMapping("/admin")
    public String dashboard(Model model) {
    	long totalProducts = sanPhamService.countAllSanPham();
        model.addAttribute("totalProducts", totalProducts);
        
        long ordersToday = donHangService.countDonHangHomNay();
        model.addAttribute("ordersToday", ordersToday);
        
        long newCustomers = nguoiDungService.countKhachHangMoiHomNay();
        model.addAttribute("newCustomers", newCustomers);
        
        long canceledOrders = donHangService.countDonHangBiHuy();
        model.addAttribute("canceledOrders", canceledOrders);
        
        return "admin/home/index";
    }
}

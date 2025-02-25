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

    // Danh sách đơn hàng có bộ lọc trạng thái
    @GetMapping
    public String xemDanhSachDonHang(
            @RequestParam(value = "trangThai", required = false) String trangThai,
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate,
            Model model) {

        List<DonHangEntity> orders;

        if ((trangThai == null || trangThai.isEmpty()) && (fromDate == null || fromDate.isEmpty()) && (toDate == null || toDate.isEmpty())) {
            orders = donHangService.getAllDonHang(); // Hiển thị tất cả nếu không có lọc
        } else if (trangThai != null && !trangThai.isEmpty() && fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) {
            orders = donHangService.getDonHangByTrangThaiAndDateRange(trangThai, fromDate, toDate);
        } else if (fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) {
            orders = donHangService.getDonHangByDateRange(fromDate, toDate);
        } else if (trangThai != null && !trangThai.isEmpty()) {
            orders = donHangService.getDonHangByTrangThai(trangThai);
        } else {
            orders = donHangService.getAllDonHang();
        }

        model.addAttribute("orders", orders);
        model.addAttribute("selectedStatus", trangThai);
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);

        return "admin/quanLy/donHangAdmin";
    }


    // Cập nhật trạng thái đơn hàng
    @PostMapping("/capnhat")
    public String capNhatTrangThai(@RequestParam Integer maDonHang, 
                                   @RequestParam String trangThai,
                                   RedirectAttributes redirectAttributes) {
        donHangService.updateTrangThai(maDonHang, trangThai);
        redirectAttributes.addFlashAttribute("message", "Cập nhật trạng thái thành công!");
        return "redirect:/admin/orders";
    }
}

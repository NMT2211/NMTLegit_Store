package com.admin.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.jpa.Service.ChiTietDonHangService;
import com.jpa.Service.DonHangService;
import com.jpa.Service.SanPhamService;

@Controller

public class ThongkeController {
	@Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private ChiTietDonHangService chiTietDonHangService;
	@RequestMapping("/thongkehangton")
    public String getInventoryPage(Model model) {
        List<Object[]> statistics = sanPhamService.getInventoryStatistics();
        
        List<Map<String, Object>> inventoryData = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        List<Double> totalValues = new ArrayList<>();
        List<Integer> itemCounts = new ArrayList<>();
        
        for (Object[] row : statistics) {
            Map<String, Object> map = new HashMap<>();
            map.put("category", row[0]);
            map.put("totalValue", row[1]);
            map.put("itemCount", row[2]);
            map.put("minPrice", row[3]);
            map.put("maxPrice", row[4]);
            map.put("avgPrice", row[5]);
            inventoryData.add(map);
            
            categories.add((String) row[0]);
            totalValues.add(((Number) row[1]).doubleValue());
            itemCounts.add(((Number) row[2]).intValue());
        }

        model.addAttribute("inventoryData", inventoryData);
        model.addAttribute("categories", categories);
        model.addAttribute("totalValues", totalValues);
        model.addAttribute("itemCounts", itemCounts);
        
        return "admin/thongke/Thongkehangton";
    }
	@RequestMapping("/thongkedoanhthu")
    public String getRevenueStatistics(Model model) {
        // Lấy dữ liệu từ DB
        List<Object[]> stats = chiTietDonHangService.getRevenueStatistics();

        // Tạo các List để đưa vào biểu đồ và bảng
        List<Map<String, Object>> revenueData = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        List<Double> totalRevenues = new ArrayList<>();
        List<Integer> totalQuantities = new ArrayList<>();

        for (Object[] row : stats) {
            String category = (String) row[0];
            Double totalRevenue = ((Number) row[1]).doubleValue();
            Integer totalQuantity = ((Number) row[2]).intValue();
            Double minPrice = ((Number) row[3]).doubleValue();
            Double maxPrice = ((Number) row[4]).doubleValue();
            Double avgPrice = ((Number) row[5]).doubleValue();

            // Đóng gói vào map cho phần bảng
            Map<String, Object> map = new HashMap<>();
            map.put("category", category);
            map.put("totalRevenue", totalRevenue);
            map.put("totalQuantity", totalQuantity);
            map.put("minPrice", minPrice);
            map.put("maxPrice", maxPrice);
            map.put("avgPrice", avgPrice);

            revenueData.add(map);

            // Dữ liệu cho biểu đồ
            categories.add(category);
            totalRevenues.add(totalRevenue);
            totalQuantities.add(totalQuantity);
        }

        // Đưa xuống model để Thymeleaf hiển thị
        model.addAttribute("revenueData", revenueData);
        model.addAttribute("categories", categories);
        model.addAttribute("totalRevenues", totalRevenues);
        model.addAttribute("totalQuantities", totalQuantities);

        return "admin/thongke/Thongkedoanhthu"; 
        // Tên file .html trong thư mục resources/templates/admin/thongke
    }
	@RequestMapping("/thongkedoanhthutheothang")
	public String getMonthlyRevenue(Model model) {
        // Lấy dữ liệu từ DB
        List<Object[]> stats = chiTietDonHangService.getMonthlyRevenueStatistics();

        // Khai báo các danh sách để đưa vào biểu đồ + bảng
        List<Integer> months = new ArrayList<>();
        List<Double> revenues = new ArrayList<>();
        List<Integer> itemCounts = new ArrayList<>();
        List<Map<String, Object>> monthlyData = new ArrayList<>();

        // Duyệt từng dòng kết quả
        for (Object[] row : stats) {
            // row[0] = month, row[1] = totalRevenue, row[2] = itemCount, ...
            Integer month = (Integer) row[0];
            Double revenue = ((Number) row[1]).doubleValue();
            Integer itemCount = ((Number) row[2]).intValue();
            Double minPrice = ((Number) row[3]).doubleValue();
            Double maxPrice = ((Number) row[4]).doubleValue();
            Double avgPrice = ((Number) row[5]).doubleValue();

            // Thêm vào danh sách vẽ biểu đồ
            months.add(month);
            revenues.add(revenue);
            itemCounts.add(itemCount);

            // Tạo Map để hiển thị chi tiết trong bảng
            Map<String, Object> map = new HashMap<>();
            map.put("month", month);
            map.put("revenue", revenue);
            map.put("itemCount", itemCount);
            map.put("minPrice", minPrice);
            map.put("maxPrice", maxPrice);
            map.put("avgPrice", avgPrice);
            monthlyData.add(map);
        }

        // Đưa xuống Model
        model.addAttribute("months", months);         // Dùng cho biểu đồ
        model.addAttribute("revenues", revenues);     // Dùng cho biểu đồ
        model.addAttribute("itemCounts", itemCounts); // Dùng cho biểu đồ
        model.addAttribute("monthlyData", monthlyData); // Dùng cho bảng

        // Trả về trang Thymeleaf
        return "admin/thongke/Thongkedoanhthuthang";
    }
	
	@RequestMapping("/thongketop10")
    public String getTop10KhachVIP(Model model) {
        // Lấy dữ liệu
        List<Object[]> topCustomers = chiTietDonHangService.findTop10Customers();

        // Tạo các list để hiển thị lên biểu đồ + bảng
        List<String> customerNames = new ArrayList<>();
        List<Double> totalMoneys = new ArrayList<>();
        List<Integer> itemCounts = new ArrayList<>();
        List<Map<String, Object>> detailList = new ArrayList<>();

        for (Object[] row : topCustomers) {
            String customerName = (String) row[0];
            Double totalMoney = ((Number) row[1]).doubleValue();
            Integer itemCount = ((Number) row[2]).intValue();
            Double minPrice = ((Number) row[3]).doubleValue();
            Double maxPrice = ((Number) row[4]).doubleValue();
            Double avgPrice = ((Number) row[5]).doubleValue();

            // Phục vụ biểu đồ
            customerNames.add(customerName);
            totalMoneys.add(totalMoney);
            itemCounts.add(itemCount);

            // Phục vụ bảng
            Map<String, Object> map = new HashMap<>();
            map.put("customerName", customerName);
            map.put("totalMoney", totalMoney);
            map.put("itemCount", itemCount);
            map.put("minPrice", minPrice);
            map.put("maxPrice", maxPrice);
            map.put("avgPrice", avgPrice);
            detailList.add(map);
        }

        // Đưa xuống model
        model.addAttribute("customerNames", customerNames);
        model.addAttribute("totalMoneys", totalMoneys);
        model.addAttribute("itemCounts", itemCounts);
        model.addAttribute("detailList", detailList);

        return "admin/thongke/Thongketop10"; // tên file .html
    }
	
	@RequestMapping("/thongketonghop")
	public String thongKeTongHop(Model model) {
	    // 1) Lấy thống kê Tồn kho
	    List<Object[]> inventoryStats = sanPhamService.getInventoryStatistics();
	    // Chỉ cần category và totalValue (bỏ qua itemCounts nếu không cần)
	    List<String> inventoryCategories = new ArrayList<>();
	    List<Double> inventoryValues = new ArrayList<>();

	    for (Object[] row : inventoryStats) {
	        String category = (String) row[0];
	        Double totalValue = ((Number) row[1]).doubleValue(); 
	        inventoryCategories.add(category);
	        inventoryValues.add(totalValue);
	    }

	    // 2) Lấy thống kê Top 10 khách VIP
	    List<Object[]> topCustomers = chiTietDonHangService.findTop10Customers();
	    List<String> topCustomerNames = new ArrayList<>();
	    List<Double> topCustomerMoney = new ArrayList<>();

	    for (Object[] row : topCustomers) {
	        String customerName = (String) row[0];
	        Double totalMoney = ((Number) row[1]).doubleValue();
	        topCustomerNames.add(customerName);
	        topCustomerMoney.add(totalMoney);
	    }

	    // 3) Lấy thống kê Doanh thu từng tháng
	    List<Object[]> monthlyStats = chiTietDonHangService.getMonthlyRevenueStatistics();
	    List<Integer> months = new ArrayList<>();
	    List<Double> monthRevenues = new ArrayList<>();

	    for (Object[] row : monthlyStats) {
	        Integer month = (Integer) row[0];
	        Double revenue = ((Number) row[1]).doubleValue();
	        months.add(month);
	        monthRevenues.add(revenue);
	    }

	    // 4) Lấy thống kê Doanh thu theo loại (giống /thongkedoanhthu)
	    List<Object[]> revenueStats = chiTietDonHangService.getRevenueStatistics();
	    List<String> revenueCategories = new ArrayList<>();
	    List<Double> revenueValues = new ArrayList<>();

	    for (Object[] row : revenueStats) {
	        String category = (String) row[0];
	        Double totalRevenue = ((Number) row[1]).doubleValue();
	        revenueCategories.add(category);
	        revenueValues.add(totalRevenue);
	    }

	    // Đưa tất cả xuống model
	    model.addAttribute("inventoryCategories", inventoryCategories);
	    model.addAttribute("inventoryValues", inventoryValues);

	    model.addAttribute("topCustomerNames", topCustomerNames);
	    model.addAttribute("topCustomerMoney", topCustomerMoney);

	    model.addAttribute("months", months);
	    model.addAttribute("monthRevenues", monthRevenues);

	    model.addAttribute("revenueCategories", revenueCategories);
	    model.addAttribute("revenueValues", revenueValues);

	    // Trả về file HTML “TongHop.html”
	    return "admin/thongke/TongHop";
	}

}

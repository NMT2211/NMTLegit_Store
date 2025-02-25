package com.jpa.Service;

import com.jpa.Entity.ChiTietDonHangEntity;
import java.util.List;

public interface ChiTietDonHangService {
    ChiTietDonHangEntity save(ChiTietDonHangEntity chiTietDonHang);
    List<ChiTietDonHangEntity> getChiTietByDonHang(Integer maDonHang);
    List<Object[]> getRevenueStatistics();
    List<Object[]> getMonthlyRevenueStatistics();
    List<Object[]> findTop10Customers();
}

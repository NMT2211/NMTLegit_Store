package com.jpa.Service;

import com.jpa.Entity.DonHangEntity;
import java.util.List;
import java.util.Optional;

public interface DonHangService {
    DonHangEntity save(DonHangEntity donHang);
    List<DonHangEntity> getAllDonHang();
    Optional<DonHangEntity> getDonHangById(Integer maDonHang);
    List<DonHangEntity> getDonHangByNguoiDung(Integer maNguoiDung);
    void updateTrangThai(Integer maDonHang, String trangThai);
    
    long countDonHangHomNay();

    long countDonHangBiHuy();
    
    List<DonHangEntity> getDonHangByTrangThai(String trangThai);
    
    List<DonHangEntity> getDonHangByDate(String orderDate);
    
    List<DonHangEntity> getDonHangByTrangThaiAndDate(String trangThai, String orderDate);
    
    List<DonHangEntity> getDonHangByDateRange(String fromDate, String toDate);
    List<DonHangEntity> getDonHangByTrangThaiAndDateRange(String trangThai, String fromDate, String toDate);


    
}
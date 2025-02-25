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
}
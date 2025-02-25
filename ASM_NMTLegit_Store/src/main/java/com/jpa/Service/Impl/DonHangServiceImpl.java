// DonHangServiceImpl.java
package com.jpa.Service.Impl;

import com.jpa.Dao.DonHangDao;
import com.jpa.Entity.DonHangEntity;

import com.jpa.Service.DonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class DonHangServiceImpl implements DonHangService {

    @Autowired
    private DonHangDao donHangDao;

    @Override
    public DonHangEntity save(DonHangEntity donHang) {
        return donHangDao.save(donHang);
    }

    @Override
    public List<DonHangEntity> getAllDonHang() {
        return donHangDao.findAll();
    }

    @Override
    public Optional<DonHangEntity> getDonHangById(Integer maDonHang) {
        return donHangDao.findById(maDonHang);
    }

    @Override
    public List<DonHangEntity> getDonHangByNguoiDung(Integer maNguoiDung) {
        return donHangDao.findByNguoiDung_MaNguoiDung(maNguoiDung);
    }

    @Override
    public void updateTrangThai(Integer maDonHang, String trangThai) {
    	donHangDao.findById(maDonHang).ifPresent(dh -> {
            dh.setTrangThai(trangThai);
            donHangDao.save(dh);
        });
    }
    
   
    @Override
    public long countDonHangHomNay() {
        return donHangDao.countDonHangHomNay();
    }
    
    @Override
    public long countDonHangBiHuy() {
        return donHangDao.countDonHangBiHuy();
    }

}


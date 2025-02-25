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
        return donHangDao.findAllByOrderByNgayDatHangDesc(); // ✅ Lấy danh sách đơn hàng theo ngày giảm dần
    }

    @Override
    public Optional<DonHangEntity> getDonHangById(Integer maDonHang) {
        return donHangDao.findById(maDonHang);
    }

    @Override
    public List<DonHangEntity> getDonHangByNguoiDung(Integer maNguoiDung) {
        return donHangDao.findByNguoiDung_MaNguoiDungOrderByNgayDatHangDesc(maNguoiDung); // ✅ Lấy đơn hàng theo người dùng và sắp xếp giảm dần
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
    
    @Override
    public List<DonHangEntity> getDonHangByTrangThai(String trangThai) {
        return donHangDao.findByTrangThaiOrderByNgayDatHangDesc(trangThai);
    }
    
    @Override
    public List<DonHangEntity> getDonHangByDate(String orderDate) {
        LocalDate date = LocalDate.parse(orderDate);
        return donHangDao.findByNgayDatHang(date);
    }

    @Override
    public List<DonHangEntity> getDonHangByTrangThaiAndDate(String trangThai, String orderDate) {
        LocalDate date = LocalDate.parse(orderDate);
        return donHangDao.findByTrangThaiAndNgayDatHang(trangThai, date);
    }
    
    @Override
    public List<DonHangEntity> getDonHangByDateRange(String fromDate, String toDate) {
        LocalDate startDate = LocalDate.parse(fromDate);
        LocalDate endDate = LocalDate.parse(toDate);
        return donHangDao.findByNgayDatHangBetween(startDate, endDate);
    }

    @Override
    public List<DonHangEntity> getDonHangByTrangThaiAndDateRange(String trangThai, String fromDate, String toDate) {
        LocalDate startDate = LocalDate.parse(fromDate);
        LocalDate endDate = LocalDate.parse(toDate);
        return donHangDao.findByTrangThaiAndNgayDatHangBetween(trangThai, startDate, endDate);
    }


}


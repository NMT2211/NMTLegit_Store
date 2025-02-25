package com.jpa.Service.Impl;


import com.jpa.Dao.ChiTietDonHangDao;
import com.jpa.Entity.ChiTietDonHangEntity;

import com.jpa.Service.ChiTietDonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChiTietDonHangServiceImpl implements ChiTietDonHangService {

    @Autowired
    private ChiTietDonHangDao chiTietDonHangDao;

    @Override
    public ChiTietDonHangEntity save(ChiTietDonHangEntity chiTietDonHang) {
        return chiTietDonHangDao.save(chiTietDonHang);
    }

    @Override
    public List<ChiTietDonHangEntity> getChiTietByDonHang(Integer maDonHang) {
        return chiTietDonHangDao.findByDonHang_MaDonHang(maDonHang);
    }
    
    @Override
    public List<Object[]> getRevenueStatistics() {
        return chiTietDonHangDao.getRevenueStatistics();
    }

    @Override
    public List<Object[]> getMonthlyRevenueStatistics() {
        return chiTietDonHangDao.getMonthlyRevenueStatistics();
    }
    public List<Object[]> findTop10Customers() {
       
         List<Object[]> results = chiTietDonHangDao.findTopCustomers();
         return results.stream().limit(10).toList();
    }
}

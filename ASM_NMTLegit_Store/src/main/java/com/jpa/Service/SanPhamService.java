package com.jpa.Service;

import com.jpa.Entity.SanPhamEntity;

import java.math.BigDecimal;
import java.util.List;

public interface SanPhamService {
    List<SanPhamEntity> getAllSanPham();
    SanPhamEntity getSanPhamById(String maSanPham);
    SanPhamEntity saveSanPham(SanPhamEntity sanPham);
    void deleteSanPham(String maSanPham);
    
    List<SanPhamEntity> getSanPhamByCategory(String maDanhMuc);
    List<SanPhamEntity> getSanPhamByCategoryAndPrice(String categoryId, BigDecimal minPrice, BigDecimal maxPrice);
    List<SanPhamEntity> getSanPhamByCategoryAndName(String categoryId, String searchTerm);
}
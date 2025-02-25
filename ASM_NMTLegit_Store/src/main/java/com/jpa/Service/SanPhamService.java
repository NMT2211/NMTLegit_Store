package com.jpa.Service;

import com.jpa.Entity.SanPhamEntity;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;




public interface SanPhamService {
    List<SanPhamEntity> getAllSanPham();
    SanPhamEntity getSanPhamById(String maSanPham);
    SanPhamEntity saveSanPham(SanPhamEntity sanPham);
    void deleteSanPham(String maSanPham);
    
    
    
    List<SanPhamEntity> getSanPhamByCategory(String maDanhMuc);
    List<SanPhamEntity> getSanPhamByCategoryAndPrice(String categoryId, BigDecimal minPrice, BigDecimal maxPrice);
    List<SanPhamEntity> getSanPhamByCategoryAndName(String categoryId, String searchTerm);
    
    public List<SanPhamEntity> searchSanPhamByKeyword(String keyword);
    List<SanPhamEntity> filterSanPham(String categoryId, BigDecimal minPrice, BigDecimal maxPrice, String sortBy);

    
    List<Object[]> getInventoryStatistics();
    long countAllSanPham();
    
    
    Page<SanPhamEntity> getAllSanPham(Pageable pageable);
    Page<SanPhamEntity> getSanPhamByCategory(String maDanhMuc, Pageable pageable);
    Page<SanPhamEntity> getSanPhamByCategoryAndPrice(String categoryId, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    Page<SanPhamEntity> getSanPhamByCategoryAndName(String categoryId, String searchTerm, Pageable pageable);
    Page<SanPhamEntity> searchSanPhamByKeyword(String keyword, Pageable pageable);
}
package com.jpa.Service.Impl;

import com.jpa.Dao.SanPhamDao;
import com.jpa.Entity.SanPhamEntity;
import com.jpa.Service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;



@Service
public class SanPhamServiceImpl implements SanPhamService {
    
    @Autowired
    private SanPhamDao sanPhamDao;

    @Override
    public List<SanPhamEntity> getAllSanPham() {
        return sanPhamDao.findAll();
    }

    @Override
    public SanPhamEntity getSanPhamById(String maSanPham) {
        return sanPhamDao.findById(maSanPham).orElse(null);
    }

    @Override
    public SanPhamEntity saveSanPham(SanPhamEntity sanPham) {
        return sanPhamDao.save(sanPham);
    }

    @Override
    public void deleteSanPham(String maSanPham) {
        sanPhamDao.deleteById(maSanPham);
    }
    
    
    
    @Override
    public List<SanPhamEntity> getSanPhamByCategory(String maDanhMuc) {
        return sanPhamDao.findByDanhMuc_MaDanhMuc(maDanhMuc);
    }
    @Override
    public List<SanPhamEntity> getSanPhamByCategoryAndPrice(String categoryId, BigDecimal minPrice, BigDecimal maxPrice) {
        return sanPhamDao.findByCategoryAndPrice(categoryId, minPrice, maxPrice);
    }
    @Override
    public List<SanPhamEntity> getSanPhamByCategoryAndName(String categoryId, String searchTerm) {
        return sanPhamDao.findByDanhMuc_MaDanhMucAndTenSanPhamContainingIgnoreCase(categoryId, searchTerm);
    }
    
    @Override
    public List<SanPhamEntity> searchSanPhamByKeyword(String keyword) {
        return sanPhamDao.findByTenSanPhamContainingIgnoreCase(keyword);
    }
    
    
    @Override
    public List<SanPhamEntity> filterSanPham(String categoryId, BigDecimal minPrice, BigDecimal maxPrice, String sortBy) {
        // Bạn có thể viết logic truy vấn động sử dụng Specification hoặc criteriaQuery
        // Ở đây dùng ví dụ đơn giản với trường hợp đơn giản:

        List<SanPhamEntity> products = sanPhamDao.findAll();

        if (categoryId != null && !categoryId.isEmpty()) {
            products = products.stream()
                .filter(p -> p.getDanhMuc().getMaDanhMuc().equals(categoryId))
                .collect(Collectors.toList());
        }

        

        if (minPrice != null) {
            products = products.stream()
                .filter(p -> p.getGiaKhuyenMai().compareTo(minPrice) >= 0)
                .collect(Collectors.toList());
        }

        if (maxPrice != null) {
            products = products.stream()
                .filter(p -> p.getGiaKhuyenMai().compareTo(maxPrice) <= 0)
                .collect(Collectors.toList());
        }

        if ("priceAsc".equals(sortBy)) {
            products.sort(Comparator.comparing(SanPhamEntity::getGiaKhuyenMai));
        } else if ("priceDesc".equals(sortBy)) {
            products.sort(Comparator.comparing(SanPhamEntity::getGiaKhuyenMai).reversed());
        }

        return products;
    }
    
    @Override
    public List<Object[]> getInventoryStatistics() {
        return sanPhamDao.getInventoryStatistics();
    }

    
    @Override
    public long countAllSanPham() {
        return sanPhamDao.count();
    }
    
    
    @Override
    public Page<SanPhamEntity> getAllSanPham(Pageable pageable) {
        return sanPhamDao.findAll(pageable);
    }

    @Override
    public Page<SanPhamEntity> getSanPhamByCategory(String maDanhMuc, Pageable pageable) {
        return sanPhamDao.findByDanhMuc_MaDanhMuc(maDanhMuc, pageable);
    }

    @Override
    public Page<SanPhamEntity> getSanPhamByCategoryAndPrice(String categoryId, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        return sanPhamDao.findByCategoryAndPrice(categoryId, minPrice, maxPrice, pageable);
    }

    @Override
    public Page<SanPhamEntity> getSanPhamByCategoryAndName(String categoryId, String searchTerm, Pageable pageable) {
        return sanPhamDao.findByDanhMuc_MaDanhMucAndTenSanPhamContainingIgnoreCase(categoryId, searchTerm, pageable);
    }

    @Override
    public Page<SanPhamEntity> searchSanPhamByKeyword(String keyword, Pageable pageable) {
        return sanPhamDao.findByTenSanPhamContainingIgnoreCase(keyword, pageable);
    }

    
   

   

}
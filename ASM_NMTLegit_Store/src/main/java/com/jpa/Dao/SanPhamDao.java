package com.jpa.Dao;

import com.jpa.Entity.SanPhamEntity;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SanPhamDao extends JpaRepository<SanPhamEntity, String> {
	
	List<SanPhamEntity> findByDanhMuc_MaDanhMuc(String maDanhMuc);
	 @Query("SELECT sp FROM SanPhamEntity sp WHERE sp.danhMuc.maDanhMuc = :categoryId AND sp.giaKhuyenMai BETWEEN :minPrice AND :maxPrice")
	 List<SanPhamEntity> findByCategoryAndPrice(@Param("categoryId") String categoryId, @Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);
	 List<SanPhamEntity> findByDanhMuc_MaDanhMucAndTenSanPhamContainingIgnoreCase(String categoryId, String searchTerm);
	 
	 List<SanPhamEntity> findByTenSanPhamContainingIgnoreCase(String keyword);
	
	 @Query("SELECT d.tenDanhMuc, SUM(sp.gia * sp.tonKho), SUM(sp.tonKho), MIN(sp.gia), MAX(sp.gia), AVG(sp.gia) " +
		       "FROM SanPhamEntity sp JOIN sp.danhMuc d GROUP BY d.tenDanhMuc")
		List<Object[]> getInventoryStatistics();
}

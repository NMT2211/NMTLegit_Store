package com.jpa.Dao;

import com.jpa.Entity.HinhAnhSanPhamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HinhAnhSanPhamDao extends JpaRepository<HinhAnhSanPhamEntity, Integer> {
}
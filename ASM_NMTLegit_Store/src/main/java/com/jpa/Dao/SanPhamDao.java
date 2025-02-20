package com.jpa.Dao;

import com.jpa.Entity.SanPhamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanPhamDao extends JpaRepository<SanPhamEntity, String> {
}

package com.jpa.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jpa.Entity.DanhMucEntity;

public interface DanhMucDao extends JpaRepository<DanhMucEntity, String> {

}

package com.jpa.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jpa.Entity.GioHangEntity;


public interface GioHangDao extends JpaRepository<GioHangEntity, Integer> {

}

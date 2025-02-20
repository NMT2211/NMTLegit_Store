package com.jpa.Dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jpa.Entity.NguoiDungEntity;


public interface NguoiDungDao extends JpaRepository<NguoiDungEntity, Integer> {

	
	Optional<NguoiDungEntity> findByUsername(String username);
    
    boolean existsByEmail(String email);
    
    boolean existsByUsername(String username);
}

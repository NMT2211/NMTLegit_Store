package com.jpa.Dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jpa.Entity.NguoiDungEntity;


public interface NguoiDungDao extends JpaRepository<NguoiDungEntity, Integer> {

	
	Optional<NguoiDungEntity> findByUsername(String username);
	
	
	Optional<NguoiDungEntity> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    boolean existsByUsername(String username);
    
    

        @Query("SELECT COUNT(nd) FROM NguoiDungEntity nd WHERE nd.ngayTao = CURRENT_DATE")
        long countKhachHangMoiHomNay();
    
}

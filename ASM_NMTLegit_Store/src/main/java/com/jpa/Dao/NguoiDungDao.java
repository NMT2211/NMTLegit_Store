package com.jpa.Dao;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jpa.Entity.NguoiDungEntity;


public interface NguoiDungDao extends JpaRepository<NguoiDungEntity, Integer> {

	
	Optional<NguoiDungEntity> findByUsername(String username);
	
	
	Optional<NguoiDungEntity> findByEmail(String email);
	Optional<NguoiDungEntity> findByResetToken(String token);
    
    boolean existsByEmail(String email);
    
    boolean existsByUsername(String username);
    
    

        @Query("SELECT COUNT(nd) FROM NguoiDungEntity nd WHERE nd.ngayTao = CURRENT_DATE")
        long countKhachHangMoiHomNay();
        
        
        @Query("SELECT u FROM NguoiDungEntity u " +
        	       "WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
        	       "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
        	       "OR LOWER(FUNCTION('unaccent', u.hoTen)) LIKE LOWER(FUNCTION('unaccent', CONCAT('%', :keyword, '%')))")
        	Page<NguoiDungEntity> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);


        
        
        
        Page<NguoiDungEntity> findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                String username, String email, Pageable pageable);
        
        Page<NguoiDungEntity> findByVaiTro(String vaiTro, Pageable pageable);
    
}

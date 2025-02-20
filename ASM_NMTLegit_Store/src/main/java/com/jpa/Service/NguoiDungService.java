package com.jpa.Service;

import com.jpa.Entity.NguoiDungEntity;
import java.util.List;
import java.util.Optional;

public interface NguoiDungService {
    
    List<NguoiDungEntity> findAll(); // Lấy tất cả người dùng
    
    Optional<NguoiDungEntity> findById(Integer id); // Tìm theo ID
    
    Optional<NguoiDungEntity> findByUsername(String username); // Tìm theo username
    
    NguoiDungEntity save(NguoiDungEntity nguoiDung); // Lưu hoặc cập nhật người dùng
    
    void deleteById(Integer id); // Xóa người dùng theo ID
    
    boolean existsByEmail(String email); // Kiểm tra email đã tồn tại hay chưa
    
    boolean existsByUsername(String username); // Kiểm tra username đã tồn tại hay chưa
}

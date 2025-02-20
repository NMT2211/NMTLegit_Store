package com.jpa.Service;

import com.jpa.Entity.DanhMucEntity;
import java.util.List;
import java.util.Optional;

public interface DanhMucService {
    List<DanhMucEntity> findAll(); // Lấy danh sách tất cả danh mục
    Optional<DanhMucEntity> findById(String maDanhMuc); // Tìm danh mục theo mã
    DanhMucEntity save(DanhMucEntity danhMuc); // Thêm hoặc cập nhật danh mục
    void deleteById(String maDanhMuc); // Xóa danh mục theo mã
}

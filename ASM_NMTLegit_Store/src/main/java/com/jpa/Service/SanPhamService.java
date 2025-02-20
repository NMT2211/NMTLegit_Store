package com.jpa.Service;

import com.jpa.Entity.SanPhamEntity;
import java.util.List;

public interface SanPhamService {
    List<SanPhamEntity> getAllSanPham();
    SanPhamEntity getSanPhamById(String maSanPham);
    SanPhamEntity saveSanPham(SanPhamEntity sanPham);
    void deleteSanPham(String maSanPham);
}
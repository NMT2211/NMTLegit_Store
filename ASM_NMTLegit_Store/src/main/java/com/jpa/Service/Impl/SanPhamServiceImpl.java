package com.jpa.Service.Impl;

import com.jpa.Dao.SanPhamDao;
import com.jpa.Entity.SanPhamEntity;
import com.jpa.Service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SanPhamServiceImpl implements SanPhamService {
    
    @Autowired
    private SanPhamDao sanPhamDao;

    @Override
    public List<SanPhamEntity> getAllSanPham() {
        return sanPhamDao.findAll();
    }

    @Override
    public SanPhamEntity getSanPhamById(String maSanPham) {
        return sanPhamDao.findById(maSanPham).orElse(null);
    }

    @Override
    public SanPhamEntity saveSanPham(SanPhamEntity sanPham) {
        return sanPhamDao.save(sanPham);
    }

    @Override
    public void deleteSanPham(String maSanPham) {
        sanPhamDao.deleteById(maSanPham);
    }
}
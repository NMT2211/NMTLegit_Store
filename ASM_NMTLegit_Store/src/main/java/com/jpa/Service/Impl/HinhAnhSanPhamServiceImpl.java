package com.jpa.Service.Impl;

import com.jpa.Dao.HinhAnhSanPhamDao;
import com.jpa.Entity.HinhAnhSanPhamEntity;
import com.jpa.Service.HinhAnhSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HinhAnhSanPhamServiceImpl implements HinhAnhSanPhamService {
    
    @Autowired
    private HinhAnhSanPhamDao hinhAnhSanPhamDao;

    @Override
    public List<HinhAnhSanPhamEntity> getAllHinhAnh() {
        return hinhAnhSanPhamDao.findAll();
    }

    @Override
    public HinhAnhSanPhamEntity getHinhAnhById(Integer maHinhAnh) {
        return hinhAnhSanPhamDao.findById(maHinhAnh).orElse(null);
    }

    @Override
    public HinhAnhSanPhamEntity saveHinhAnh(HinhAnhSanPhamEntity hinhAnh) {
        return hinhAnhSanPhamDao.save(hinhAnh);
    }

    @Override
    public void deleteHinhAnh(Integer maHinhAnh) {
        hinhAnhSanPhamDao.deleteById(maHinhAnh);
    }
}

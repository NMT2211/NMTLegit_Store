package com.jpa.Service;

import com.jpa.Entity.HinhAnhSanPhamEntity;
import java.util.List;

public interface HinhAnhSanPhamService {
    List<HinhAnhSanPhamEntity> getAllHinhAnh();
    HinhAnhSanPhamEntity getHinhAnhById(Integer maHinhAnh);
    HinhAnhSanPhamEntity saveHinhAnh(HinhAnhSanPhamEntity hinhAnh);
    void deleteHinhAnh(Integer maHinhAnh);
}

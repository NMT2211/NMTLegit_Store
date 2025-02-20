package com.jpa.Service.Impl;

import com.jpa.Dao.DanhMucDao;
import com.jpa.Entity.DanhMucEntity;
import com.jpa.Service.DanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DanhMucServiceImpl implements DanhMucService {

    @Autowired
    private DanhMucDao danhMucDao;

    @Override
    public List<DanhMucEntity> findAll() {
        return danhMucDao.findAll();
    }

    @Override
    public Optional<DanhMucEntity> findById(String maDanhMuc) {
        return danhMucDao.findById(maDanhMuc);
    }

    @Override
    public DanhMucEntity save(DanhMucEntity danhMuc) {
        return danhMucDao.save(danhMuc);
    }

    @Override
    public void deleteById(String maDanhMuc) {
        danhMucDao.deleteById(maDanhMuc);
    }
}

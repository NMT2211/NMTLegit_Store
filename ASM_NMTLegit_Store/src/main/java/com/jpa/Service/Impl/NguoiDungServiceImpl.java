package com.jpa.Service.Impl;

import com.jpa.Dao.NguoiDungDao;
import com.jpa.Entity.NguoiDungEntity;

import com.jpa.Service.NguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NguoiDungServiceImpl implements NguoiDungService {

    @Autowired
    private NguoiDungDao NguoiDungDao;

    @Override
    public List<NguoiDungEntity> findAll() {
        return NguoiDungDao.findAll();
    }

    @Override
    public Optional<NguoiDungEntity> findById(Integer id) {
        return NguoiDungDao.findById(id);
    }

    @Override
    public Optional<NguoiDungEntity> findByUsername(String username) {
        return NguoiDungDao.findByUsername(username);
    }

    @Override
    public NguoiDungEntity save(NguoiDungEntity nguoiDung) {
        return NguoiDungDao.save(nguoiDung);
    }

    @Override
    public void deleteById(Integer id) {
    	NguoiDungDao.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return NguoiDungDao.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return NguoiDungDao.existsByUsername(username);
    }
}

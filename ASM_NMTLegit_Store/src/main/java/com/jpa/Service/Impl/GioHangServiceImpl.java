package com.jpa.Service.Impl;

import com.jpa.Dao.GioHangDao;
import com.jpa.Dao.NguoiDungDao;
import com.jpa.Dao.SanPhamDao;
import com.jpa.Entity.DanhMucEntity;
import com.jpa.Entity.GioHangEntity;
import com.jpa.Entity.NguoiDungEntity;
import com.jpa.Entity.SanPhamEntity;
import com.jpa.Service.GioHangService;
import com.jpa.Service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GioHangServiceImpl implements GioHangService {

    @Autowired
    private GioHangDao gioHangDao;

    @Autowired
    private NguoiDungDao nguoiDungDao;

    @Autowired
    private SanPhamService SanPhamService;

    @Override
    public List<GioHangEntity> getCartByUser(Integer userId) {
        return gioHangDao.findAll()
                .stream()
                .filter(cart -> cart.getMaNguoiDung().getMaNguoiDung().equals(userId))
                .toList();
    }

    @Override
    public GioHangEntity addToCart(GioHangEntity gioHang) {

        return gioHangDao.save(gioHang);
    }
    
    

    @Override
    public GioHangEntity updateQuantity(Integer cartId, Integer quantity) {
        GioHangEntity cartItem = gioHangDao.findById(cartId).orElseThrow(() -> new RuntimeException("Giỏ hàng không tồn tại"));
        cartItem.setSoLuong(quantity);
        return gioHangDao.save(cartItem);
    }

    @Override
    public void removeFromCart(Integer cartId) {
        gioHangDao.deleteById(cartId);
    }

    @Override
    public void clearCart(Integer userId) {
        List<GioHangEntity> cartItems = getCartByUser(userId);
        gioHangDao.deleteAll(cartItems);
    }
    
    
    @Override
    public int countProductsInCart(Integer userId) {
        return getCartByUser(userId).stream().mapToInt(GioHangEntity::getSoLuong).sum();
    }

}

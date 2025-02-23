package com.jpa.Service;

import com.jpa.Entity.GioHangEntity;
import java.util.List;

public interface GioHangService {
    List<GioHangEntity> getCartByUser(Integer userId);
    GioHangEntity addToCart(GioHangEntity gioHang);
    GioHangEntity updateQuantity(Integer cartId, Integer quantity);
    void removeFromCart(Integer cartId);
    void clearCart(Integer userId);
    
    
    int countProductsInCart(Integer userId);

 
}

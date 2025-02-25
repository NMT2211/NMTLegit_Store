package com.jpa.Dao;


import com.jpa.Entity.ChiTietDonHangEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChiTietDonHangDao extends JpaRepository<ChiTietDonHangEntity, Integer> {
    List<ChiTietDonHangEntity> findByDonHang_MaDonHang(Integer maDonHang);
    
    @Query("SELECT sp.danhMuc.tenDanhMuc, " +
            "       SUM(ct.soLuong * ct.Gia) AS totalRevenue, " +
            "       SUM(ct.soLuong)         AS totalQuantity, " +
            "       MIN(ct.Gia)             AS minPrice, " +
            "       MAX(ct.Gia)             AS maxPrice, " +
            "       AVG(ct.Gia)             AS avgPrice " +
            "FROM ChiTietDonHangEntity ct " +
            "JOIN ct.sanPham sp " +
            "GROUP BY sp.danhMuc.tenDanhMuc")
     List<Object[]> getRevenueStatistics();
     @Query("SELECT FUNCTION('MONTH', dh.ngayDatHang) AS month, " +
             "       SUM(ct.soLuong * ct.Gia) AS totalRevenue, " +
             "       SUM(ct.soLuong)         AS itemCount, " +
             "       MIN(ct.Gia)             AS minPrice, " +
             "       MAX(ct.Gia)             AS maxPrice, " +
             "       AVG(ct.Gia)             AS avgPrice " +
             "FROM   ChiTietDonHangEntity ct " +
             "JOIN   ct.donHang dh " +
             "GROUP BY FUNCTION('MONTH', dh.ngayDatHang) " +
             "ORDER BY FUNCTION('MONTH', dh.ngayDatHang)")
      List<Object[]> getMonthlyRevenueStatistics();
      @Query("SELECT nd.hoTen AS customerName, " +
              "       SUM(ct.soLuong * ct.Gia) AS totalMoney, " +
              "       SUM(ct.soLuong)         AS itemCount, " +
              "       MIN(ct.Gia)             AS minPrice, " +
              "       MAX(ct.Gia)             AS maxPrice, " +
              "       AVG(ct.Gia)             AS avgPrice " +
              "FROM   ChiTietDonHangEntity ct " +
              "JOIN   ct.donHang dh " +
              "JOIN   dh.nguoiDung nd " +
              "GROUP BY nd.hoTen " +
              "ORDER BY SUM(ct.soLuong * ct.Gia) DESC")
       List<Object[]> findTopCustomers(); // Lấy tất cả, sẽ giới hạn sau
       
       @Query("SELECT c.sanPham, SUM(c.soLuong) as tongBan " +
               "FROM ChiTietDonHangEntity c " +
               "GROUP BY c.sanPham " +
               "ORDER BY tongBan DESC")
        List<Object[]> findBestSellingProducts();
       
 
}
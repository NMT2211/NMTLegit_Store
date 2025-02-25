package com.jpa.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "NguoiDung") // Đảm bảo tên bảng chính xác
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class NguoiDungEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY vì SQL có AUTO_INCREMENT
    @Column(name = "MaNguoiDung", updatable = false, nullable = false)
    private Integer maNguoiDung;

    @Column(name = "username", unique = true, nullable = false, length = 20)
    private String username; // ✅ Thêm username để khớp với database

    @Column(name = "HoTen", nullable = false, length = 100)
    private String hoTen;

    @Column(name = "Email", unique = true, nullable = false, length = 100)
    private String email;

    @Column(name = "MatKhau", nullable = false, length = 255)
    private String matKhau;

    @Column(name = "SoDienThoai", length = 15)
    private String soDienThoai;

    @Column(name = "DiaChi", columnDefinition = "NVARCHAR(MAX)")
    private String diaChi;

    @Column(name = "VaiTro", nullable = false, length = 50)
    @Builder.Default
    private String vaiTro = "KhachHang"; // ✅ Giá trị mặc định

    @Column(name = "TrangThai", nullable = false, length = 50)
    @Builder.Default
    private String trangThai = "HoatDong"; // ✅ Giá trị mặc định

    @Temporal(TemporalType.DATE)
    @Column(name = "NgayTao", nullable = false, updatable = false)
    @Builder.Default
    private Date ngayTao = new Date(); // ✅ Đặt mặc định là ngày hiện tại
    
    @Column(name = "GioiTinh", nullable = false)
    @Builder.Default
    private Boolean gioiTinh = true; // true = Nam, false = Nữ
    
    
    @Column(name = "ResetToken", length = 6)
    private String resetToken;

    @Column(name = "TokenExpiry")
    private Date tokenExpiry;


    @PrePersist
    protected void onCreate() {
        this.ngayTao = new Date(); // Đảm bảo ngày tạo tự động được đặt khi lưu vào DB
    }
}

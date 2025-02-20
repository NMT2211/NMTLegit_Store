package com.jpa.Entity;



import java.util.Date;
import lombok.*;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "GioHang")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GioHangEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer MaGioHang;

    @ManyToOne
    @JoinColumn(name = "MaNguoiDung", nullable = false)
    private NguoiDungEntity MaNguoiDung;

    @ManyToOne
    @JoinColumn(name = "MaSanPham", nullable = false)
    private SanPhamEntity MaSanPham;

    @Column(nullable = false)
    private Integer SoLuong;
}

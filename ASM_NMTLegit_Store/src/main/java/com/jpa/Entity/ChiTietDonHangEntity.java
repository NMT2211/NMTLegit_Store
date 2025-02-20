package com.jpa.Entity;



import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;

@Entity
@Table(name = "ChiTietDonHang")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ChiTietDonHangEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maChiTietDonHang;

    @ManyToOne
    @JoinColumn(name = "MaDonHang", nullable = false)
    private DonHangEntity donHang;

    @ManyToOne
    @JoinColumn(name = "MaSanPham", nullable = false)
    private SanPhamEntity sanPham;

    @Column(nullable = false)
    private Integer soLuong;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal Gia;
}

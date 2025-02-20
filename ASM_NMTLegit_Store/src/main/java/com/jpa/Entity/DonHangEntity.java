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
@Table(name = "DonHang")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DonHangEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maDonHang;

    @ManyToOne
    @JoinColumn(name = "MaNguoiDung", nullable = false)
    private NguoiDungEntity nguoiDung;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal tongTien;
    

    @Column(nullable = false, length = 50)
    private String phuongThucThanhToan = "TheTinDung";

    @Column(nullable = false, length = 50)
    private String trangThai = "ChoXuLy";

    @Temporal(TemporalType.DATE)
    @Column(nullable = false, updatable = false)
    private Date ngayDatHang = new Date();
}

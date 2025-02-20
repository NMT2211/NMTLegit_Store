package com.jpa.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "SanPham")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SanPhamEntity {

    @Id
    @Column(name = "MaSanPham", length = 20, nullable = false, updatable = false)
    private String maSanPham;

    @ManyToOne
    @JoinColumn(name = "MaDanhMuc", nullable = false)
    private DanhMucEntity danhMuc;

    @Column(name = "TenSanPham", nullable = false, length = 255)
    private String tenSanPham;

    @Column(name = "Gia", nullable = false)
    private BigDecimal gia;

    @Column(name = "GiaKhuyenMai")
    private BigDecimal giaKhuyenMai;

    @Column(name = "MoTaChinh", columnDefinition = "NVARCHAR(MAX)")
    private String moTaChinh;

    @Column(name = "ChiTietSanPham", columnDefinition = "NVARCHAR(MAX)")
    private String chiTietSanPham;

    @Column(name = "CauHinh", columnDefinition = "NVARCHAR(MAX)")
    private String cauHinh;

    @Column(name = "ChinhSachBaoHanh", columnDefinition = "NVARCHAR(MAX)")
    private String chinhSachBaoHanh;

    @Column(name = "CauHoiThuongGap", columnDefinition = "NVARCHAR(MAX)")
    private String cauHoiThuongGap;

    @Column(name = "TonKho", nullable = false)
    private Integer tonKho;

    @OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HinhAnhSanPhamEntity> hinhAnhs; // Liên kết với bảng HinhAnhSanPham

    @Temporal(TemporalType.DATE)
    @Column(name = "NgayTao", nullable = false, updatable = false)
    @Builder.Default
    private Date ngayTao = new Date();

    @Temporal(TemporalType.DATE)
    @Column(name = "NgayCapNhat")
    @Builder.Default
    private Date ngayCapNhat = new Date();

    @PrePersist
    protected void onCreate() {
        this.ngayTao = new Date();
        this.ngayCapNhat = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.ngayCapNhat = new Date();
    }
}

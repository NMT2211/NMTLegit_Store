package com.jpa.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "HinhAnhSanPham")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HinhAnhSanPhamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maHinhAnh;

    @ManyToOne
    @JoinColumn(name = "MaSanPham", nullable = false)
    private SanPhamEntity sanPham; 

    @Column(nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String duongDanHinh;
}

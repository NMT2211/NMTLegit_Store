package com.jpa.Entity;



import java.util.Date;
import lombok.*;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity
@Table(name = "MaGiamGia")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MaGiamGiaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer MaGiamGia;

    @Column(nullable = false, unique = true, length = 50)
    private String MaCode;

    @Column(nullable = false)
    private Integer PhanTramGiam;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date NgayHetHan;
}

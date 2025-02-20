package com.jpa.Entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "DanhMuc")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DanhMucEntity {
	@Id
    @Column(length = 20, nullable = false)
    private String maDanhMuc;  // Đổi kiểu từ Integer sang String

    @Column(nullable = false, length = 100)
    private String tenDanhMuc;

    @Column(nullable = false, unique = true, length = 100)
    private String hinhAnh;
    
    @Column(nullable = false, unique = true, length = 100)
    private String icon;
}


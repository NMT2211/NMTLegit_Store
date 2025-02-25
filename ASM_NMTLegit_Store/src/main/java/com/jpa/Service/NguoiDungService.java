package com.jpa.Service;

import com.jpa.Entity.NguoiDungEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface NguoiDungService {

    // ✅ Tạo OTP ngẫu nhiên
    String generateOTP();

    // ✅ Gửi mã OTP đặt lại mật khẩu qua email
    boolean sendPasswordResetOTP(String email);

    // ✅ Xác minh mã OTP
    boolean verifyOTP(String email, String otp);

    // ✅ Đặt lại mật khẩu mới
    boolean resetPassword(String email, String otp, String newPassword);

    // ✅ Các chức năng quản lý người dùng cơ bản
    List<NguoiDungEntity> findAll(); // Lấy tất cả người dùng

    Optional<NguoiDungEntity> findById(Integer id); // Tìm theo ID

    Optional<NguoiDungEntity> findByUsername(String username); // Tìm theo username

    Optional<NguoiDungEntity> findByEmail(String email); // Tìm theo email

    NguoiDungEntity save(NguoiDungEntity nguoiDung); // Lưu hoặc cập nhật người dùng

    void deleteById(Integer id); // Xóa người dùng theo ID

    boolean existsByEmail(String email); // Kiểm tra email đã tồn tại hay chưa

    boolean existsByUsername(String username); // Kiểm tra username đã tồn tại hay chưa

    long countKhachHangMoiHomNay(); // Đếm số khách hàng mới trong ngày

    // ✅ Phân trang danh sách người dùng
    Page<NguoiDungEntity> findAll(Pageable pageable);

    // ✅ Tìm kiếm người dùng theo từ khóa (hỗ trợ tiếng Việt không dấu)
    Page<NguoiDungEntity> findByUsernameOrEmail(String keyword, Pageable pageable);

    // ✅ Lọc người dùng theo vai trò (Quản trị / Khách hàng)
    Page<NguoiDungEntity> findByVaiTro(String vaiTro, Pageable pageable);

    // ✅ Tìm kiếm nâng cao (hỗ trợ tìm kiếm tiếng Việt có dấu và không dấu)
    Page<NguoiDungEntity> searchByKeyword(String keyword, Pageable pageable);
}

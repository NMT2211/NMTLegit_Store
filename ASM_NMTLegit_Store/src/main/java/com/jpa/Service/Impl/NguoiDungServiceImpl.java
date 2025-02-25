package com.jpa.Service.Impl;

import com.jpa.Dao.NguoiDungDao;
import com.jpa.Entity.NguoiDungEntity;
import com.jpa.Service.NguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Pattern;
import java.text.Normalizer;
import java.util.stream.Collectors;

@Service
public class NguoiDungServiceImpl implements NguoiDungService {

    @Autowired
    private NguoiDungDao nguoiDungDao;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Tạo mã OTP ngẫu nhiên (6 số) an toàn hơn bằng SecureRandom
    @Override
    public String generateOTP() {
        SecureRandom random = new SecureRandom();
        return String.format("%06d", random.nextInt(1000000));
    }

    // Gửi mã OTP qua email
    @Override
    public boolean sendPasswordResetOTP(String email) {
        Optional<NguoiDungEntity> user = nguoiDungDao.findByEmail(email);
        if (user.isPresent()) {
            String otp = generateOTP();
            Date expiryTime = Date.from(LocalDateTime.now().plusMinutes(10).atZone(ZoneId.systemDefault()).toInstant());

            NguoiDungEntity nguoiDung = user.get();
            nguoiDung.setResetToken(otp);
            nguoiDung.setTokenExpiry(expiryTime);
            nguoiDungDao.save(nguoiDung);

            // Gửi email OTP
            sendEmail(email, "Mã đặt lại mật khẩu", "Mã OTP của bạn là: " + otp + "\nMã này có hiệu lực trong 10 phút.");
            return true;
        }
        return false;
    }

    // Gửi email
    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    // Xác minh OTP
    @Override
    public boolean verifyOTP(String email, String otp) {
        Optional<NguoiDungEntity> user = nguoiDungDao.findByEmail(email);
        if (user.isPresent()) {
            NguoiDungEntity nguoiDung = user.get();
            if (nguoiDung.getResetToken() != null &&
                nguoiDung.getResetToken().equals(otp) &&
                nguoiDung.getTokenExpiry() != null &&
                nguoiDung.getTokenExpiry().after(new Date())) {
                return true;
            }
        }
        return false;
    }

    // Đặt lại mật khẩu mới (có mã hóa)
    @Override
    public boolean resetPassword(String email, String otp, String newPassword) {
        Optional<NguoiDungEntity> user = nguoiDungDao.findByEmail(email);
        if (user.isPresent()) {
            NguoiDungEntity nguoiDung = user.get();
            if (nguoiDung.getResetToken() != null &&
                nguoiDung.getResetToken().equals(otp) &&
                nguoiDung.getTokenExpiry() != null &&
                nguoiDung.getTokenExpiry().after(new Date())) {

                // Lưu mật khẩu mới (đã mã hóa từ Controller)
                nguoiDung.setMatKhau(newPassword);
                nguoiDung.setResetToken(null);
                nguoiDung.setTokenExpiry(null);
                nguoiDungDao.save(nguoiDung);

                return true;
            }
        }
        return false;
    }


    // ✅ Các hàm hỗ trợ tìm kiếm người dùng
    @Override
    public List<NguoiDungEntity> findAll() {
        return nguoiDungDao.findAll();
    }

    @Override
    public Optional<NguoiDungEntity> findById(Integer id) {
        return nguoiDungDao.findById(id);
    }

    @Override
    public Optional<NguoiDungEntity> findByUsername(String username) {
        return nguoiDungDao.findByUsername(username);
    }

    @Override
    public Optional<NguoiDungEntity> findByEmail(String email) {
        return nguoiDungDao.findByEmail(email);
    }

    @Override
    public NguoiDungEntity save(NguoiDungEntity nguoiDung) {
        return nguoiDungDao.save(nguoiDung);
    }

    @Override
    public void deleteById(Integer id) {
        nguoiDungDao.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return nguoiDungDao.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return nguoiDungDao.existsByUsername(username);
    }

    @Override
    public long countKhachHangMoiHomNay() {
        return nguoiDungDao.countKhachHangMoiHomNay();
    }

    @Override
    public Page<NguoiDungEntity> findAll(Pageable pageable) {
        return nguoiDungDao.findAll(pageable);
    }

    // ✅ Chuyển đổi tiếng Việt không dấu để tìm kiếm tốt hơn
    public static String removeAccents(String text) {
        if (text == null) return null;
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{M}");
        return pattern.matcher(normalized).replaceAll("").toLowerCase();
    }

    @Override
    public Page<NguoiDungEntity> findByUsernameOrEmail(String keyword, Pageable pageable) {
        String normalizedKeyword = removeAccents(keyword);
        List<NguoiDungEntity> allUsers = nguoiDungDao.findAll();

        List<NguoiDungEntity> filteredUsers = allUsers.stream()
                .filter(user -> removeAccents(user.getHoTen()).contains(normalizedKeyword) ||
                                removeAccents(user.getUsername()).contains(normalizedKeyword) ||
                                removeAccents(user.getEmail()).contains(normalizedKeyword))
                .collect(Collectors.toList());

        return new PageImpl<>(filteredUsers, pageable, filteredUsers.size());
    }

    @Override
    public Page<NguoiDungEntity> findByVaiTro(String vaiTro, Pageable pageable) {
        return nguoiDungDao.findByVaiTro(vaiTro, pageable);
    }

    @Override
    public Page<NguoiDungEntity> searchByKeyword(String keyword, Pageable pageable) {
        return nguoiDungDao.searchByKeyword(keyword, pageable);
    }
}

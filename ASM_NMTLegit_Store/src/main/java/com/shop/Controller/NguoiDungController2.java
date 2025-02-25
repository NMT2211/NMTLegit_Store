package com.shop.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.jpa.Service.NguoiDungService;

@RestController
@RequestMapping("/api/auth")
public class NguoiDungController2 {

    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // ✅ Gửi mã OTP đến email
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        boolean sent = nguoiDungService.sendPasswordResetOTP(email);
        if (sent) {
            return ResponseEntity.ok("Mã OTP đã được gửi đến email của bạn. Vui lòng kiểm tra hộp thư.");
        }
        return ResponseEntity.badRequest().body("Email không tồn tại trong hệ thống.");
    }

    // ✅ Xác minh OTP
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOTP(@RequestParam String email, @RequestParam String otp) {
        boolean isValid = nguoiDungService.verifyOTP(email, otp);
        if (isValid) {
            return ResponseEntity.ok("OTP hợp lệ. Bạn có thể đặt lại mật khẩu.");
        }
        return ResponseEntity.badRequest().body("OTP không hợp lệ hoặc đã hết hạn.");
    }

    // ✅ Đặt lại mật khẩu
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String email,
                                                @RequestParam String otp,
                                                @RequestParam String newPassword) {
        // Kiểm tra độ mạnh của mật khẩu trước khi đặt lại
        if (newPassword.length() < 6) {
            return ResponseEntity.badRequest().body("Mật khẩu phải có ít nhất 6 ký tự.");
        }

        boolean isReset = nguoiDungService.resetPassword(email, otp, passwordEncoder.encode(newPassword));
        if (isReset) {
            return ResponseEntity.ok("Mật khẩu đã được đặt lại thành công.");
        }
        return ResponseEntity.badRequest().body("OTP không hợp lệ hoặc đã hết hạn.");
    }
}

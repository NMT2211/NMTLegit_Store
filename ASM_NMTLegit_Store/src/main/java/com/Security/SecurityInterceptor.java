package com.Security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SecurityInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        String requestUri = request.getRequestURI();

        // ✅ Bỏ qua Interceptor cho các trang đăng nhập, đăng ký
        if (requestUri.startsWith("/auth/") || requestUri.equals("/login") || requestUri.equals("/register")) {
            return true; 
        }

        // 🛑 Kiểm tra nếu truy cập trang Admin mà không có quyền
        if (requestUri.startsWith("/admin")) {
            if (role == null || !role.equals("QuanTri")) {
                session.setAttribute("previousUrl", requestUri);
                response.sendRedirect("/403");
                return false;
            }
        }

        // 🛑 Kiểm tra nếu truy cập chức năng mua hàng mà chưa đăng nhập
        if (requestUri.startsWith("/cart") || requestUri.startsWith("/checkout")) {
            if (session.getAttribute("username") == null) {
                session.setAttribute("previousUrl", requestUri);
                
                // Không redirect lại nếu đã ở trang login
                if (!requestUri.equals("/login")) {
                    response.sendRedirect("/login?error=not_logged_in");
                    return false;
                }
            }
        }

        return true;
    }
}

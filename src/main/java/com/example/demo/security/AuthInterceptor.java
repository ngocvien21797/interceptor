package com.example.demo.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

  private boolean isAjax(HttpServletRequest req) {
    String h = req.getHeader("X-Requested-With");
    return h != null && h.equalsIgnoreCase("XMLHttpRequest");
  }

  @Override
  public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
    String uri = req.getRequestURI();

    // public
    if (uri.startsWith("/login") || "/403".equals(uri) ||
        uri.startsWith("/css") || uri.startsWith("/js") ||
        uri.startsWith("/images") || uri.startsWith("/webjars")) {
      return true;
    }

    UserSession user = (UserSession) req.getSession().getAttribute("USER");
    if (user == null) {
      if (isAjax(req)) res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      else res.sendRedirect("/login");
      return false;
    }

    // quyền theo annotation (nếu có)
    if (handler instanceof HandlerMethod hm) {
      RequireRole rr = hm.getMethodAnnotation(RequireRole.class);
      if (rr == null) rr = hm.getBeanType().getAnnotation(RequireRole.class);
      if (rr != null && !rr.value().equalsIgnoreCase(user.role())) {
        if (isAjax(req)) res.sendError(HttpServletResponse.SC_FORBIDDEN);
        else res.sendRedirect("/403");
        return false;
      }
    }

    // quyền theo URL /admin/**
    if (uri.startsWith("/admin") && !"ADMIN".equalsIgnoreCase(user.role())) {
      if (isAjax(req)) res.sendError(HttpServletResponse.SC_FORBIDDEN);
      else res.sendRedirect("/403");
      return false;
    }

    return true;
  }
}

package com.example.demo.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public class RequestTimingInterceptor implements HandlerInterceptor {
  private static final Logger log = LoggerFactory.getLogger(RequestTimingInterceptor.class);
  private static final String START = "_start";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    request.setAttribute(START, System.currentTimeMillis());
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    Object st = request.getAttribute(START);
    if (st instanceof Long start) {
      long ms = System.currentTimeMillis() - start;
      log.info("[{}] {} -> {} ({} ms)", request.getMethod(), request.getRequestURI(), response.getStatus(), ms);
    }
  }
}

package com.example.day7.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
@Slf4j
public class RequestLoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Long start = System.currentTimeMillis();

        ContentCachingRequestWrapper wReq = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wRes = new ContentCachingResponseWrapper(response);

        String traceId = UUID.randomUUID().toString().substring(0, 8);
        MDC.put("traceId", traceId);

        try {
            filterChain.doFilter(wReq, wRes);
        } finally {
            long elapsed = System.currentTimeMillis() - start;
            String reqBody = new String(wReq.getContentAsByteArray(), StandardCharsets.UTF_8);
            String resBody = new String(wRes.getContentAsByteArray(), StandardCharsets.UTF_8);

            log.info("HTTP {} {} {} status={} {}ms\nreq={}\nres={}",
                    request.getMethod(), request.getRequestURI(), MDC.get("traceId"),
                    response.getStatus(), elapsed,
                    reqBody, resBody);

            wRes.copyBodyToResponse();
            MDC.clear();
        }
    }
}

package com.example.day5.filter;

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

        long start = System.currentTimeMillis();

        // body를 두번 읽기 위해 wrapping
        ContentCachingRequestWrapper wReq = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wRes = new ContentCachingResponseWrapper(response);

        // MDC에 traceId 부여
        String traceId = UUID.randomUUID().toString().substring(0, 8);
        MDC.put("traceId", traceId);

        try {
            filterChain.doFilter(wReq, wRes);
        } finally {
            long elapsed = System.currentTimeMillis() - start;
            String reqBody = new String(wReq.getContentAsByteArray(), StandardCharsets.UTF_8);
            String resBody = new String(wRes.getContentAsByteArray(), StandardCharsets.UTF_8);

            log.info("HTTP {} {} status={} {}ms\nreq={}\nres={}",
                    request.getMethod(), request.getRequestURI(),
                    response.getStatus(), elapsed,
                    reqBody, resBody);

            wRes.copyBodyToResponse(); // 응답 본문 다시 복사 (안 하면 응답 비어버림)
            MDC.clear();
        }
    }
}

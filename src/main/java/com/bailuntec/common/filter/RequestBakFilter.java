package com.bailuntec.common.filter;

import com.bailuntec.common.wrapper.RequestBakRequestWrapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 对request请求进行包装备份请求参数
 *
 * @author robbendev
 */
@ConditionalOnWebApplication
@Component
@ServletComponentScan
@WebFilter(filterName = "requestBakFilter")
public class RequestBakFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        RequestBakRequestWrapper requestWrapper = new RequestBakRequestWrapper(servletRequest);
        chain.doFilter(requestWrapper, response);
    }

    @Override
    public void destroy() {

    }
}

package com.example.client.responseFile;

import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by imain on 2018/2/16.
 */
@Configuration
public class CorsFilter implements Filter {
//在每个方法调用前执行
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.err.println("22设置跨域请求...");
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest reqs = (HttpServletRequest) req;
        // Origin ->> http://localhost:8080，允许此URL进行资源访问
        // https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Headers/Access-Control-Allow-Origin?utm_source=mozilla&utm_medium=devtools-netmonitor&utm_campaign=default
        response.setHeader("Access-Control-Allow-Origin",reqs.getHeader("Origin")); // 这是必须的，下面都是可选项
        response.setHeader("Access-Control-Allow-Credentials", "false");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        // application/x-www-form-urlencoded, multipart/form-data 或 text/plain
        response.setHeader("Access-Control-Allow-Headers","x-requested-with,content-type");
        chain.doFilter(req, res);
    }
    public void init(FilterConfig filterConfig) {}
    public void destroy() {}
}
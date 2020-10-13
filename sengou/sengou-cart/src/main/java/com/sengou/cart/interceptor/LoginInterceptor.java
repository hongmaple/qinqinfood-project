package com.sengou.cart.interceptor;

import com.sengou.auth.entity.UserInfo;
import com.sengou.auth.utils.JwtUtils;
import com.sengou.cart.config.JwtProperties;
import com.sengou.common.utils.CookieUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    private JwtProperties jwtProperties;

    //定义一个线程域，存放登录用户
    private static final ThreadLocal<UserInfo>tl = new ThreadLocal<>();

    public LoginInterceptor(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception {
        // 从cookie中查询token
        String token = CookieUtils.getCookieValue(request,"SG_TOKEN");
        if (StringUtils.isBlank(token)){
            //未登录，返回401 401无权限
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        //有toke，查询用户信息
        try {
            //jwtProperties获取公钥，根据token和公钥获取用户信息
            UserInfo userInfo = JwtUtils.getInfoFromToken(token,jwtProperties.getPublicKey());
            //放入线程
            tl.set(userInfo);
            return true;
        }catch (Exception e){
            // 抛出异常，证明未登录,返回401
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        tl.remove();
    }

    public static UserInfo getLoginUser() {
        return tl.get();
    }

}
















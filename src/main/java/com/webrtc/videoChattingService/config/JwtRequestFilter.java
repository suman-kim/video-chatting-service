package com.webrtc.videoChattingService.config;

import com.webrtc.videoChattingService.entity.member.Member;
import com.webrtc.videoChattingService.jwt.CookieUtil;
import com.webrtc.videoChattingService.jwt.JwtUtil;
import com.webrtc.videoChattingService.jwt.RedisUtil;
import com.webrtc.videoChattingService.service.member.MyUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {


    private final MyUserDetailsService userDetailsService;

    private final JwtUtil jwtUtil;

    private final CookieUtil cookieUtil;

    private final RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        final Cookie jwtToken = cookieUtil.getCookie(httpServletRequest,JwtUtil.ACCESS_TOKEN_NAME);

        String nickName = null;
        String jwt = null;
        String refreshJwt = null;
        String refreshUname = null;

        try{

            System.out.println("jwtToken ==" + jwtToken);
            if(jwtToken != null){
                jwt = jwtToken.getValue();
                nickName = jwtUtil.getUsername(jwt);
            }
            System.out.println("jwtToke.getValue() ==" + jwt);
            System.out.println("nickName ==" + nickName);
            if(nickName != null){

                UserDetails userDetails = userDetailsService.loadUserByUsername(nickName);

                if(jwtUtil.validateToken(jwt,userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
        catch (ExpiredJwtException e){
            Cookie refreshToken = cookieUtil.getCookie(httpServletRequest,JwtUtil.REFRESH_TOKEN_NAME);
            System.out.println("refreshToken ==" + refreshToken);
            if(refreshToken != null){
                refreshJwt = refreshToken.getValue();
            }
        }catch(Exception e){
            System.out.println("오류1");
            System.out.println(e);
        }

        try{

            System.out.println("refreshJwt222 ==" + refreshJwt);
            if(refreshJwt != null){
                //refreshToken
                refreshUname = redisUtil.getData(refreshJwt);
                System.out.println(refreshJwt);
                System.out.println(refreshUname);
                System.out.println(jwtUtil.getUsername(refreshJwt));
                if(refreshUname.equals(jwtUtil.getUsername(refreshJwt))){
                    UserDetails userDetails = userDetailsService.loadUserByUsername(refreshUname);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    Member member = new Member();
                    member.setNickName(refreshUname);
                    String newToken = jwtUtil.generateToken(member);

                    Cookie newAccessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME,newToken);
                    httpServletResponse.addCookie(newAccessToken);
                }
            }
        }
        catch(ExpiredJwtException e){
            System.out.println("오류2");
            System.out.println(e);
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}


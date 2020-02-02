package com.sun.erpbackend.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.erpbackend.entity.user.User;
import com.sun.erpbackend.service.user.UserService;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	UserService userService;
	@Bean
	PasswordEncoder passwordEncoder () {
		return new BCryptPasswordEncoder();
	}
	@Override
	protected void configure (AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService);
	}
	@Override
	protected void configure (HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/erp/api/*").hasRole("admin")
			.antMatchers("/erp/hello").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.loginPage("/erp/login_page")// 自定义网页 可以返回自己定义的错误信息
			.loginProcessingUrl("/erp/login") // 通过post该接口可以实现登陆
			.usernameParameter("name")
			.passwordParameter("password")
			.permitAll()
			.successHandler(new AuthenticationSuccessHandler() {				
				@Override
				public void onAuthenticationSuccess(HttpServletRequest arg0, HttpServletResponse arg1, Authentication arg2)
						throws IOException, ServletException {
					arg1.setContentType("application/json;charset=utf-8");
					PrintWriter printWriter = arg1.getWriter();
					System.out.println("Success");
					arg1.setStatus(200);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("status", 200);
					map.put("msg", "Success");

					User principal = (User) arg2.getPrincipal();
					map.put("userid", principal.getId());
					ObjectMapper objectMapper = new ObjectMapper();
					printWriter.write(objectMapper.writeValueAsString(map));
					printWriter.flush();
					printWriter.close();
				}
			})
			.failureHandler(new AuthenticationFailureHandler() {
				@Override
				public void onAuthenticationFailure(HttpServletRequest arg0, HttpServletResponse arg1, AuthenticationException arg2)
						throws IOException, ServletException {
					arg1.setContentType("application/json;charset=utf-8");
					PrintWriter printWriter = arg1.getWriter();
					arg1.setStatus(401);
					Map<String, Object> map = new HashMap<>();
					map.put("status", 401);
					if(arg2 instanceof LockedException) {
						map.put("msg", "用户被锁定，登陆失败");
					}else if(arg2 instanceof BadCredentialsException) {
						map.put("msg", "用户名或密码错误，登陆失败");
					}else if(arg2 instanceof DisabledException) {
						map.put("msg", "用户被禁用，登陆失败");
					}else if(arg2 instanceof AccountExpiredException) {
						map.put("msg", "用户过期，登陆失败");
					}else if(arg2 instanceof CredentialsExpiredException) {
						map.put("msg", "用户密码过期，登陆失败");
					}else {
						map.put("msg", "登陆失败");
					}
					ObjectMapper oMapper = new ObjectMapper();
					printWriter.write(oMapper.writeValueAsString(map));
					printWriter.flush();
					printWriter.close();
				}
			})
			.and()
			.logout()
			.logoutUrl("/erp/logout")
			.clearAuthentication(true)
			.invalidateHttpSession(false)
			.addLogoutHandler(new LogoutHandler() {				
				@Override
				public void logout(HttpServletRequest arg0, HttpServletResponse arg1, Authentication arg2) {
				}
			})
			.logoutSuccessHandler(new LogoutSuccessHandler() {				
				@Override
				public void onLogoutSuccess(HttpServletRequest arg0, HttpServletResponse arg1, Authentication arg2)
						throws IOException, ServletException {
					arg1.sendRedirect("/login_page");
					//arg1.sendRedirect("/login");
				}
			})
			.and()
			.cors()
			.and()
			.csrf().disable();
	}
}

package com.chq.project.admin.common.security;

import com.chq.project.admin.system.service.CustomUserServiceImpl;
import com.chq.project.admin.system.service.MyFilterSecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * @author CHQ
 * @Description 继承WebSecurityConfigurerAdapter抽象类，主要是最后配置Security，配置之前定义的拦截器、提供用户基本权限信息、以及一些访问控制。
 * @date 2019/1/28
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;


    /**
     * 注册UserDetailsService 的bean
     *
     * @return
     */
    @Bean
    UserDetailsService customUserService() {
        return new CustomUserServiceImpl();
    }

    /**
     * user Details Service验证
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService()).passwordEncoder(new BCryptPasswordEncoder());

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(new String[]{"/static/**"}).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login/form")
                .failureUrl("/login?error")
                .successForwardUrl("/loginSuccess")
                .permitAll() //登录页面用户任意访问
                .and()
                .logout().permitAll()
                .and()
                .headers()
                .frameOptions().sameOrigin();
        //注销行为任意访问
        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/static/**", "/swagger-ui.html/**");
    }

}

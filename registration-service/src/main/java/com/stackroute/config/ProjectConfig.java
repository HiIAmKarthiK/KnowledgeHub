//package com.stackroute.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//public class ProjectConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.antMatcher("/**")
//                .authorizeRequests()
//                .antMatchers("/api/v1/register/noauth","/api/v1/register/users","/api/v1/register/upload","/api/v1/register/uploadnotes/{userEmail}","/api/v1/register/allnotes/{userEmail}","/api/v1/register/userdetails/{email}")
//                .permitAll()
//                .anyRequest().authenticated().and()
//                .oauth2Login();
//    }
//}

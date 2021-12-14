package com.spring.practice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.spring.practice.service.UserSecurityService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserSecurityService usersecurityService;

	private PasswordEncoder passwordEncoder() {
		return SecurityUtility.passwordEncoder();
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/assets/**", "/", "/create").permitAll().antMatchers("/user/**")
				.access("hasRole('ROLE_USER')").antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')").anyRequest()
				.authenticated();
		http.csrf().disable().cors().disable().formLogin().loginPage("/login").loginProcessingUrl("/loginProcess")
				.defaultSuccessUrl("/home").failureUrl("/login?error").permitAll().and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/?logout")
				.deleteCookies("SESSION").invalidateHttpSession(true).clearAuthentication(true).permitAll();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usersecurityService).passwordEncoder(passwordEncoder());
	}

}

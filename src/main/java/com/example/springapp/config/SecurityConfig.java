package com.example.springapp.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
@Configuration
//@EnableAutoConfiguration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

	/*  auth
	  		.jdbcAuthentication()
	  		//.usersByUsernameQuery("select username, password,'true' from user where username=?")
			//.authoritiesByUsernameQuery("select u.username, r.role from user u inner join user_role ur on(u.user_id=ur.user_id) inner join role r on(ur.role_id=r.role_id) where u.username=?")
	  		.usersByUsernameQuery("select username, password, 'true' from user where username=?")
            .authoritiesByUsernameQuery("select username, 'ROLE_USER' from user where username=?")
	  		.dataSource(dataSource)
	  		.passwordEncoder(bCryptPasswordEncoder);
	  		*/
		auth.userDetailsService(userDetailsService());
	 }
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
	        throws Exception {
	    auth.userDetailsService(userDetailsService()).passwordEncoder(bCryptPasswordEncoder);

	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http
	            .authorizeRequests()
	            .antMatchers("/", "/home", "/register", "/register/**", "/send_email_register").permitAll()
	            .anyRequest().authenticated()
	            .and()
	            .formLogin()
				.loginPage("/login").defaultSuccessUrl("/welcome")
				.usernameParameter("username")
				.passwordParameter("password")
				.permitAll()
	            .and()
	            .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/home?logout")
	            .permitAll();
	}
	
	@Override
	protected UserDetailsService userDetailsService() {
	    return userDetailsService;
	}

} 
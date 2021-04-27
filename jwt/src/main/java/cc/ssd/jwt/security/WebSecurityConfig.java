package cc.ssd.jwt.security;


import cc.ssd.jwt.security.jwt.AuthEntryPointJwt;
import cc.ssd.jwt.security.jwt.AuthTokenFilter;
import cc.ssd.jwt.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
			http.cors()
				.and().csrf().disable()
				.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/api/login").permitAll()
				.antMatchers("/api/greeting").permitAll()
				//.antMatchers("/api/dashboard/**").hasAuthority("Administrator") // hasAuthority kullanılması için roller ROLE_* diye tanımlanmalı
				.antMatchers("/api/dashboard/**").access("hasAuthority('Administrator')") //Bu çalışıyor.
				//.antMatchers("/api/dashboard/**").access("hasAnyAuthority('Administrator','RegisteredUser')") //Bu çalışıyor.
				//.antMatchers("/akademikpersonelview/**").hasRole("APERSONEL")
				.antMatchers("/users/**").permitAll()
				.anyRequest().authenticated()
				.and()
				//.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}

}

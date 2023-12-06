package SpringGroup.SpringResult.global.security;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  // JWT 토큰을 생성하고 검증하는 역할
  private final JwtTokenProvider JwtTokenProvider;

  // authenticationManager(사용자 인증을 담당)를 Bean 등록
  // @Bean
  // public AuthenticationManager
  // authenticationManager(AuthenticationManagerBuilder builder) throws Exception
  // {
  // return builder.build();
  // }
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // 웹 보안 설정을 정의
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(JwtTokenProvider); // JWT 인증 필터를 생성

    http
        .csrf(csrf -> csrf
            .disable()) // CSRF 보호 비활성화
        .httpBasic(basic -> basic
            .disable()) // HTTP 기본 인증 비활성화
        .authorizeRequests(authz -> authz // 특정 URL 패턴에 대한 접근 권한을 설정
            .antMatchers("/test").authenticated()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/user/**").hasRole("USER")
            .antMatchers("/**").permitAll())
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .sessionManagement(sessions -> sessions
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 세션을 생성하지 않고, 인증을 위해 세션을 사용하지 않습니다

    return http.build();
  }
}

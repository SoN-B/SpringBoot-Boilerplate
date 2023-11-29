package SpringGroup.SpringResult.global.security;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import SpringGroup.SpringResult.domain.member.repository.MemberJpaRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import java.util.Date;
import java.util.Base64;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

// 토큰을 생성하고 검증하는 클래스입니다.
// 해당 컴포넌트는 필터클래스에서 사전 검증을 거칩니다.
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
  private final MemberJpaRepository memberJpaRepository;

  private String secretKey = "springsecretkey";

  // 토큰 유효시간 30분
  private long tokenValidTime = 30 * 60 * 1000L;

  // 객체 초기화, secretKey를 Base64로 인코딩한다.
  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  // JWT 토큰 생성
  public String createToken(String userPk, List<String> roles) {
    Claims claims = Jwts.claims().setSubject(userPk); // sub 필드 - userPk 값
    claims.put("roles", roles); // roles 필드 - roles 값
    Date now = new Date();
    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now) // 토큰 발행 시간
        .setExpiration(new Date(now.getTime() + tokenValidTime)) // 토큰 만료 시간
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact(); // JWT 토큰을 문자열 형태로 생성
  }

  // 주어진 JWT 토큰을 이용하여 Authentication 객체를 생성하고 반환
  // Authentication 객체는 Spring Security에서 현재 인증된 사용자의 정보를 담고 있습니다.
  public Authentication getAuthentication(String token) {
    UserDetails userDetails = memberJpaRepository.findByEmail(this.getUserPk(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  // 토큰에서 회원 정보 추출
  public String getUserPk(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  // Request의 Header에서 token 값을 가져옵니다. "Authorization" : "TOKEN값'
  public String resolveToken(HttpServletRequest request) {
    return request.getHeader("Authorization");
  }

  // 토큰의 유효성 + 만료일자 확인
  public boolean validateToken(String jwtToken) {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
      return !claims.getBody().getExpiration().before(new Date());
    } catch (Exception e) {
      return false;
    }
  }
}

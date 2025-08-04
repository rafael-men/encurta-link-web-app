package com.rafael_men.encurta_link_api.security;

import com.rafael_men.encurta_link_api.dto.UserDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationInMs;

    private SecretKey getSigningKey() {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception e) {
            String paddedSecret = jwtSecret;
            while (paddedSecret.getBytes().length < 64) {
                paddedSecret += paddedSecret;
            }
            return Keys.hmacShaKeyFor(paddedSecret.substring(0, 64).getBytes());
        }
    }

    public String generateToken(UserDto userDto) {
        Date expiryDate = new Date(System.currentTimeMillis() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(userDto.getId().toString())
                .claim("username", userDto.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }


    public String getUsernameFromToken(String token) {
        try {
            Claims claims = getClaims(token);
            return claims.get("username", String.class);
        } catch (Exception e) {
            logger.error("Erro ao extrair username do token: {}", e.getMessage());
            return null;
        }
    }


    public UUID getUserIdFromToken(String token) {
        try {
            Claims claims = getClaims(token);
            return UUID.fromString(claims.getSubject());
        } catch (Exception e) {
            logger.error("Erro ao extrair user ID do token: {}", e.getMessage());
            return null;
        }
    }


    public Date getExpirationDateFromToken(String token) {
        try {
            Claims claims = getClaims(token);
            return claims.getExpiration();
        } catch (Exception e) {
            logger.error("Erro ao extrair data de expiração do token: {}", e.getMessage());
            return null;
        }
    }


    public Date getIssuedAtDateFromToken(String token) {
        try {
            Claims claims = getClaims(token);
            return claims.getIssuedAt();
        } catch (Exception e) {
            logger.error("Erro ao extrair data de criação do token: {}", e.getMessage());
            return null;
        }
    }


    public boolean isTokenExpired(String token) {
        try {
            Date expiration = getExpirationDateFromToken(token);
            return expiration != null && expiration.before(new Date());
        } catch (Exception e) {
            logger.error("Erro ao verificar expiração do token: {}", e.getMessage());
            return true;
        }
    }


    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(authToken);


            if (isTokenExpired(authToken)) {
                logger.warn("Token expirado");
                return false;
            }

            return true;
        } catch (MalformedJwtException ex) {
            logger.error("Token JWT malformado: {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            logger.error("Token JWT expirado: {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            logger.error("Token JWT não suportado: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("Claims JWT vazio: {}", ex.getMessage());
        } catch (Exception ex) {
            logger.error("Erro na validação do token JWT: {}", ex.getMessage());
        }
        return false;
    }

    public boolean validateToken(String token, UserDto userDto) {
        try {
            String username = getUsernameFromToken(token);
            UUID userId = getUserIdFromToken(token);

            return (username != null &&
                    userId != null &&
                    username.equals(userDto.getUsername()) &&
                    userId.equals(userDto.getId()) &&
                    !isTokenExpired(token));
        } catch (Exception e) {
            logger.error("Erro ao validar token para usuário: {}", e.getMessage());
            return false;
        }
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            logger.error("Erro ao extrair claims do token: {}", e.getMessage());
            throw e;
        }
    }

    public String refreshToken(String token) {
        try {
            Claims claims = getClaims(token);
            UUID userId = UUID.fromString(claims.getSubject());
            String username = claims.get("username", String.class);

            UserDto userDto = new UserDto(userId, username);
            return generateToken(userDto);
        } catch (Exception e) {
            logger.error("Erro ao fazer refresh do token: {}", e.getMessage());
            throw new RuntimeException("Não foi possível fazer refresh do token", e);
        }
    }

    public long getExpirationTimeInMillis() {
        return jwtExpirationInMs;
    }

    public long getTimeToExpiration(String token) {
        try {
            Date expiration = getExpirationDateFromToken(token);
            if (expiration != null) {
                return expiration.getTime() - System.currentTimeMillis();
            }
            return 0;
        } catch (Exception e) {
            logger.error("Erro ao calcular tempo restante do token: {}", e.getMessage());
            return 0;
        }
    }

    public boolean shouldRefreshToken(String token) {
        try {
            long timeToExpiration = getTimeToExpiration(token);
            return timeToExpiration < (10 * 60 * 1000);
        } catch (Exception e) {
            return true;
        }
    }


    public void logTokenInfo(String token) {
        try {
            Claims claims = getClaims(token);
            logger.info("=== TOKEN INFO ===");
            logger.info("Subject (User ID): {}", claims.getSubject());
            logger.info("Username: {}", claims.get("username"));
            logger.info("Issued At: {}", claims.getIssuedAt());
            logger.info("Expires At: {}", claims.getExpiration());
            logger.info("Time to expiration: {} ms", getTimeToExpiration(token));
            logger.info("Should refresh: {}", shouldRefreshToken(token));
            logger.info("==================");
        } catch (Exception e) {
            logger.error("Erro ao extrair informações do token: {}", e.getMessage());
        }
    }
}
package br.com.gva.quefominha.core.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.domain.entity.Customer;
import br.com.gva.quefominha.repositories.CustomerRepository;

@Service
public class JwtService {

    // CORREÇÃO 1: secret key lida do application.properties via @Value,
    // nunca mais hardcoded no código-fonte.
    @Value("${jwt.secret-key}")
    private String secretKey;

    // CORREÇÃO 2: expiração lida do application.properties.
    // Era: 1000 * 60 * 24 = 1.440.000ms = 24 MINUTOS (bug!).
    // Agora: configurado como 86400000ms = 24 HORAS por padrão.
    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Autowired
    private CustomerRepository repository;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setId(this.getCustomerId(userDetails.getUsername()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // CORREÇÃO 2 aplicada: usa jwtExpiration injetado (24h por padrão)
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        // CORREÇÃO 1 aplicada: usa secretKey injetado do properties
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String getCustomerId(String email) {
        Customer customer = repository.findByEmail(email).orElseThrow();
        return customer.getId();
    }
}

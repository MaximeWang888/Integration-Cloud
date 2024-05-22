package org.efrei;

import org.efrei.DAO.UserDetailRepository;
import org.efrei.clients.AuthServiceClient;
import org.efrei.entity.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class UserService {
    @Autowired
    private UserDetailRepository userDetailRepository;

    static String SECRET_KEY = "";

    @Autowired
    private AuthServiceClient authServiceClient;

    public UserDetail getUserDetail(Long userId) {
        return userDetailRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserDetail updateUserDetail(Long userId, UserDetail userDetail) {
        if (!userDetailRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
        userDetail.setId(userId);
        return userDetailRepository.save(userDetail);
    }
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    public Boolean isTokenValid(String token) {
        return !isTokenExpired(token);
    }
    public void removeUser(Long userId) {
        userDetailRepository.deleteById(userId);
    }

    public String ping() {
        return authServiceClient.ping("Je viens du service UserService !");
    }
}

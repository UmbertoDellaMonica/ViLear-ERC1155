package it.tcgroup.vilear.base.Payload.JWTServiceBase;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.sign.key}")
    private  String jwtSignKey;


    @Override
    public String retrieveToken(HttpServletRequest request){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        token = noBearerToken(token);
        return token;
    }

    @Override
    public Authentication extractUserFromToken(String token) {
        // Recupera i campi del Jwt Token
        Claims claims = getClaimsFromToken(token);
        // Recupera le liste dell'authorities
        List<SimpleGrantedAuthority> grantedAuthoritieList = getAuthoritiesFromToken(token,getClaimsFromToken(token));
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        claims.get("email").toString(),
                        null,
                        grantedAuthoritieList);

        return authentication;
    }


    @Override
    public List<SimpleGrantedAuthority> getAuthoritiesFromToken(String token, Claims claims){
        List<SimpleGrantedAuthority> grantedAuthoritieList = new ArrayList<>();

        List<LinkedHashMap<String,Object>> rolesList = (List<LinkedHashMap<String, Object>>) claims.get("role");

        for(LinkedHashMap<String, Object> grantedAuth :  rolesList ){

            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(grantedAuth.get("authority").toString());
            grantedAuthoritieList.add((SimpleGrantedAuthority) grantedAuthority);
        }
        return grantedAuthoritieList;
    }


    @Override
    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(hashKey()).build().parseClaimsJws(token).getBody();
    }



    /**
     * Algoritmo per la Chiave di Cifrazione del Jwt
     */
    private SecretKey hashKey() {
        MessageDigest messageDigest;
        byte[] sha256 = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            sha256 = messageDigest.digest(jwtSignKey.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return Keys.hmacShaKeyFor(sha256);
    }

    @Override
    public String noBearerToken(String token){
        if(token == null){
            return null;
        }
        token=token.replaceFirst("Bearer ","");
        return token;
    }

}

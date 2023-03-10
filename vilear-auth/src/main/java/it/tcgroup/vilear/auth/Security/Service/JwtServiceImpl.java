package it.tcgroup.vilear.auth.Security.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultJwtBuilder;
import io.jsonwebtoken.security.Keys;
import it.tcgroup.vilear.auth.Entity.UserEntity;
import it.tcgroup.vilear.auth.Model.UserJwtModel;
import it.tcgroup.vilear.auth.Redis.Service.UserRedisService;
import it.tcgroup.vilear.auth.Repository.UserRepository;
import it.tcgroup.vilear.base.Payload.Exception.Unahautorized;
import it.tcgroup.vilear.base.Payload.Exception.UserNotFoundException;
import it.tcgroup.vilear.base.Payload.JWTServiceBase.JwtService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.*;

@Service
public class JwtServiceImpl implements JwtServiceAuth {
    @Value("${jwt.sign.key}")
    private  String jwtSignKey;

    @Value("${jwt.duration.seconds}")
    private int jwtDurationSeconds;

    @Value("${user.super_admin.email}")
    private String userSuperAdminEmail;

    @Value("${user.super_admin.password}")
    private String userSuperAdminPassword;

    @Autowired
    private UserRedisService userRedisService;


    @Autowired
    private UserRepository userRepository;

    /**
     * Costruisce il Token Jwt e lo restituisce al Client al momento della Login
     *
     * @param autentication fa partire l'autenticazione
     *                      Costruisce un'associazione chiave valore con il nome dell'utente
     *                      E la sua password
     * @param email      fa partire l'autenticazione
     *                      Costruisce un'associazione chiave valore con il nome dell'utente
     *                      E la sua password
     * @return
     */
    @Override
    public String buildToken(Authentication autentication, String email) {

        // Verifico che l'utente abbia lo stesso username
        if(!email.equals(autentication.getName())){
            throw new Unahautorized();
        }
        // Costruisco il Jwt in base all'admin
        if(autentication.getName().equalsIgnoreCase(userSuperAdminEmail)){
            String token = buildTokenAdmin(autentication);
            return token;
        }


        // Recupero lo UserJwtModel
        Optional<UserEntity> userEntity = userRepository.findUserByEmail(email);
        if(userEntity.isEmpty()){
            throw new UserNotFoundException();
        }

        // Costruisco un Builder
        JwtBuilder builder = new DefaultJwtBuilder();

        Map<String, Object> claims = new HashMap<>();
        // Recupero gli Oggetti
        claims.put("user_id",userEntity.get().getId());
        claims.put("email", autentication.getName());
        claims.put("role", autentication.getAuthorities());
        builder.addClaims(claims);
        // Data di rilascio
        Date releaseDate = Date.from(Instant.now());
        // Aggiunge i minuti di Expired Token
        Date expirationDate = DateUtils.addSeconds(releaseDate, jwtDurationSeconds);
        // Setto i campi appena aggiunti
        builder.setIssuedAt(releaseDate);
        builder.setExpiration(expirationDate);

        Key key = hashKey();

        String token = builder.signWith(key).compact();
        // Verifco che l'email non sia uguale a quella dell'admin
        // L'admin non deve andare in cache
        // Elimino inizialmente questo utente poichè contiene in cache dati inesatti
            UserJwtModel userRedisModel = buildUserJwtModel(token, userEntity.get(), releaseDate,expirationDate);
        // Salva questo dato in Cache
        return token;
    }

    private String buildTokenAdmin(Authentication autentication) {
        JwtBuilder builder = new DefaultJwtBuilder();

        Map<String, Object> claims = new HashMap<>();
        // Recupero gli Oggetti
        claims.put("user_id",0);
        claims.put("email", autentication.getName());
        claims.put("role", autentication.getAuthorities());
        builder.addClaims(claims);
        // Data di rilascio
        Date releaseDate = Date.from(Instant.now());
        // Aggiunge i minuti di Expired Token
        Date expirationDate = DateUtils.addSeconds(releaseDate, jwtDurationSeconds);
        // Setto i campi appena aggiunti
        builder.setIssuedAt(releaseDate);
        builder.setExpiration(expirationDate);

        Key key = hashKey();

        String token = builder.signWith(key).compact();
        UserJwtModel userJwtModel = new UserJwtModel();
        userJwtModel.setId(0);
        userJwtModel.setPassword(userSuperAdminPassword);
        userJwtModel.setEmail(userSuperAdminEmail);
        userJwtModel.setToken(token);
        userJwtModel.setReleaseDate(releaseDate);
        userJwtModel.setExpiredDate(expirationDate);
        userRedisService.add(userJwtModel);
        return token;
    }

    /**
     * Aggiunge il Dato all'interno della Cache
     * @param token
     * @param userEntity
     * @param releaseDate
     * @param expireDate
     * @return
     */
    private UserJwtModel buildUserJwtModel(
            String token,
            UserEntity userEntity,
            Date releaseDate,
            Date expireDate
        ){
        // Setto tutti i dati nello UserJwt e lo salvo in cache
        UserJwtModel userRedisModel = new UserJwtModel();
        userRedisModel.setId(userEntity.getId());
        userRedisModel.setPassword(userEntity.getPassword());
        userRedisModel.setEmail(userEntity.getEmail());
        userRedisModel.setToken(token);
        userRedisModel.setReleaseDate(releaseDate);
        userRedisModel.setExpiredDate(expireDate);
        return userRedisService.add(userRedisModel);
    }



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


    @Override
    public Boolean isExpiredToken(String token){
        UserJwtModel userJwtModel = userRedisService.getByToken(token);
        if(userRedisService.isExpiredTokenFromUser(userJwtModel)){
            return Boolean.TRUE;
        }else {
            return Boolean.FALSE;
        }
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

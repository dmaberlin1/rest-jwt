package com.dmadev.restjwt.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;


@Component
public class JwtTokenUtils {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Duration jwtlifetime;

    //worked method generateToken with jjwt library
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> rolesList = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", rolesList);
        //  example       claims.put("email",user.email)

        Date issuedDate=new Date();
        Date expiredDate=new Date(issuedDate.getTime()+jwtlifetime.toMillis());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    public String getUsername(String token){
        return getAllClaimsFromToken(token).getSubject();
        //имя юзера будет жить в subject,  на практике там будет не только name
    }

//    @SuppressWarnings("unchecked")
//    public List<String> getRoles(String token){
//        return (List<String>) getAllClaimsFromToken(token).get("roles");
//    }

    @SuppressWarnings("unchecked")
    public List<String> getRoles(String token){
         List<?> roles=getAllClaimsFromToken(token).get("roles",List.class);
         return roles.stream().map(Object::toString).toList();
    }


    //Получаю полезные данные из токена, общий метод
    private Claims getAllClaimsFromToken(String token){
      return Jwts.parser()
              .setSigningKey(secret)
              .parseClaimsJws(token)
              .getBody();
    }
}






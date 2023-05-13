package com.ascendingdc.training.project2020.service.impl;

import com.ascendingdc.training.project2020.dto.RoleDto;
import com.ascendingdc.training.project2020.dto.UserDto;
//import com.ascendingdc.training.project2020.entity.Role;
//import com.ascendingdc.training.project2020.entity.User;
import com.ascendingdc.training.project2020.service.JWTService;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Set;

@Service
public class JWTServiceImpl implements JWTService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private final String SECRET_KEY = System.getProperty("secret.key");
//    private final String SECRET_KEY = System.getenv("secret.key");
    private final String ISSUER = "com.ascending";
    private final long EXPIRATION_TIME = 86400 * 1000;

    public String generateToken(UserDto userDto) {
        /*
           1. decide signature algorithm
   2. hard code secret key  -- late ruse VM option to pass in the key
   3. sign JWT with key
   4.  organize our payload:  Claims ---> map   cliams has some prefefined keys   ,  add your own custom key/value pairs
   5. set claims JWT api
   6. generate the token

         */
        logger.info("==================, input SECRET_KEY = {}", SECRET_KEY);

        //JWT signature algorithm using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //Sign JWT with SECRET_KEY
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        Claims claims = Jwts.claims();
        claims.setId(String.valueOf(userDto.getId()));
        claims.setSubject(userDto.getName());
        claims.setIssuedAt(new Date(System.currentTimeMillis()));
        claims.setIssuer(ISSUER);
        claims.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME));

        Set<RoleDto> roles = userDto.getRoleDtoSet();
        String allowedReadResources = "";
        String allowedCreateResources = "";
        String allowedUpdateResources = "";
        String allowedDeleteResources = "";
//
///        String allowedResource = roles.stream().map(role -> role.getAllowedResource()).collect(Collectors.joining(","));
//        claims.put("allowedResource", allowedResource);
//        String allowedReadResources = roles.stream().filter(r->r.isAllowedRead()).map(r1->r1.getAllowedResource()).collect(Collectors.joining(","));
///        logger.info("======, allowedResource = {}", allowedResource);

        for (RoleDto roleDto : roles) {
            if (roleDto.isAllowedRead())
                allowedReadResources = String.join(roleDto.getAllowedResource(), allowedReadResources, ",");
            if (roleDto.isAllowedCreate())
                allowedCreateResources = String.join(roleDto.getAllowedResource(), allowedCreateResources, ",");
            if (roleDto.isAllowedUpdate())
                allowedUpdateResources = String.join(roleDto.getAllowedResource(), allowedUpdateResources, ",");
            if (roleDto.isAllowedDelete())
                allowedDeleteResources = String.join(roleDto.getAllowedResource(), allowedDeleteResources, ",");
        }

///        for (Role role : roles) {
///            if (role.isAllowedRead()) allowedReadResources = allowedResource;
///            if (role.isAllowedCreate()) allowedCreateResources = allowedResource;
///            if (role.isAllowedUpdate()) allowedUpdateResources = allowedResource;
///            if (role.isAllowedDelete()) allowedDeleteResources = allowedResource;
///        }
        logger.info("======, allowedReadResources = {}", allowedReadResources);
        logger.info("======, allowedCreateResources = {}", allowedCreateResources);
        logger.info("======, allowedUpdateResources = {}", allowedUpdateResources);
        logger.info("======, allowedDeleteResources = {}", allowedDeleteResources);


        claims.put("allowedReadResources", allowedReadResources.replaceAll(",$", ""));
        claims.put("allowedCreateResources", allowedCreateResources.replaceAll(",$", ""));
        claims.put("allowedUpdateResources", allowedUpdateResources.replaceAll(",$", ""));
        claims.put("allowedDeleteResources", allowedDeleteResources.replaceAll(",$", ""));

//        //Set the JWT Claims
        JwtBuilder builder = Jwts.builder().setClaims(claims).signWith(signatureAlgorithm, signingKey);

        //Builds the JWT and serializes it to a compact, URL-safe string
        String generatedToken = builder.compact();
        return generatedToken;
    }

    public Claims decryptJwtToken(String token) {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Claims claims = Jwts.parser()
                .setSigningKey(apiKeySecretBytes)
                .parseClaimsJws(token).getBody();
//        Claims claims = Jwts.parser()
//                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
//                .parseClaimsJws(token).getBody();

        logger.debug("Claims: " + claims.toString());
        return claims;
    }

    public boolean hasTokenExpired(String token) {
        boolean hasExpiredFlag = true;

        try {
            Claims claims = decryptJwtToken(token);
            Date tokenExpirationDate = claims.getExpiration();
            Date todayDate = new Date();

            hasExpiredFlag = tokenExpirationDate.before(todayDate);
        } catch (ExpiredJwtException ex) {
            logger.error("ExpiredJwtException is thrown when decryptJwtToken(token) in hasTokenExpired(token) is called, error={}", ex.getMessage());
        }
        return hasExpiredFlag;
    }
}

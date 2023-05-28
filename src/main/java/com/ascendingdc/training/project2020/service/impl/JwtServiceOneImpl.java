package com.ascendingdc.training.project2020.service.impl;

import com.ascendingdc.training.project2020.dto.RoleDto;
import com.ascendingdc.training.project2020.dto.UserDto;
import com.ascendingdc.training.project2020.service.JWTService;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Service("jwtOneService")
public class JwtServiceOneImpl implements JWTService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

//    private final String SECRET_KEY = System.getProperty("secret_key");

    private final String SECRET_KEY = System.getenv("secret_key");

    private final long EXPIRATION_TIME = 86400 * 1000;

    private final String ISSUER = "com.ascendingdc";

    @Override
    public String generateToken(UserDto userDto) {
        /*
         * 1. decide signature algorithm
         * 2. hard code secret key  -- late ruse VM option to pass in the key
         * 3. organize our payload:  Claims ---> map   cliams has some prefefined keys   ,  add your own custom key/value pairs
         * 4. set claims JWT api
         * 5. sign JWT with key
         * 6. generate the token
         */
        logger.info("=====, retrieved SECRET_KEY={}", SECRET_KEY);

        //decide JWT signature algorithm to be used to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

        //Create and organize Claims
        Claims claims = Jwts.claims();
        claims.setId(String.valueOf(userDto.getId()));
        claims.setSubject(userDto.getName());
        claims.setIssuedAt(new Date(System.currentTimeMillis()));
        claims.setIssuer(ISSUER);
        claims.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME));

/////////////////////////
        /*  1. retrieve all roles belong to the specific userDto
         *     user vs role is many to many
         *  2. loop through each role to populate (concatenate URI strings)
         *     the four variables:
         *     a. allowedReadResources
         *     b. allowedCreateResources
         *     c. allowedUpdateResources
         *     d. allowedDeleteResources
         *  3. remove the ending "comma" from the above four String variables
         *  4. Add the four Strings as custom attributes to JWT Claims
         */
        String allowedReadResources = "";
        String allowedCreateResources = "";
        String allowedUpdateResources = "";
        String allowedDeleteResources = "";

        Set<RoleDto> roleDtoSet = userDto.getRoleDtoSet();
        for(RoleDto roleDto : roleDtoSet) {
            if(roleDto.isAllowedRead())
                allowedReadResources = String.join(",", roleDto.getAllowedResource(), allowedReadResources);
            if(roleDto.isAllowedCreate())
                allowedCreateResources = String.join(",", roleDto.getAllowedResource(), allowedCreateResources);
            if(roleDto.isAllowedUpdate())
                allowedUpdateResources = String.join(",", roleDto.getAllowedResource(), allowedUpdateResources);
            if(roleDto.isAllowedDelete())
                allowedDeleteResources = String.join(",", roleDto.getAllowedResource(), allowedDeleteResources);
        }
        logger.info("======, The final result of allowedReadResources = {}", allowedReadResources);
        logger.info("======, The final result of allowedCreateResources = {}", allowedCreateResources);
        logger.info("======, The final result of allowedUpdateResources = {}", allowedUpdateResources);
        logger.info("======, The final result of allowedDeleteResources = {}", allowedDeleteResources);

        claims.put("allowedReadResources", allowedReadResources.replaceAll(",$", ""));
        claims.put("allowedCreateResources", allowedCreateResources.replaceAll(",$", ""));
        claims.put("allowedUpdateResources", allowedUpdateResources.replaceAll(",$", ""));
        claims.put("allowedDeleteResources", allowedDeleteResources.replaceAll(",$", ""));

        //set claims and sign with plain secret key string
//        JwtBuilder jwtBuilder = Jwts.builder().setClaims(claims).signWith(signatureAlgorithm, SECRET_KEY);

        //set claims and sign with String byte array
        byte[] secretKeyStringBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
//        Key signingKey = new SecretKeySpec(secretKeyStringBytes, signatureAlgorithm.getJcaName());
        JwtBuilder jwtBuilder = Jwts.builder().setClaims(claims).signWith(signatureAlgorithm, secretKeyStringBytes);

        //build the JWT token and then serializes the token to compact it
        String generatedToken = jwtBuilder.compact();

        return generatedToken;
    }

    @Override
    public Claims decryptJwtToken(String token) {
        byte[] secretKeyStringBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Claims claims = Jwts.parser()
                .setSigningKey(secretKeyStringBytes)
                .parseClaimsJws(token)
                .getBody();
        logger.info("=== parsed claims = {}", claims);
        return claims;
    }

    @Override
    public boolean hasTokenExpired(String token) {
        boolean hasTokenExpired = true;
        try {
            Claims claims = decryptJwtToken(token);
            Date tokenExpirationDate = claims.getExpiration();
            Date nowDate = new Date();

            hasTokenExpired = nowDate.after(tokenExpirationDate);
        } catch (ExpiredJwtException e) {
            logger.error("ExpiredJwtException is caught, error = {}", e.getMessage());
        }
        return hasTokenExpired;
    }

    @Override
    public boolean validateAccessToken(String token) {
        boolean isTokenValid = false;
        try {
            byte[] secretKeyStringBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
            Jwts.parser()
                    .setSigningKey(secretKeyStringBytes)
                    .parseClaimsJws(token);
            isTokenValid = true;
        } catch (ExpiredJwtException ex) {
            logger.error("JWT expired", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("Token is null, empty or only whitespace", ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.error("JWT is invalid", ex);
        } catch (UnsupportedJwtException ex) {
            logger.error("JWT is not supported", ex);
        } catch (SignatureException ex) {
            logger.error("Signature validation failed");
        }
        return isTokenValid;
    }
}

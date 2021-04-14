package com.stackroute.config;

import com.stackroute.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*This is a service class which generates JwtToken for authentication.
 * JwtToken: is an open standard (RFC 7519) that defines a compact and self-contained way
 * for securely transmitting information between parties as a JSON object.*/
@Service
public class JWTGenerator {

    /*generates a new JWT access token and returns it to the client. On every request to a restricted resource,
    the client sends the access token in the query string or Authorization header. */
    public Map<String, String> generateJwtToken(User user) {
        String jwtToken = "";
        jwtToken = Jwts.builder().setSubject(user.getEmail()).setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "TopSecret").compact();
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", jwtToken);
        tokenMap.put("role", "regular User");
        return tokenMap;
    }
}
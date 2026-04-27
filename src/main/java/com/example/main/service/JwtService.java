package com.example.main.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    // Clave secreta para firmar los tokens (guárdala segura)
    private String secretKey = "Secretito ;)";

    // Tiempo de expiración: 1 hora
    private long expiration = 3600_000;

    // Genera un token JWT para un usuario
    public String generarToken(com.example.main.api.modelo.Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getNombre())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String validarToken(String token) {
        Claims claims = Jwts.parser()   // obtenemos el parser
                .setSigningKey(secretKey) // clave para verificar la firma
                .build()                 // construimos el parser
                .parseSignedClaims(token)  // parseamos el token firmado
                .getPayload();            // obtenemos el body -> claims

        return claims.getSubject(); // devuelve el username
    }
}
package com.example.main.api.controlador;

import com.example.main.api.modelo.Usuario;
import com.example.main.service.JwtService;
import com.example.main.service.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class ControladorUsuario {

    private UsuarioServicio usuarioservicio;
    private JwtService jwtService;

    public ControladorUsuario(UsuarioServicio usuarioservicio, JwtService jwtService) {
        this.usuarioservicio = usuarioservicio;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login (@RequestBody Map<String, String> body){
        String nombre = body.get("usuario");
        String contraseña = body.get("contraseña");

        Usuario usuario = usuarioservicio.buscarPorNombre(nombre);
        if(usuario == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(contraseña, usuario.getContraseña())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = jwtService.generarToken(usuario);

        Map<String,String> respuesta = new HashMap<>();
        respuesta.put("token",token);
        respuesta.put("usuarioId", String.valueOf(usuario.getId()));
        respuesta.put("nombre", usuario.getNombre());
        return ResponseEntity.ok(respuesta);
    }

}

package com.example.main.api.controlador;

import com.example.main.api.modelo.Entrada;
import com.example.main.service.EntradaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ControladorEntrada {

    private EntradaServicio entradaServicio;

    @Autowired
    public ControladorEntrada(EntradaServicio entradaServicio) {
        this.entradaServicio = entradaServicio;
    }

    //localhost:8080/entrada?id=X
    @GetMapping("/entrada")
    public ResponseEntity<Entrada> getEntrada(@RequestParam Integer id, @RequestParam Integer usuarioid) {
        Entrada entrada = entradaServicio.getEntrada(id, usuarioid);
        if (entrada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entrada);
    }

    //localhost:8080/lista
    @GetMapping("/lista")
    public ResponseEntity<List<Entrada>> getLista(@RequestParam Integer usuarioid) {
        List<Entrada> lista = entradaServicio.getLista(usuarioid);
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/entrada")
    public ResponseEntity<Entrada> crearEntrada(@RequestBody Entrada entrada, @RequestParam int usuarioId) {
        Entrada creada = entradaServicio.crearEntrada(entrada, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @DeleteMapping("/entrada")
    public ResponseEntity<Void> borrarEntrada(@RequestParam Integer id, @RequestParam int usuarioId) {
        boolean respuesta = entradaServicio.borrarEntrada(id, usuarioId);
        if (respuesta) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build(); // 404
        }
    }
}

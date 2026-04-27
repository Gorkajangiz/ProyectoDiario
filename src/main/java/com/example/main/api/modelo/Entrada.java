package com.example.main.api.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class Entrada implements Serializable {

    private Integer idEntrada;
    private String titulo;
    private String texto;
    private Timestamp fecha;
    private Tipo tipo;
    private Integer usuarioId;

    public Entrada(Integer idEntrada, String titulo, String texto, Timestamp fecha, Tipo tipo, Integer usuarioId) {
        this.idEntrada = idEntrada;
        this.titulo = titulo;
        this.texto = texto;
        this.fecha = fecha;
        this.tipo = tipo;
        this.usuarioId = usuarioId;
    }

    public Integer getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(Integer idEntrada) {
        this.idEntrada = idEntrada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Entrada entrada = (Entrada) o;
        return Objects.equals(idEntrada, entrada.idEntrada);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idEntrada);
    }
}

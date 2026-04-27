package com.example.main.service;

import com.example.main.api.modelo.Entrada;
import com.example.main.api.modelo.Tipo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class EntradaServicio {

    @Autowired
    private DataSource dataSource;

    public EntradaServicio() {
    }

    public List<Entrada> getLista(int usuarioid) {
        try {
            Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from entrada where usuario_id = ? order by fecha desc");
            ps.setInt(1, usuarioid);
            List<Entrada> entradas = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Entrada entrada = new Entrada(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4), Tipo.valueOf(rs.getString(5)), usuarioid);
                entradas.add(entrada);
            }
            rs.close();
            ps.close();
            con.close();
            return entradas;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    public Entrada getEntrada(int id, int usuarioid) {
        try {
            Connection con = dataSource.getConnection();
            Entrada entrada = null;
            PreparedStatement ps = con.prepareStatement("select * from entrada where id = ? and usuario_id = ?");
            ps.setInt(1, id);
            ps.setInt(2, usuarioid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                entrada = new Entrada(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4), Tipo.valueOf(rs.getString(5)), usuarioid);
            }
            rs.close();
            ps.close();
            con.close();
            return entrada;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    public Entrada crearEntrada(Entrada entrada, int usuarioid) {
        try {
        Connection con = dataSource.getConnection();
            PreparedStatement ps;
            ps = con.prepareStatement("INSERT INTO entrada (titulo, texto, fecha, tipo, usuario_id) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, entrada.getTitulo());
            ps.setString(2, entrada.getTexto());
            ps.setTimestamp(3, entrada.getFecha());
            ps.setString(4, entrada.getTipo().toString());
            ps.setInt(5, usuarioid);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entrada.setIdEntrada(rs.getInt(1));
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entrada;
    }

    public boolean borrarEntrada(int id, int usuarioid) {
        boolean respuesta = false;
        try {
        Connection con = dataSource.getConnection();
        PreparedStatement ps;
            ps = con.prepareStatement("delete from entrada where id = ? and usuario_id = ?");
            ps.setInt(1, id);
            ps.setInt(2, usuarioid);
            respuesta = ps.executeUpdate() > 0;
            ps.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return respuesta;
    }


}

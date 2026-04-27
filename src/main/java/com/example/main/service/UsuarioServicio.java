package com.example.main.service;

import com.example.main.api.modelo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;

@Service
public class UsuarioServicio {

    @Autowired
    private DataSource dataSource;

    public UsuarioServicio() {
    }

    public Usuario buscarPorNombre(String nombre) {
        try {
            Connection con = dataSource.getConnection();
            Usuario usuario = null;
            PreparedStatement ps = con.prepareStatement("select * from usuario where nombre = ?");
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
            rs.close();
            ps.close();
            con.close();
            return usuario;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    public Usuario crearUsuario(Usuario usuario) {
        try {
        Connection con = dataSource.getConnection();
            PreparedStatement ps;
            ps = con.prepareStatement("insert into usuario (nombre, contraseña) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getContraseña());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                usuario.setId(rs.getInt(1));
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuario;
    }
}

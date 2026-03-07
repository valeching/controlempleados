package com.proyecto.controlempleados.service;

import com.proyecto.controlempleados.model.Rol;
import com.proyecto.controlempleados.model.Usuario;
import com.proyecto.controlempleados.repository.UsuarioRepository;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
    }

    public List<Usuario> listar(){
        return usuarioRepository.findAll();
    }

    public Usuario crearUsuario(String username, String password, Rol rol){

        if (usuarioRepository.existsByUsername(username)){
            throw new IllegalArgumentException("El username ya existe!");
        }

        Usuario u = new Usuario();
        u.setUsername(username);
        u.setPassword(encoder.encode(password)); 
        u.setRol(rol);
        u.setEnabled(true);

        return usuarioRepository.save(u);
    }
}

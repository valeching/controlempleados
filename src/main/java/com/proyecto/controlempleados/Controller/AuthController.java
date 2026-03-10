package com.proyecto.controlempleados.Controller;

import com.proyecto.controlempleados.model.Rol;
import com.proyecto.controlempleados.model.Usuario;
import com.proyecto.controlempleados.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String inicio(){
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registro")
    public String registro() {
        return "registro";
    }

    @PostMapping("/registro")
    public String guardarUsuario(Usuario usuario){

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRol(Rol.EMPLEADO);

        usuarioRepository.save(usuario);

        return "redirect:/login";
    }

    @GetMapping("/home")
    public String home(Authentication auth, Model model){

        String username = auth.getName();

        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);

        model.addAttribute("usuario", usuario);

        return "home";
    }

    @PostMapping("/perfil/actualizar")
    public String actualizarPerfil(
            @RequestParam Long id,
            @RequestParam String nombre,
            @RequestParam String username,
            @RequestParam String correo,
            Authentication auth,
            Model model) {

        Usuario usuarioDB = usuarioRepository.findById(id).orElse(null);

        if (usuarioDB != null) {
            usuarioDB.setNombre(nombre);
            usuarioDB.setUsername(username);
            usuarioDB.setCorreo(correo);

            usuarioRepository.save(usuarioDB);

            Authentication nuevaAuth = new UsernamePasswordAuthenticationToken(
                    usuarioDB.getUsername(),
                    auth.getCredentials(),
                    auth.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(nuevaAuth);

            model.addAttribute("usuario", usuarioDB);
        }

        return "home";
    }
}
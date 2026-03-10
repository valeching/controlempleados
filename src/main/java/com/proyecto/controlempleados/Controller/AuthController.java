package com.proyecto.controlempleados.Controller;

import com.proyecto.controlempleados.model.Rol;
import com.proyecto.controlempleados.model.Usuario;
import com.proyecto.controlempleados.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

@Controller
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // RUTA RAÍZ
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
        public String actualizarPerfil(@ModelAttribute Usuario usuario){

        Usuario usuarioDB = usuarioRepository.findById(usuario.getId()).orElse(null);

        if(usuarioDB != null){

            usuarioDB.setNombre(usuario.getNombre());
            usuarioDB.setUsername(usuario.getUsername());
            usuarioDB.setCorreo(usuario.getCorreo());

            usuarioRepository.save(usuarioDB);
        }

        return "redirect:/home";
    }
}
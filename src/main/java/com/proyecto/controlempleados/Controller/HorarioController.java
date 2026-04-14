package com.proyecto.controlempleados.Controller;

import com.proyecto.controlempleados.model.Horario;
import com.proyecto.controlempleados.model.Usuario;
import com.proyecto.controlempleados.repository.UsuarioRepository;
import com.proyecto.controlempleados.service.HorarioService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/horarios")
public class HorarioController {

    private final HorarioService service;
    private final UsuarioRepository usuarioRepo;

    public HorarioController(HorarioService service, UsuarioRepository usuarioRepo) {
        this.service = service;
        this.usuarioRepo = usuarioRepo;
    }

    
    @GetMapping
    public String verHorarios(Authentication auth, Model model) {

        Usuario usuario = usuarioRepo.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Horario> horarios;

        
        if (usuario.getRol().name().equals("ADMIN")) {
            horarios = service.listarTodos();
        } else {
            
            horarios = service.listarPorUsuario(usuario);
        }

        model.addAttribute("horarios", horarios);

        return "horarios";
    }

    @PostMapping("/entrada")
    public String entrada(Authentication auth, Model model) {

        try {
            Usuario usuario = usuarioRepo.findByUsername(auth.getName())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            service.registrarEntrada(usuario);

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "redirect:/horarios";
    }

    
    @PostMapping("/salida")
    public String salida(Authentication auth, Model model) {

        try {
            Usuario usuario = usuarioRepo.findByUsername(auth.getName())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            service.registrarSalida(usuario);

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "redirect:/horarios";
    }
}
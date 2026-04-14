package com.proyecto.controlempleados.Controller;
import com.proyecto.controlempleados.model.Usuario;
import com.proyecto.controlempleados.service.HorarioService;
import com.proyecto.controlempleados.repository.UsuarioRepository;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/horarios")
public class HorarioController {

    private final HorarioService horarioService;
    private final UsuarioRepository usuarioRepo;

    public HorarioController(HorarioService horarioService, UsuarioRepository usuarioRepo) {
        this.horarioService = horarioService;
        this.usuarioRepo = usuarioRepo;
    }

    // 🔹 REGISTRAR ENTRADA
    @PostMapping("/entrada")
    public String entrada(Authentication auth, RedirectAttributes redirectAttributes) {
        try {
            Usuario u = usuarioRepo.findByUsername(auth.getName())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            horarioService.registrarEntrada(u);

            redirectAttributes.addFlashAttribute("success", "Entrada registrada correctamente");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/horarios";
    }

    // 🔹 REGISTRAR SALIDA
    @PostMapping("/salida")
    public String salida(Authentication auth, RedirectAttributes redirectAttributes) {
        try {
            Usuario u = usuarioRepo.findByUsername(auth.getName())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            horarioService.registrarSalida(u);

            redirectAttributes.addFlashAttribute("success", "Salida registrada correctamente");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/horarios";
    }

    // 🔹 VER HORARIOS
    @GetMapping
    public String verHorarios(Authentication auth, Model model) {

        Usuario u = usuarioRepo.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

       
        if (u.getRol().name().equals("ADMIN")) {
            model.addAttribute("horarios", horarioService.todos());
        } else {
            model.addAttribute("horarios", horarioService.misHorarios(u));
        }

       
        String estado = horarioService.estado(u);
        model.addAttribute("estado", estado);

  
        boolean enLinea = estado.equals("En línea");
        model.addAttribute("enLinea", enLinea);

        return "horarios";
    }
}
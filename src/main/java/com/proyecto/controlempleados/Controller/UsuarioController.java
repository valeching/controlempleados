package com.proyecto.controlempleados.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.proyecto.controlempleados.model.Usuario;
import com.proyecto.controlempleados.repository.UsuarioRepository;

@Controller
@RequestMapping("/admin/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String listarUsuarios(Model model){

        model.addAttribute("usuarios", usuarioRepository.findAll());

        return "usuarios/lista";
    }

    @GetMapping("/nuevo")
    public String nuevoUsuario(Model model){

        model.addAttribute("usuario", new Usuario());

        return "usuarios/form";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario){

        usuarioRepository.save(usuario);

        return "redirect:/admin/usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id){

        usuarioRepository.deleteById(id);

        return "redirect:/admin/usuarios";
    }

}
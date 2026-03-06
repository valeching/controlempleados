package com.proyecto.controlempleados.Init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.proyecto.controlempleados.model.Rol;
import com.proyecto.controlempleados.Repository.UsuarioRepository;
import com.proyecto.controlempleados.service.UsuarioService;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository repo;
    private final UsuarioService service;

    public DataInitializer(UsuarioRepository repo, UsuarioService service) {
        this.repo = repo;
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {

        // Crear administrador si no existe
        if (!repo.existsByUsername("admin")) {
            service.crearUsuario("admin", "admin123", Rol.ADMIN);
            System.out.println("Usuario ADMIN creado");
        }

        // Crear usuario empleado si no existe
        if (!repo.existsByUsername("empleado")) {
            service.crearUsuario("empleado", "empleado123", Rol.USER);
            System.out.println("Usuario EMPLEADO creado");
        }

    }
}

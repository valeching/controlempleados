package com.proyecto.controlempleados.service;

import com.proyecto.controlempleados.model.Empleado;
import com.proyecto.controlempleados.repository.EmpleadoRepository;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoService {

    private final EmpleadoRepository repo;

    public EmpleadoService(EmpleadoRepository repo) {
        this.repo = repo;
    }

    public List<Empleado> listar(){
        return repo.findAll();
    }

    public Empleado crear(Empleado e){
        return repo.save(e);
    }

    public Empleado buscarPorId(Long id){
        return repo.findById(id).orElse(null);
    }

    public void eliminar(Long id){
        repo.deleteById(id);
    }

    // VALIDACIONES

    public boolean existeCedula(String cedula){
        return repo.existsByCedula(cedula);
    }

    public boolean existeCorreo(String correo){
        return repo.existsByCorreo(correo);
    }

    public boolean existeTelefono(String telefono){
        return repo.existsByTelefono(telefono);
    }
}
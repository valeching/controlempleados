package com.proyecto.controlempleados.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.proyecto.controlempleados.model.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    boolean existsByCedula(String cedula);

    boolean existsByCorreo(String correo);

    boolean existsByTelefono(String telefono);

}
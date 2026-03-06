package com.proyecto.controlempleados.Repository;

import com.proyecto.controlempleados.model.Empleado;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long>{

    Optional<Empleado> findByNombre(String nombre);

}

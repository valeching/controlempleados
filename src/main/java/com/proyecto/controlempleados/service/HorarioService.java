package com.proyecto.controlempleados.service;

import com.proyecto.controlempleados.model.Horario;
import com.proyecto.controlempleados.model.Usuario;
import com.proyecto.controlempleados.repository.HorarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HorarioService {

    private final HorarioRepository horarioRepo;

    public HorarioService(HorarioRepository horarioRepo) {
        this.horarioRepo = horarioRepo;
    }

    // 🔹 REGISTRAR ENTRADA
    public void registrarEntrada(Usuario usuario) {

        if (horarioRepo.findTopByUsuarioAndHoraSalidaIsNullOrderByHoraEntradaDesc(usuario).isPresent()) {
            throw new RuntimeException("Ya tienes una entrada sin salida");
        }

        Horario h = new Horario();
        h.setUsuario(usuario);
        h.setHoraEntrada(LocalDateTime.now());

        horarioRepo.save(h);
    }

    // 🔹 REGISTRAR SALIDA
    public void registrarSalida(Usuario usuario) {

        Horario h = horarioRepo
        .findTopByUsuarioAndHoraSalidaIsNullOrderByHoraEntradaDesc(usuario)
        .orElseThrow(() -> new RuntimeException("No hay entrada registrada"));

    
        if (h.getHoraSalida() != null) {
                throw new RuntimeException("Esta entrada ya tiene salida");
        }

        
        LocalDateTime salida = LocalDateTime.now();

        if (salida.isBefore(h.getHoraEntrada())) {
            throw new RuntimeException("La salida no puede ser antes de la entrada");
        }

        h.setHoraSalida(salida);
        horarioRepo.save(h);
    }

    
    public List<Horario> misHorarios(Usuario usuario) {
        return horarioRepo.findByUsuario(usuario);
    }

    
    public List<Horario> todos() {
        return horarioRepo.findAll();
    }

    
    public String estado(Usuario usuario) {
        boolean enLinea = horarioRepo
                .findTopByUsuarioAndHoraSalidaIsNullOrderByHoraEntradaDesc(usuario)
                .isPresent();

        return enLinea ? "En línea" : "Fuera de línea";
    }
}

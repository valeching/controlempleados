package com.proyecto.controlempleados.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.proyecto.controlempleados.model.Empleado;
import com.proyecto.controlempleados.service.EmpleadoService;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("empleados", empleadoService.listar());
        return "empleados";
    }

    @GetMapping("/guardar")
    public String mostrarFormularioCreacion(Model model) {
        model.addAttribute("empleado", new Empleado());
        return "empleado-form";
    }

    @PostMapping("/guardar")
    public String crear(@ModelAttribute("empleado") Empleado empleado,
                        BindingResult br,
                        Model model) {

        if (br.hasErrors()) {
            return "empleado-form";
        }

        if (empleado.getId() == null) {

            if (empleadoService.existeCedula(empleado.getCedula())) {
                model.addAttribute("error", "Esa cédula ya existe");
                return "empleado-form";
            }

            if (empleadoService.existeCorreo(empleado.getCorreo())) {
                model.addAttribute("error", "Ese correo ya existe");
                return "empleado-form";
            }

            if (empleadoService.existeTelefono(empleado.getTelefono())) {
                model.addAttribute("error", "Ese teléfono ya existe");
                return "empleado-form";
            }
        }

        empleadoService.crear(empleado);

        return "redirect:/empleados";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {

        Empleado empleado = empleadoService.buscarPorId(id);

        model.addAttribute("empleado", empleado);

        return "empleado-form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {

        empleadoService.eliminar(id);

        return "redirect:/empleados";
    }
}
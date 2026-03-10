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
    // Para mostrar el formulario de creación de un nuevo empleado, simplemente pasamos un nuevo objeto Empleado al modelo para que se pueda llenar en el formulario
    @GetMapping("/guardar")
    public String mostrarFormularioCreacion(Model model) {
        model.addAttribute("empleado", new Empleado());
        return "empleado-form";
    }
    // Para guardar un nuevo empleado, recibimos el objeto Empleado desde el formulario y lo pasamos al servicio para crear el empleado
    @PostMapping("/guardar")
    public String crear(@ModelAttribute("empleado") Empleado empleado, BindingResult br) {

        if (br.hasErrors()){
            return "empleado-form";
        }

        empleadoService.crear(empleado);

        return "redirect:/empleados";
    }
     // Para mostrar el formulario de edición de un empleado existente, primero obtenemos el empleado por su ID y lo pasamos al modelo para que se pueda llenar en el formulario
    @GetMapping("/editar/{id}")
       public String editar(@PathVariable Long id, Model model){

        Empleado empleado = empleadoService.buscarPorId(id);

        model.addAttribute("empleado", empleado);

        return "empleado-form";

    }   
     // Para eliminar un empleado, simplemente recibimos el ID del empleado a eliminar y lo pasamos al servicio para eliminarlo
    @GetMapping("/eliminar/{id}")
        public String eliminar(@PathVariable Long id){

        empleadoService.eliminar(id);

        
        return "redirect:/empleados";

    }

}

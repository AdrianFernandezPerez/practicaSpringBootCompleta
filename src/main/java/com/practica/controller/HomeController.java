package com.practica.controller;

import com.practica.model.Perfil;
import com.practica.model.Usuario;
import com.practica.model.Vacante;
import com.practica.service.ICategoriasService;
import com.practica.service.IUsuariosService;
import com.practica.service.IVacantesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private IVacantesService serviceVacantes;

    @Autowired
    private IUsuariosService serviceUsuarios;

    @Autowired
    private ICategoriasService serviceCategorias;

    @GetMapping("/tabla")
    public String mostrarTabla(Model model){
        List<Vacante> lista = serviceVacantes.buscarTodas();
        model.addAttribute("vacantes", lista);
        return "tabla";
    }

    @GetMapping("/detalle")
    public String mostrarDetalle(Model model){
        Vacante vacante = new Vacante();
        vacante.setNombre("Ingeniero de comunicaciones");
        vacante.setDescripcion("Se solicita ingeniero para dar soporte a intranet");
        vacante.setFecha(new Date());
        vacante.setSalario(9700.0);
        model.addAttribute("vacante", vacante);
        return "detalle";
    }

    @GetMapping("/listado")
    public String mostrarListado(Model model){
        List<String> lista = new LinkedList<>();
        lista.add("Ingeniero de Sistemas");
        lista.add("Auxiliar de Contabilidad");
        lista.add("Vendedor");
        lista.add("Arquitecto");
        model.addAttribute("empleos", lista);

        return "listado";
    }

    @GetMapping("/")
    public String mostrarHome(Model model){
        return "home";
    }

    @GetMapping("/signup")
    public String registrarse(Usuario usuario){
        return "usuarios/formRegistro";
    }

    @PostMapping("/signup")
    public String guardarRegistro(Usuario usuario, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            for(ObjectError error : result.getAllErrors()){
                System.out.println("Ocurrio un error: "+ error.getDefaultMessage());
            }
            return "vacantes/formVacante";
        }
        usuario.setEstatus(1);
        usuario.setFechaRegistro(new Date());
        Perfil perfil = new Perfil();
        perfil.setId(3);
        usuario.agregar(perfil);
        serviceUsuarios.guardar(usuario);
        attributes.addFlashAttribute("msg", "Usuario Registrado");
        System.out.println("Usuario: " + usuario);
        return "redirect:/usuarios/index";
    }

    @GetMapping("/search")
    public String buscar(@ModelAttribute("search") Vacante vacante, Model model){
        System.out.println("Buscando por: " + vacante);
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("descripcion", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Vacante> example = Example.of(vacante, matcher);
        List<Vacante> lista = serviceVacantes.buscarByExample(example);
        model.addAttribute("vacantes", lista);
        return "home";
    }

    @ModelAttribute
    public void setGenericos(Model model){
        Vacante vacanteSearch = new Vacante();
        vacanteSearch.reset();
        model.addAttribute("vacantes", serviceVacantes.buscarDestacadas());
        model.addAttribute("categorias", serviceCategorias.buscarTodas());
        model.addAttribute("search", vacanteSearch);
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}

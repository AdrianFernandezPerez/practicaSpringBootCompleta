package com.practica.controller;

import com.practica.model.Categoria;
import com.practica.service.ICategoriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping(value = "/categorias")
public class CategoriasController {

    @Autowired
    @Qualifier("categoriasServiceJpa")
    private ICategoriasService serviceCategorias;

    @GetMapping("/index")
    public String mostrarIndex(Model model) {
        List<Categoria> lista = serviceCategorias.buscarTodas();
        model.addAttribute("categorias", lista);
        return "categorias/listCategorias";
    }

    @GetMapping("/create")
    public String crear(Categoria categoria) {
        return "categorias/formCategoria";
    }

    @PostMapping("/save")
    public String guardar(Categoria categoria, BindingResult result, RedirectAttributes attributes) {
        if(result.hasErrors()){
            for(ObjectError error : result.getAllErrors()){
                System.out.println("Ocurrio un error: "+ error.getDefaultMessage());
            }
            return "categorias/formCategoria";
        }
        serviceCategorias.guardar(categoria);
        attributes.addFlashAttribute("msg", "Registro Guardado");
        System.out.println("Categoria: " + categoria);
        return "redirect:/categorias/index";
    }

    @GetMapping("/delete/{id}")
    public String eliminar(@PathVariable("id") int idCategoria, RedirectAttributes attributes){
        System.out.println("Borrando categoria con id: " + idCategoria);
        serviceCategorias.eliminar(idCategoria);
        attributes.addFlashAttribute("msg", "La categoria fue eliminada!");
        return "redirect:/categorias/index";
    }

}

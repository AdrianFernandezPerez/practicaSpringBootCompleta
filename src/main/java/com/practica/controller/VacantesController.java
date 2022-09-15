package com.practica.controller;

import com.practica.model.Vacante;
import com.practica.service.ICategoriasService;
import com.practica.service.IVacantesService;
import com.practica.util.Utileria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/vacantes")
public class VacantesController {

    //Coge el valor de application.properties de la variable asignada
    @Value("${empleosapp.ruta.imagenes}")
    private String ruta;

    @Autowired
    private IVacantesService serviceVacantes;

    @Autowired
    @Qualifier("categoriasServiceJpa")
    private ICategoriasService serviceCategorias;

    @GetMapping("/index")
    public String mostrarIndex(Model model){
        List<Vacante> lista = serviceVacantes.buscarTodas();
        model.addAttribute("vacantes", lista);
        return "vacantes/listVacantes";
    }

    @GetMapping(value = "/indexPaginate")
    public String mostrarIndexPaginado(Model model, Pageable page){
        Page<Vacante>lista = serviceVacantes.buscarTodas(page);
        model.addAttribute("vacantes", lista);
        return "vacantes/listVacantes";
    }

    @GetMapping("/create")
    public String crear(Vacante vacante, Model model){
        return "vacantes/formVacante";
    }

    /**
    @PostMapping("/save")
    public String guardar(@RequestParam("nombre") String nombre, @RequestParam("descripcion") String descripcion,
                          @RequestParam("estatus") String estatus, @RequestParam("fecha") String fecha,
                          @RequestParam("destacado") int destacado, @RequestParam("salario") double salario,
                          @RequestParam("detalles") String detalles){
        System.out.println("Nombre Vacante: " + nombre);
        System.out.println("Descripcion: " + descripcion);
        System.out.println("Estatus: " + estatus);
        System.out.println("Fecha Publicación: " + fecha);
        System.out.println("Destacado: " + destacado);
        System.out.println("Salario Ofrecido: " + salario);
        System.out.println("Detalles: " + detalles);
        return "vacantes/listVacantes";
    }
     */
    @PostMapping("/save")
    public String guardar(Vacante vacante, BindingResult result, RedirectAttributes attributes, @RequestParam("archivoImagen") MultipartFile multiPart){
        if(result.hasErrors()){
            for(ObjectError error : result.getAllErrors()){
                System.out.println("Ocurrio un error: "+ error.getDefaultMessage());
            }
            return "vacantes/formVacante";
        }
        if(!multiPart.isEmpty()){
            //String ruta = "/Users/adriancallejo/Desktop/empleos/img-vacantes/";
            String nombreImagen = Utileria.guardarArchivo(multiPart, ruta);
            if (nombreImagen != null){
                //Procesamos la variable nombreImagen
                vacante.setImagen(nombreImagen);
            }
        }
        serviceVacantes.guardar(vacante);
        attributes.addFlashAttribute("msg", "Registro Guardado");
        System.out.println("Vacante: " + vacante);
        return "redirect:/vacantes/index";
    }

    @GetMapping("/edit/{id}")
    public String editar(@PathVariable("id") int idVacante, Model model){
        Vacante vacante = serviceVacantes.buscarPorId(idVacante);
        model.addAttribute("vacante", vacante);
        return "vacantes/formVacante";
    }

    @GetMapping("/delete/{id}")
    public String eliminar(@PathVariable("id") int idVacante, RedirectAttributes attributes){
        System.out.println("Borrando vacante con id: " + idVacante);
        serviceVacantes.eliminar(idVacante);
        attributes.addFlashAttribute("msg", "La vacante fue eliminada!");
        return "redirect:/vacantes/index";
   }

    @GetMapping("/view/{id}")
    public String verDetalle(@PathVariable("id") int idVacante, Model model){

        Vacante vacante = serviceVacantes.buscarPorId(idVacante);
        //System.out.println("IdVacante: " + idVacante);
        System.out.println("Vacante: " + vacante);
        model.addAttribute("vacante", vacante);
        return "detalle";
    }

    @ModelAttribute
    public void setGenericos(Model model){
        model.addAttribute("categorias", serviceCategorias.buscarTodas());
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

}

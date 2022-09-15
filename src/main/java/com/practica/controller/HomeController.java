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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    //Damos uso al objeto auth para obtener datos del usuario iniciado
    @GetMapping("/index")
    public String mostrarIndex(Authentication auth, HttpSession session){
        String username = auth.getName();
        System.out.println("Nombre del usuario: " + username);
        for(GrantedAuthority rol: auth.getAuthorities()){
            System.out.println("ROL: " + rol.getAuthority());
        }

        if(session.getAttribute("Usuario") == null){
            Usuario usuario = serviceUsuarios.buscarPorUsername(username);
            usuario.setPassword(null);
            System.out.println("Usuario: " + usuario);
            session.setAttribute("usuario", usuario);
        }
        return "redirect:/";
    }

    @GetMapping("/signup")
    public String registrarse(Usuario usuario){
        return "usuarios/formRegistro";
    }

    @PostMapping("/signup")
    public String guardarRegistro(Usuario usuario, BindingResult result, RedirectAttributes attributes){

        String pwdPlano = usuario.getPassword();
        String pwdEncriptado = passwordEncoder.encode(pwdPlano);
        usuario.setPassword(pwdEncriptado);

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

    @GetMapping("/login")
    public String mostrarLogin(){
        return "formLogin";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        SecurityContextLogoutHandler logoutHandler =
                new SecurityContextLogoutHandler();
        logoutHandler.logout(request, null, null);
        return "redirect:/login";
    }

    @GetMapping("/bcrypt/{texto}")
    @ResponseBody
    public String encriptar(@PathVariable("texto") String texto){
        return texto + " Encriptado en Bcrypt: " + passwordEncoder.encode(texto);
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}

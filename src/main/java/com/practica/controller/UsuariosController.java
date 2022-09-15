package com.practica.controller;

import com.practica.model.Usuario;
import com.practica.service.db.UsuariosServiceJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private UsuariosServiceJpa usuariosServiceJpa;

    @GetMapping("/index")
	public String mostrarIndex(Model model) {
        List<Usuario> usuarios = usuariosServiceJpa.buscarTodos();
        model.addAttribute("usuarios", usuarios);
    	return "usuarios/listUsuarios";
	}
    
    @GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idUsuario, RedirectAttributes attributes) {
        System.out.println("Borrando usuario con id: " + idUsuario);
        usuariosServiceJpa.eliminar(idUsuario);
        attributes.addFlashAttribute("msg", "El usuario fue eliminado!");
		return "redirect:/usuarios/index";
	}
}

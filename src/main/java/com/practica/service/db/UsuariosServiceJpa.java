package com.practica.service.db;

import com.practica.model.Usuario;
import com.practica.repository.UsuariosRepository;
import com.practica.repository.VacantesRepository;
import com.practica.service.IUsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
public class UsuariosServiceJpa implements IUsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Override
    public void guardar(Usuario usuario) {
        usuariosRepository.save(usuario);
    }

    @Override
    public void eliminar(Integer idUsuario) {
        usuariosRepository.deleteById(idUsuario);
    }

    @Override
    public List<Usuario> buscarTodos() {
        return usuariosRepository.findAll();
    }
}

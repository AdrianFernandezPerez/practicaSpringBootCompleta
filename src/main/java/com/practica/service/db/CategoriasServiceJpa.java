package com.practica.service.db;

import com.practica.model.Categoria;
import com.practica.repository.CategoriasRepository;
import com.practica.service.ICategoriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class CategoriasServiceJpa implements ICategoriasService {

    @Autowired
    private CategoriasRepository categoriasRepository;

    @Override
    public void guardar(Categoria categoria) {
        categoriasRepository.save(categoria);
    }

    @Override
    public List<Categoria> buscarTodas() {
        return categoriasRepository.findAll();
    }

    @Override
    public Categoria buscarPorId(Integer idCategoria) {
        Optional<Categoria> optional = categoriasRepository.findById(idCategoria);
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public void eliminar(Integer idCategoria) {
        categoriasRepository.deleteById(idCategoria);
    }
}

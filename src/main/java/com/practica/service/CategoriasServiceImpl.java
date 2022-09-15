package com.practica.service;

import com.practica.model.Categoria;
import com.practica.repository.CategoriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CategoriasServiceImpl implements ICategoriasService{

    private List<Categoria> lista = null;

    @Autowired
    private CategoriasRepository categoriasRepo;

    public CategoriasServiceImpl() {
        lista = new LinkedList<Categoria>();
            Categoria categoria1 = new Categoria();
            categoria1.setId(1);
            categoria1.setNombre("Recursos Humanos");
            categoria1.setDescripcion("Trabajos relacionados con el area de RH.");

            Categoria categoria2 = new Categoria();
            categoria2.setId(2);
            categoria2.setNombre("Ventas");
            categoria2.setDescripcion("Ofertas de trabajo relacionado con ventas.");

            Categoria categoria3 = new Categoria();
            categoria3.setId(3);
            categoria3.setNombre("Arquitectura");
            categoria3.setDescripcion("Diseño de planos en general y trabajos relacionados.");

            lista.add(categoria1);
            lista.add(categoria2);
            lista.add(categoria3);
    }

    @Override
    public void guardar(Categoria categoria) {
        lista.add(categoria);
    }

    @Override
    public List<Categoria> buscarTodas() {
        return lista;
    }

    @Override
    public Categoria buscarPorId(Integer idCategoria) {
        for (Categoria categoria: lista) {
            if (categoria.getId()==idCategoria){
                return categoria;
            }
        }
        return null;
    }

    @Override
    public void eliminar(Integer idCategoria) {
        categoriasRepo.deleteById(idCategoria);
    }
}

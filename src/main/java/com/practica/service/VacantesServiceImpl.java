package com.practica.service;

import com.practica.model.Vacante;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

@Service
public class VacantesServiceImpl implements IVacantesService{

    private List<Vacante> lista = null;

    public VacantesServiceImpl(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        lista = new LinkedList<Vacante>();
        try{
            Vacante vacante1 = new Vacante();
            vacante1.setId(1);
            vacante1.setNombre("Ingeniero Civil");
            vacante1.setDescripcion("Solicitamos Ing. Civil para diseñar puente peatonal");
            vacante1.setFecha(sdf.parse("08-02-2019"));
            vacante1.setSalario(8500.0);
            vacante1.setDestacado(1);
            vacante1.setImagen("empresa1.png");

            Vacante vacante2 = new Vacante();
            vacante2.setId(2);
            vacante2.setNombre("Contador Publico");
            vacante2.setDescripcion("Solicitamos Ing. Civil para diseñar puente peatonal");
            vacante2.setFecha(sdf.parse("08-02-2019"));
            vacante2.setSalario(9000.0);
            vacante2.setDestacado(0);
            vacante2.setImagen("empresa2.png");

            Vacante vacante3 = new Vacante();
            vacante3.setId(3);
            vacante3.setNombre("Ingeniero Eléctrico");
            vacante3.setDescripcion("Solicitamos Ing. Civil para diseñar puente peatonal");
            vacante3.setFecha(sdf.parse("08-02-2019"));
            vacante3.setSalario(10000.0);
            vacante3.setDestacado(0);

            Vacante vacante4 = new Vacante();
            vacante4.setId(4);
            vacante4.setNombre("Diseñador Gráfico");
            vacante4.setDescripcion("Solicitamos Ing. Civil para diseñar puente peatonal");
            vacante4.setFecha(sdf.parse("08-02-2019"));
            vacante4.setSalario(9500.0);
            vacante4.setDestacado(1);
            vacante4.setImagen("empresa3.png");

            lista.add(vacante1);
            lista.add(vacante2);
            lista.add(vacante3);
            lista.add(vacante4);
        } catch (ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public List<Vacante> buscarTodas() {
        return lista;
    }

    @Override
    public Vacante buscarPorId(Integer idVacante) {
        for(Vacante v : lista){
            if(v.getId()==idVacante){
                return v;
            }
        }
        return null;
    }

    @Override
    public void guardar(Vacante vacante) {
        lista.add(vacante);
    }

    @Override
    public List<Vacante> buscarDestacadas() {
        return null;
    }

    @Override
    public void eliminar(Integer idVacante) {

    }

    @Override
    public List<Vacante> buscarByExample(Example<Vacante> example) {
        return null;
    }

    @Override
    public Page<Vacante> buscarTodas(Pageable page) {
        return null;
    }


}

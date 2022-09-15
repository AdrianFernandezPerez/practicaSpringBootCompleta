package com.practica.service.db;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import net.itinajero.model.Solicitud;
import net.itinajero.service.ISolicitudesService;

public class SolicitudesServiceJpa implements ISolicitudesService {

	@Override
	public void guardar(Solicitud solicitud) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminar(Integer idSolicitud) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Solicitud> buscarTodas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Solicitud buscarPorId(Integer idSolicitud) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Solicitud> buscarTodas(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}	

}

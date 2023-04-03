package com.pessoal.cobranca.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.pessoal.cobranca.model.StatusTitulo;
import com.pessoal.cobranca.model.Titulo;
import com.pessoal.cobranca.repository.TituloRepository;
import com.pessoal.cobranca.repository.filter.TituloFilter;

@Service
public class CadastroTituloService {

	@Autowired
	private TituloRepository tituloRepository;

	public void salvar(Titulo titulo) {
		try {
			tituloRepository.save(titulo);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data invalido!");
		}

	}

	public void excluir(Long codigo) {
		tituloRepository.deleteById(codigo);

	}

	public String receber(Long codigo) {

		@SuppressWarnings("deprecation")
		Titulo titulo = tituloRepository.getOne(codigo);
		titulo.setStatus(StatusTitulo.PAGO);
		tituloRepository.save(titulo);
		return StatusTitulo.PAGO.getDescricao();

	}

	public List<Titulo> filtrar(TituloFilter filtro){
	
		LocalDate dataInicial = filtro.getDataInicial();
		
		LocalDate dataFinal = filtro.getDataFinal();
		
	
		if(dataInicial == null && dataFinal == null) { 
			
			String descricao = filtro.getDescricao() == null ? "" : filtro.getDescricao();
			
			return tituloRepository.findByDescricaoContaining(descricao);
			
		}

		
		
		return tituloRepository.findByStartDateBetween(dataInicial, dataFinal);
	}

}

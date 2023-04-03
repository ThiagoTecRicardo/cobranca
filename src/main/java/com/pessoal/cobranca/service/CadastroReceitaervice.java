package com.pessoal.cobranca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.pessoal.cobranca.model.Receita;
import com.pessoal.cobranca.model.StatusReceita;
import com.pessoal.cobranca.repository.ReceitaRepository;
import com.pessoal.cobranca.repository.filter.ReceitaFilter;

@Service
public class CadastroReceitaervice {
	
	@Autowired
	private ReceitaRepository receitaRepository;
	
	public void salvar(Receita receita) {
		try {
			receitaRepository.save(receita);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data invalido!");
		}
				
	}

	public void excluir(Long codigo) {
		receitaRepository.deleteById(codigo);
		
	}
	
public String receber(Long codigo) {
		
		@SuppressWarnings("deprecation")
		Receita receita = receitaRepository.getOne(codigo);
		receita.setStatus(StatusReceita.RECEBIDO);
		receitaRepository.save(receita);
		return StatusReceita.RECEBIDO.getDescricao();
		
	}

public List<Receita> filtrar(ReceitaFilter filtro) {
	String descricao = filtro.getDescricao() == null ? "" : filtro.getDescricao();
	return receitaRepository.findByDescricaoContaining(descricao);
}

}

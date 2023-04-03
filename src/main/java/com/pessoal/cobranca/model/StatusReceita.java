package com.pessoal.cobranca.model;

public enum StatusReceita {
	
	PENDENTE("Pendente"),
	RECEBIDO("Recebido");
	
	private String descricao;
	
	StatusReceita(String descricao) {
		this.descricao = descricao;
	}
	 
	 public String getDescricao() {
		 return descricao;
	 }

}

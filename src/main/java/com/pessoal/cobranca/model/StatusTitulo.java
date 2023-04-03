package com.pessoal.cobranca.model;

public enum StatusTitulo {
	
	PENDENTE("Pendente"),
	PAGO("Pago");
	
	private String descricao;
	
	 StatusTitulo(String descricao) {
		this.descricao = descricao;
	}
	 
	 public String getDescricao() {
		 return descricao;
	 }

}

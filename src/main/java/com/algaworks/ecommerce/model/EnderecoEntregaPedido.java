package com.algaworks.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class EnderecoEntregaPedido {
	
	@Column(length = 9)
	public String cep;
	
	@Column(length = 100)
	public String logradouro;
	
	@Column(length = 10)
	public String numero;
	
	@Column(length = 50)
	public String complemento;
	
	@Column(length = 50)
	public String bairro;
	
	@Column(length = 50)
	public String cidade;
	
	@Column(length = 2)
	public String estado;

}
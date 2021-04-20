package com.algaworks.ecommerce.model;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Endereco {
	
	public String cep;
	public String logradouro;
	public String numero;
	public String complemento;
	public String bairro;
	public String cidade;
	public String estado;

}
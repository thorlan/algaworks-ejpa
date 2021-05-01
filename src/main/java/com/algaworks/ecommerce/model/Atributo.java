package com.algaworks.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Atributo {

	@Column(nullable = false, length = 100)
	private String nome;
	
	private String valor;
	
}

package com.algaworks.ecommerce.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "estoque")
public class Estoque extends EntitadeBaseInteger{
	
	@OneToOne(optional = false)
	@JoinColumn(name = "produto_id")
	private Produto produto;
	
	private Integer quantidade;
	
}

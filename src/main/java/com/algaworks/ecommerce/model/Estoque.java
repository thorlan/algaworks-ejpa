package com.algaworks.ecommerce.model;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "estoque")
public class Estoque extends EntidadeBaseInteger{
	
	@OneToOne(optional = false)
	@JoinColumn(name = "produto_id", nullable = false, foreignKey = @ForeignKey(name ="fk_estoque_produto"))
	private Produto produto;
	
	private Integer quantidade;
	
}

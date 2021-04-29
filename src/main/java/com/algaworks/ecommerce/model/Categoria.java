package com.algaworks.ecommerce.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "categoria")
public class Categoria extends EntitadeBaseInteger{
	
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "categoria_pai_id")
	private Categoria categoriaPai;
	
	@Column(name = "categorias")
	@OneToMany(mappedBy = "categoriaPai")
	private List<Categoria> categoriasFilha;
	
	@ManyToMany(mappedBy = "categorias")
	private List<Produto> produtos;
	
}

package com.algaworks.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.algaworks.ecommerce.listener.GenericoListener;

import lombok.Getter;
import lombok.Setter;

@EntityListeners({ GenericoListener.class })
@Entity
@Getter
@Setter
@Table(name = "produto")
public class Produto extends EntitadeBaseInteger {

	@Column(name = "data_criacao", updatable = false)
	private LocalDateTime dataCriacao;

	@Column(name = "data_ultima_atualizacao", insertable = false)
	private LocalDateTime dataUltimaAtualizacao;

	private String nome;

	private String descricao;

	private BigDecimal preco;

	@OneToMany(mappedBy = "produto")
	private List<ItemPedido> itemPedido;

	@ManyToMany
	@JoinTable(name = "produto_categoria", joinColumns = @JoinColumn(name = "produto_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"))
	private List<Categoria> categorias;

	@OneToOne
	@JoinColumn(name = "produto")
	private Estoque estoque;
	
	@ElementCollection
	@CollectionTable(name = "produto_tag", 
					joinColumns = @JoinColumn(name = "produto_id"))
	@Column(name = "tag")
	private List<String> tags;
	
	@ElementCollection
	@CollectionTable(name = "produto_atributo", 
					joinColumns = @JoinColumn(name = "produto_id"))
	private List<Atributo> atributos;
	
	@Lob
	private byte[] foto;

}

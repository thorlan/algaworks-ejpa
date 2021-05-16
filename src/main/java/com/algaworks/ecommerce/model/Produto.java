package com.algaworks.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.algaworks.ecommerce.listener.GenericoListener;

import lombok.Getter;
import lombok.Setter;

@EntityListeners({ GenericoListener.class })
@Entity
@Getter
@Setter
@NamedQueries({
	@NamedQuery(name ="Produto.listar", query = "select p from Produto p")
	
})
@Table(name = "produto", 
		uniqueConstraints = { @UniqueConstraint(name = "unq_nome", columnNames = { "nome" })},
		indexes = { @Index(name = "idx_nome", columnList = "nome")})
public class Produto extends EntitadeBaseInteger {

	@Column(name = "data_criacao", updatable = false, nullable = false, columnDefinition = "datetime(6)")
	private LocalDateTime dataCriacao;

	@Column(name = "data_ultima_atualizacao", insertable = false)
	private LocalDateTime dataUltimaAtualizacao;

	@Column(name = "nome", length = 100)
	private String nome;

	@Column(columnDefinition = "varchar(275) not null default 'descricao'")
	private String descricao;

	private BigDecimal preco;

	@OneToMany(mappedBy = "produto")
	private List<ItemPedido> itemPedido;

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "produto_categoria",
			joinColumns = @JoinColumn(name = "produto_id", nullable = false, foreignKey = @ForeignKey(name ="fk_produto_categoria_produto")),
			inverseJoinColumns = @JoinColumn(name = "categoria_id", nullable = false, foreignKey = @ForeignKey(name ="fk_produto_categoria_categoria")))
	private List<Categoria> categorias;

	@OneToOne
	@JoinColumn(name = "produto")
	private Estoque estoque;
	
	@ElementCollection
	@CollectionTable(name = "produto_tag", 
					joinColumns = @JoinColumn(name = "produto_id", nullable = false, foreignKey = @ForeignKey(name ="fk_produto_tag_produto")))
	@Column(name = "tag", length = 50, nullable = false)
	private List<String> tags;
	
	@ElementCollection
	@CollectionTable(name = "produto_atributo", 
					joinColumns = @JoinColumn(name = "produto_id", nullable = false, foreignKey = @ForeignKey(name ="fk_produto_atributo_produto")))
	private List<Atributo> atributos;
	
	@Lob
	private byte[] foto;

}

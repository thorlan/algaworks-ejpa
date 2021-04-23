package com.algaworks.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "pedido")
public class Pedido {
	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;
	
	@Column(name = "data_pedido")
	public LocalDateTime dataPedido;
	
	@Column(name = "data_conclusao")
	public LocalDateTime dataConclusao;
	
	@OneToOne(mappedBy = "pedido")
	public NotaFiscal notaFiscal;
	
	public BigDecimal total;
	
	@Enumerated(EnumType.STRING)
	public StatusPedido status;
	
	@Embedded
	private Endereco enderecoEntrega;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@OneToMany(mappedBy = "pedido")
	private List<ItemPedido> itens;
	
	@OneToOne(mappedBy = "pedido")
	private PagamentoCartao pagamentoCartao;
	
}

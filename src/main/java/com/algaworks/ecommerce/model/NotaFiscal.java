package com.algaworks.ecommerce.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "nota_fiscal")
public class NotaFiscal {
	
	@EqualsAndHashCode.Include
	@Id
	@Column(name = "pedido_id")
	private Integer id;
	
	@MapsId
	@OneToOne(optional = false)
	@JoinColumn(name = "pedido_id")
//	@JoinTable(name = "pedido_nota_fiscal", 
//				joinColumns = @JoinColumn(name = "nota_fiscal_id", unique = true),
//				inverseJoinColumns = @JoinColumn(name = "pedido_id", unique = true))
	private Pedido pedido;
	
	@Lob
	private byte[] xml;
	
	@Column(name = "data_emissao")
	private Date dataEmissao;
}

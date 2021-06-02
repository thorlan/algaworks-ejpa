package com.algaworks.ecommerce.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "nota_fiscal")
public class NotaFiscal extends EntidadeBaseInteger{
	
	@MapsId
	@OneToOne(optional = false)
	@JoinColumn(name = "pedido_id", nullable = false, foreignKey = @ForeignKey(name ="fk_nota_fiscal_pedido"))
//	@JoinTable(name = "pedido_nota_fiscal", 
//				joinColumns = @JoinColumn(name = "nota_fiscal_id", unique = true),
//				inverseJoinColumns = @JoinColumn(name = "pedido_id", unique = true))
	private Pedido pedido;
	
	@Lob
	@Column(columnDefinition = "longblob", nullable = false)
	private byte[] xml;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_emissao", columnDefinition = "datetime(6)", nullable = false)
	private Date dataEmissao;
}

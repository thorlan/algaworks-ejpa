package com.algaworks.ecommerce.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
//@DiscriminatorColumn(name = "tipo_pagamento", discriminatorType = DiscriminatorType.STRING)
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@Table(name = "pagamento")
public abstract class Pagamento extends EntitadeBaseInteger {
	
	@MapsId
	@OneToOne(optional = false)
	@JoinColumn(name = "pedido_id")
	private Pedido pedido;
	
	@Enumerated(EnumType.STRING)
	private StatusPagamento status;
}

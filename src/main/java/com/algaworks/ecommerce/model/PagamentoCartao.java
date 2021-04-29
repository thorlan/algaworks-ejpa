package com.algaworks.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
//@DiscriminatorValue("Cartao")
@Setter
@Entity
@Table(name = "pagamento_cartao")
public class PagamentoCartao extends Pagamento {
	
	@Column(name = "numero_cartao")
	private String numeroCartao;
}

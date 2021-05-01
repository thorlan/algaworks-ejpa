package com.algaworks.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@DiscriminatorValue("cartao")
@Setter
@Entity
public class PagamentoCartao extends Pagamento {
	
	@Column(name = "numero_cartao", length = 50)
	private String numeroCartao;
}

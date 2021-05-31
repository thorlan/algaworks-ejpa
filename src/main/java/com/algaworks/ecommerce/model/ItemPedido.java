package com.algaworks.ecommerce.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@SqlResultSetMappings({
	@SqlResultSetMapping(name = "item_pedido-produto.ItemPedido-Produto", 
			entities = {@EntityResult(entityClass = ItemPedido.class),@EntityResult(entityClass = Produto.class)})
})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "item_pedido")
public class ItemPedido {
	
	@EmbeddedId
	private ItemPedidoId id;
	
	@MapsId("pedidoId")
	@ManyToOne(optional = false)
	@JoinColumn(name = "pedido_id", nullable = false, foreignKey = @ForeignKey(name ="fk_item_pedido_pedido"))
	private Pedido pedido;
	
	@MapsId("produtoId")
	@ManyToOne(optional = false)
	@JoinColumn(name = "produto_id", nullable = false, foreignKey = @ForeignKey(name ="fk_item_pedido_produto"))
	private Produto produto;
	
	@Column(name = "preco_produto", precision = 19, scale = 2, nullable = false)
	private BigDecimal precoProduto;
	
	@Column(columnDefinition = "integer", nullable = false)
	private Integer quantidade;
}

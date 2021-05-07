package com.algaworsk.ecommerce.cascata;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.ItemPedido;
import com.algaworks.ecommerce.model.ItemPedidoId;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class CascadeTypeDeleteTest extends EntityManagerTest {

	@Test
	public void removerRelacaoProdutoCategoria() {
		Produto produto = em.find(Produto.class, 1);

		Assert.assertFalse(produto.getCategorias().isEmpty());

		em.getTransaction().begin();
		produto.getCategorias().clear();
		em.getTransaction().commit();

		em.clear();

		Produto produtoVerificacao = em.find(Produto.class, produto.getId());
		Assert.assertTrue(produtoVerificacao.getCategorias().isEmpty());
	}

	// @Test
	public void removerPedidoEItens() {
		Pedido pedido = em.find(Pedido.class, 1);

		em.getTransaction().begin();
		em.remove(pedido); // Necessário CascadeType.REMOVE no atributo "itens".
		em.getTransaction().commit();

		em.clear();

		Pedido pedidoVerificacao = em.find(Pedido.class, pedido.getId());
		Assert.assertNull(pedidoVerificacao);
	}

	//@Test
	public void removerItemPedidoEPedido() {
		ItemPedido itemPedido = em.find(ItemPedido.class, new ItemPedidoId(1, 1));

		em.getTransaction().begin();
		em.remove(itemPedido); // Necessário CascadeType.REMOVE no atributo "pedido".
		em.getTransaction().commit();

		em.clear();

		Pedido pedidoVerificacao = em.find(Pedido.class, itemPedido.getPedido().getId());
		Assert.assertNull(pedidoVerificacao);
	}
}
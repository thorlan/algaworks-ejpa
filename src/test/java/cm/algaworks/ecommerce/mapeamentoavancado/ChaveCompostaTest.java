package cm.algaworks.ecommerce.mapeamentoavancado;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.ItemPedido;
import com.algaworks.ecommerce.model.ItemPedidoId;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;
import com.algaworks.ecommerce.model.StatusPedido;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class ChaveCompostaTest extends EntityManagerTest{

	@Test
	public void salvarItem() {
		em.getTransaction().begin();
		
		Cliente cliente = em.find(Cliente.class, 1);
		Produto produto = em.find(Produto.class, 1);
		
		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);
		pedido.setDataCriacao(LocalDateTime.now());
		pedido.setStatus(StatusPedido.AGUARDANDO);
		pedido.setTotal(produto.getPreco());

		em.persist(pedido);
		
		em.flush();
		
		ItemPedido itemPedido = new ItemPedido();
		//setar produtoId e pepdidoId vai dar cagada quando rodar?
		itemPedido.setId(new ItemPedidoId());
		itemPedido.setPedido(pedido);
		itemPedido.setProduto(produto);
		itemPedido.setPrecoProduto(produto.getPreco());
		itemPedido.setQuantidade(1);
		
		em.persist(itemPedido);
		
		em.getTransaction().commit();
		
		em.clear();
		
		Pedido pedidoVerificacao = em.find(Pedido.class, pedido.getId());
		Assert.assertNotNull(pedidoVerificacao);
		Assert.assertFalse(pedidoVerificacao.getItens().isEmpty());
	}
	
	@Test
	public void buscarItem() {
		ItemPedido itemPedido = em.find(ItemPedido.class, new ItemPedidoId(1,1));
		Assert.assertNotNull(itemPedido);
	}
}

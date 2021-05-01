package relacionamentos;

import java.math.BigDecimal;
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

public class RelacionamentoManyToOneTest extends EntityManagerTest{

	@Test
	public void verificaRelacionamento() {
		Cliente cliente = em.find(Cliente.class, 1);
		
		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);
		pedido.setStatus(StatusPedido.AGUARDANDO);
		pedido.setDataCriacao(LocalDateTime.now());
		pedido.setTotal(BigDecimal.TEN);
		
		em.getTransaction().begin();
		em.persist(pedido);
		em.getTransaction().commit();
		
		em.clear();
		
		Pedido pedidoVerificacao = em.find(Pedido.class, pedido.getId());
		
		Assert.assertNotNull(pedidoVerificacao);
		Assert.assertNotNull(pedidoVerificacao.getCliente());
	}
	
	@Test
	public void verificaRelacionamentoItemPedido() {
		em.getTransaction().begin();
		
		Cliente cliente = em.find(Cliente.class, 1);
		Produto produto = em.find(Produto.class, 1);
		
		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);
		pedido.setStatus(StatusPedido.AGUARDANDO);
		pedido.setDataCriacao(LocalDateTime.now());
		pedido.setTotal(BigDecimal.TEN);
		em.persist(pedido);
		
		ItemPedido itemPedido = new ItemPedido();
		itemPedido.setPrecoProduto(produto.getPreco());
		itemPedido.setQuantidade(1);
		itemPedido.setPedido(pedido);
		itemPedido.setProduto(produto);
		itemPedido.setId(new ItemPedidoId(pedido.getId(), produto.getId()));
		
		em.persist(itemPedido);
		em.getTransaction().commit();
		
		em.clear();
		
		ItemPedido itemPedidoVerificacao = em.find(ItemPedido.class, new ItemPedidoId(pedido.getId(), produto.getId()));
		
		Assert.assertNotNull(itemPedidoVerificacao);
		Assert.assertNotNull(itemPedidoVerificacao.getProduto());
		Assert.assertNotNull(itemPedidoVerificacao.getPedido());
		
	}
}

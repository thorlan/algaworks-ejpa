package cm.algaworks.ecommerce.iniciandocomjpa;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.EnderecoEntregaPedido;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.StatusPedido;

public class MapeamentoObjetoEmbutido extends EntityManagerTest {

	@Test
	public void analisaMapeamentoObjetoEmbutido() { 
		
		Cliente cliente = em.find(Cliente.class, 1);
		
		EnderecoEntregaPedido endereco = new EnderecoEntregaPedido();
		endereco.setBairro("Neves");
		endereco.setCep("24425-300");
		endereco.setCidade("São Gonçalo");
		endereco.setComplemento("-");
		endereco.setEstado("RJ");
		endereco.setLogradouro("Rua Mauricio de Abreu");
		endereco.setNumero("10000000");
		
		Pedido pedido = new Pedido();
		pedido.setDataCriacao(LocalDateTime.now());
		pedido.setStatus(StatusPedido.AGUARDANDO);
		pedido.setTotal(new BigDecimal(1000));
		pedido.setDataCriacao(LocalDateTime.now());
		
		pedido.setEnderecoEntregaPedido(endereco);
		
		pedido.setCliente(cliente);
		
		em.getTransaction().begin();
		em.persist(pedido);
		em.getTransaction().commit();
		
		em.clear();
		
		Pedido pedidoVerificacao = em.find(Pedido.class, pedido.getId());
		
		Assert.assertEquals(pedido.getEnderecoEntregaPedido().getBairro(), pedidoVerificacao.getEnderecoEntregaPedido().getBairro());
	}
	
	
}

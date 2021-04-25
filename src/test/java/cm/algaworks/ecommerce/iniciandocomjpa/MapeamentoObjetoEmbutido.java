package cm.algaworks.ecommerce.iniciandocomjpa;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Endereco;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.StatusPedido;

public class MapeamentoObjetoEmbutido extends EntityManagerTest {

	@Test
	public void analisaMapeamentoObjetoEmbutido() { 
		
		Cliente cliente = entityManager.find(Cliente.class, 1);
		
		Endereco endereco = new Endereco();
		endereco.setBairro("Neves");
		endereco.setCep("24425-300");
		endereco.setCidade("São Gonçalo");
		endereco.setComplemento("-");
		endereco.setEstado("Rio de Janeiro");
		endereco.setLogradouro("Rua Mauricio de Abreu");
		endereco.setNumero("10000000");
		
		Pedido pedido = new Pedido();
		pedido.setDataCriacao(LocalDateTime.now());
		pedido.setStatus(StatusPedido.AGUARDANDO);
		pedido.setTotal(new BigDecimal(1000));
		
		pedido.setEnderecoEntrega(endereco);
		
		pedido.setCliente(cliente);
		
		entityManager.getTransaction().begin();
		entityManager.persist(pedido);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
		
		Assert.assertEquals(pedido.getEnderecoEntrega().getBairro(), pedidoVerificacao.getEnderecoEntrega().getBairro());
	}
	
	
}

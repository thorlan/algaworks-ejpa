package relacionamentos;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.NotaFiscal;
import com.algaworks.ecommerce.model.PagamentoCartao;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.StatusPagamento;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class RelacionamentoOneToOneTest extends EntityManagerTest{

	@Test
	public void verificaRelacionamento() {
		
		Pedido pedido = entityManager.find(Pedido.class, 1);
		PagamentoCartao pagamentoCartao = new PagamentoCartao();
		pagamentoCartao.setNumero("1234");
		pagamentoCartao.setStatus(StatusPagamento.PROCESSANDO);
		pagamentoCartao.setPedido(pedido);
		
		entityManager.getTransaction().begin();
		entityManager.persist(pagamentoCartao);
		entityManager.getTransaction().commit();
		entityManager.clear();
		
		Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
		
		Assert.assertNotNull(pedidoVerificacao.getPagamentoCartao());
	}
	
	@Test
	public void verificaRelacionamentoPedidoNotaFiscal() {
		
		Pedido pedido = entityManager.find(Pedido.class, 1);
		NotaFiscal nota = new NotaFiscal();
		nota.setXml("TESTE");
		nota.setDataEmissao(new Date());
		nota.setPedido(pedido);
		
		entityManager.getTransaction().begin();
		entityManager.persist(nota);
		entityManager.getTransaction().commit();
		entityManager.clear();
		
		Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
		
		Assert.assertNotNull(pedidoVerificacao.getNotaFiscal());
	}
	

}

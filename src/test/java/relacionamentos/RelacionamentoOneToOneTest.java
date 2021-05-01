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
		
		em.getTransaction().begin();
		
		Pedido pedido = em.find(Pedido.class, 1);
		PagamentoCartao pagamentoCartao = new PagamentoCartao();
		pagamentoCartao.setNumeroCartao("1234");
		pagamentoCartao.setStatus(StatusPagamento.PROCESSANDO);
		pagamentoCartao.setPedido(pedido);
		
		em.persist(pagamentoCartao);
		em.getTransaction().commit();
		em.clear();
		
		Pedido pedidoVerificacao = em.find(Pedido.class, pedido.getId());
		
		Assert.assertNotNull(pedidoVerificacao.getPagamento());
	}
	
	@Test
	public void verificaRelacionamentoPedidoNotaFiscal() {
		
		Pedido pedido = em.find(Pedido.class, 1);
		NotaFiscal nota = new NotaFiscal();
		nota.setXml("TESTE".getBytes());
		nota.setDataEmissao(new Date());
		nota.setPedido(pedido);
		nota.setDataEmissao(new Date());
		
		em.getTransaction().begin();
		em.persist(nota);
		em.getTransaction().commit();
		em.clear();
		
		Pedido pedidoVerificacao = em.find(Pedido.class, pedido.getId());
		
		Assert.assertNotNull(pedidoVerificacao.getNotaFiscal());
	}
	

}

package jpql;

import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.NotaFiscal;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;
import com.algaworks.ecommerce.model.StatusPagamento;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class PassandoParametrosTest extends EntityManagerTest {
	

	@Test
	public void usandoParametros() {
	    //   String jpql = "select p from Pedido p join p.itens i where i.id.produtoId = 1";
		//	 String jpql = "select p from Pedido p join p.itens i where i.produto.id = 1";
		//	 String jpql = "select p from Pedido p join p.itens i join i.produto pro where pro.id = 1";
		
		String jpql = "Select p from Pedido p join p.pagamento pag where p.id = :pedidoId and pag.status = :pagamentoStatus";
		int pedidoId = 2;
		
		TypedQuery<Pedido> typedQuery = em.createQuery(jpql, Pedido.class);
		typedQuery.setParameter("pedidoId", pedidoId);
		typedQuery.setParameter("pagamentoStatus", StatusPagamento.PROCESSANDO);
		
		List<Pedido> pedidos = typedQuery.getResultList();
		
		Assert.assertTrue(pedidos.size() == 1);
	}
	
	@Test
	public void usandoParametroDate() {
	    //   String jpql = "select p from Pedido p join p.itens i where i.id.produtoId = 1";
		//	 String jpql = "select p from Pedido p join p.itens i where i.produto.id = 1";
		//	 String jpql = "select p from Pedido p join p.itens i join i.produto pro where pro.id = 1";
		
		String jpql = "Select nf from NotaFiscal nf where nf.dataEmissao <= ?1";
		
		TypedQuery<NotaFiscal> typedQuery = em.createQuery(jpql, NotaFiscal.class);
		typedQuery.setParameter(1, new Date(), TemporalType.TIMESTAMP);
		
		List<NotaFiscal> notas = typedQuery.getResultList();
		
		Assert.assertTrue(notas.size() == 1);
	}
	
	
}

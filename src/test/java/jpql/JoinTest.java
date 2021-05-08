package jpql;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Pedido;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class JoinTest extends EntityManagerTest {
	
	@Test
	public void usarJoinFetch() {
		//join fetch faz o jpql ir ao banco e pegar tudo direto, diferente do normal que so pegaria o itens quando usasse
		//p.getItens() no java
		String jpql = "Select p from Pedido p "
					+ "join fetch p.itens "
					+ "left join fetch p.pagamento "
					+ "join fetch p.cliente "
					+ "left join fetch p.notaFiscal ";
		
		TypedQuery<Pedido> typedQuery = em.createQuery(jpql, Pedido.class);
		
		List<Pedido> pedidos = typedQuery.getResultList();
		Assert.assertFalse(pedidos.isEmpty());
	}
	
	
	@Test
	public void fazerLeftJoin() {
		String jpql = "Select p from Pedido p left join p.pagamento pag on pag.status = 'PROCESSANDO'";
		
		TypedQuery<Object[]> typedQuery = em.createQuery(jpql, Object[].class);
		
		List<Object[]> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
	}
	
	@Test
	public void fazerJoin() {
		String jpql = "Select p, i from Pedido p join p.itens i";
		
		TypedQuery<Object[]> typedQuery = em.createQuery(jpql, Object[].class);
		
		List<Object[]> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
	}
	
}

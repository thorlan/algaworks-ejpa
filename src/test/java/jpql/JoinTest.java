package jpql;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class JoinTest extends EntityManagerTest {
	
	@Test
	public void fazerJoin() {
		String jpql = "Select p, i from Pedido p join p.itens i";
		
		TypedQuery<Object[]> typedQuery = em.createQuery(jpql, Object[].class);
		
		List<Object[]> lista = typedQuery.getResultList();
		Assert.assertTrue(lista.size() == 3);
		
	}
}

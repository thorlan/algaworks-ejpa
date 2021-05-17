package jpql;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Pedido;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class OperadoresLogicosTest extends EntityManagerTest {

	@Test
	public void usarOperadores() {

		String jpql = "select p from Pedido p "
					+ "where p.total > 500 "
					+ "and p.status = 'AGUARDANDO' "
					+ "and p.cliente.id = 1 "
					+ "or p.status = 'PAGO'";

		TypedQuery<Pedido> typedQuery = em.createQuery(jpql, Pedido.class);

		List<Pedido> pedidos = typedQuery.getResultList();

		Assert.assertFalse(pedidos.isEmpty());
		
		Assert.assertTrue(pedidos.get(0).getId() == 1);

	}

	

}

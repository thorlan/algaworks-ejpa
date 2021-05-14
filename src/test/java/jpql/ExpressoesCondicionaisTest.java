package jpql;

import java.util.Arrays;
import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class ExpressoesCondicionaisTest extends EntityManagerTest{

	@Test
	public void usarExpressaoINListOfObjetos() {

		// util para condicionar os agrupamentos
		//total de vendas por categoria
		//quero somente as categorias que vendem > 100
		// no having so as condicoes de agrupamento ou group by
		Cliente cliente1 = em.find(Cliente.class, 1);
		Cliente cliente2 = em.find(Cliente.class, 2);
		
		List<Cliente> clientes = Arrays.asList(cliente1, cliente2);
		
		String jpql = "select p from Pedido p where p.cliente in (:clientes)";

		TypedQuery<Pedido> typedQuery = em.createQuery(jpql, Pedido.class);
		typedQuery.setParameter("clientes", clientes);

		List<Pedido> lista = typedQuery.getResultList();

		Assert.assertFalse(lista.isEmpty());

		lista.forEach(pedido -> System.out.println(pedido));
	}
	
	@Test
	public void usarExpressaoIN() {

		// util para condicionar os agrupamentos
		//total de vendas por categoria
		//quero somente as categorias que vendem > 100
		// no having so as condicoes de agrupamento ou group by
		
		List<Integer> pedidosId = Arrays.asList(1,3,4);
		String jpql = "select p from Pedido p where p.id in(:lista)";

		TypedQuery<Pedido> typedQuery = em.createQuery(jpql, Pedido.class);
		typedQuery.setParameter("lista", pedidosId);

		List<Pedido> lista = typedQuery.getResultList();

		Assert.assertFalse(lista.isEmpty());

		lista.forEach(pedido -> System.out.println(pedido));
	}
	
	
	//@Test
	public void usarExpressaoCase() {

		// util para condicionar os agrupamentos
		//total de vendas por categoria
		//quero somente as categorias que vendem > 100
		// no having so as condicoes de agrupamento ou group by
		
		String jpql = "select p.id, "
				+ "case p.status "
					+ "when 'PAGO' then 'Está pago' "
					+ "when 'CANCELADO' then 'Foi cancelado' "
					+ " else 'Está aguardando' "
				+ "end "
				+ " from Pedido p";

		TypedQuery<Object[]> typedQuery = em.createQuery(jpql, Object[].class);

		List<Object[]> lista = typedQuery.getResultList();

		Assert.assertFalse(lista.isEmpty());

		lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
	}
}

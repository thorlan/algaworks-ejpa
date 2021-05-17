package jpql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class ExpressoesCondicionaisTest extends EntityManagerTest {
	
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

	@Test
	public void expressaoLikeParaPrimeiroNome() {

		String jpql = "select c from Cliente c where c.nome like concat(:nome, '%')";

		TypedQuery<Cliente> typedQuery = em.createQuery(jpql, Cliente.class);
		typedQuery.setParameter("nome", "Fernando");

		List<Cliente> clientes = typedQuery.getResultList();

		Assert.assertFalse(clientes.isEmpty());

		Assert.assertTrue(clientes.get(0).getNome().startsWith("Fernando"));
	}

	@Test
	public void expressaoLikeParaSegundoNome() {

		String jpql = "select c from Cliente c where c.nome like concat('%' , :nome)";

		TypedQuery<Cliente> typedQuery = em.createQuery(jpql, Cliente.class);
		typedQuery.setParameter("nome", "Medeiros");

		List<Cliente> clientes = typedQuery.getResultList();

		Assert.assertFalse(clientes.isEmpty());

		Assert.assertTrue(clientes.get(0).getNome().endsWith("Medeiros"));
	}

	@Test
	public void expressaoLikeParaNomesQueContenhamALetraA() {

		String jpql = "select c from Cliente c where c.nome like concat('%' , :nome , '%')";

		TypedQuery<Cliente> typedQuery = em.createQuery(jpql, Cliente.class);
		typedQuery.setParameter("nome", "a");

		List<Cliente> clientes = typedQuery.getResultList();

		Assert.assertFalse(clientes.isEmpty());

		Assert.assertTrue(clientes.get(0).getNome().contains("a"));
	}

	@Test
	public void isNull() {

		String jpql = "select p from Produto p where p.foto is null";

		TypedQuery<Produto> typedQuery = em.createQuery(jpql, Produto.class);

		List<Produto> produtos = typedQuery.getResultList();

		Assert.assertFalse(produtos.isEmpty());

		Assert.assertTrue(produtos.get(0).getFoto() == null);
	}

	@Test
	public void isEmpty() {

		String jpql = "select p from Produto p where p.categorias is empty";

		TypedQuery<Produto> typedQuery = em.createQuery(jpql, Produto.class);

		List<Produto> produtos = typedQuery.getResultList();

		Assert.assertFalse(produtos.isEmpty());

		Assert.assertTrue(produtos.get(0).getCategorias().isEmpty());
	}

	@Test
	public void usarMaiorMenor() {

		String jpql = "select p from Produto p where p.preco >= :preco";

		TypedQuery<Produto> typedQuery = em.createQuery(jpql, Produto.class);
		typedQuery.setParameter("preco", new BigDecimal(499));

		List<Produto> produtos = typedQuery.getResultList();

		Assert.assertFalse(produtos.isEmpty());

		Assert.assertTrue(produtos.get(0).getPreco().doubleValue() >= 499);
	}

	@Test
	public void usarMaiorMenorComDatas() {

		String jpql = "select p from Pedido p where p.dataCriacao >= :data";

		LocalDateTime hoje = LocalDateTime.now();

		TypedQuery<Pedido> typedQuery = em.createQuery(jpql, Pedido.class);
		typedQuery.setParameter("data", hoje.minusDays(2));

		List<Pedido> pedidos = typedQuery.getResultList();

		Assert.assertFalse(pedidos.isEmpty());

		System.out.println("printaaaando");
		pedidos.forEach(p -> System.out.println(p.getId()));

		// Assert.assertTrue(pedidos.get(0).getPreco().doubleValue() >= 499);
	}

	@Test
	public void usarBetweenPreco() {

		String jpql = "select p from Produto p where " + "p.preco between :precoInicial and :precoFinal";

		TypedQuery<Produto> typedQuery = em.createQuery(jpql, Produto.class);
		typedQuery.setParameter("precoInicial", new BigDecimal(499));
		typedQuery.setParameter("precoFinal", new BigDecimal(1400));

		List<Produto> produtos = typedQuery.getResultList();

		Assert.assertFalse(produtos.isEmpty());

		Assert.assertTrue(produtos.get(0).getPreco().doubleValue() >= 499);
		Assert.assertTrue(produtos.get(0).getPreco().doubleValue() <= 1400);
	}

	@Test
	public void usarBetweenData() {

		String jpql = "select p from Pedido p where " + "p.dataCriacao between :dataInicial and :dataFinal";

		LocalDateTime hoje = LocalDateTime.now();

		TypedQuery<Pedido> typedQuery = em.createQuery(jpql, Pedido.class);
		typedQuery.setParameter("dataInicial", hoje.minusDays(2));
		typedQuery.setParameter("dataFinal", hoje);

		List<Pedido> pedidos = typedQuery.getResultList();

		Assert.assertFalse(pedidos.isEmpty());

	}

	@Test
	public void usarDiferente() {

		String jpql = "select p from Produto p where " + "p.id <> 1";

		TypedQuery<Produto> typedQuery = em.createQuery(jpql, Produto.class);

		List<Produto> produtos = typedQuery.getResultList();

		Assert.assertFalse(produtos.isEmpty());

		Assert.assertTrue(produtos.size() >= 1);

	}

}
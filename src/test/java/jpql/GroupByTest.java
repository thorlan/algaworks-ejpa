package jpql;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class GroupByTest extends EntityManagerTest {

	// @Test
	public void agruparResultado() {

		String jpql = "select c.nome, count(p.id) from Categoria c join c.produtos p group by c.id ";

		jpql = "select concat(count(p.id), '-' , year(p.dataCriacao), '/', function('monthname', p.dataCriacao)), sum(p.total) "
				+ "from Pedido p " + "group by year(p.dataCriacao), month(p.dataCriacao) ";

		jpql = "select c.nome, sum(ip.precoProduto) from ItemPedido ip " + " join ip.produto pro "
				+ " join pro.categorias c " + " group by c.id";

		TypedQuery<Object[]> typedQuery = em.createQuery(jpql, Object[].class);

		List<Object[]> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(arr -> System.out.println(arr[0] + " - " + arr[1]));

	}

	// @Test
	public void buscarTotalDeVendasPorCliente() {

		String jpql = "select c.nome, sum(ip.precoProduto) from ItemPedido ip " + " join ip.pedido p "
				+ "join p.cliente c " + " group by c.id";

		TypedQuery<Object[]> typedQuery = em.createQuery(jpql, Object[].class);

		List<Object[]> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(arr -> System.out.println(arr[0] + " tem um total de " + arr[1] + " vendas"));

	}

	// @Test
	public void totalDeVendasPorDiaEPorCategoria() {

		String jpql = "select " + " concat(year(p.dataCriacao), '/', month(p.dataCriacao), '/', day(p.dataCriacao)), "
				+ " concat(c.nome, ': ', sum(ip.precoProduto)) "
				+ " from ItemPedido ip join ip.pedido p join ip.produto pro join pro.categorias c "
				+ " group by year(p.dataCriacao), month(p.dataCriacao), day(p.dataCriacao), c.id "
				+ " order by p.dataCriacao, c.nome ";

		TypedQuery<Object[]> typedQuery = em.createQuery(jpql, Object[].class);

		List<Object[]> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(arr -> System.out.println(arr[0] + " - " + arr[1]));

	}

	// @Test
	public void agruparEFiltrarResultado() {
//	         Total de vendas por mês.
//	        String jpql = "select concat(year(p.dataCriacao), '/', function('monthname', p.dataCriacao)), sum(p.total) " +
//	                " from Pedido p " +
//	                " where year(p.dataCriacao) = year(current_date) " + //so do ano atual!
//	                " group by year(p.dataCriacao), month(p.dataCriacao) ";

//	         Total de vendas por categoria.
//	        String jpql = "select c.nome, sum(ip.precoProduto) from ItemPedido ip " +
//	                " join ip.produto pro join pro.categorias c join ip.pedido p " +
//	                " where year(p.dataCriacao) = year(current_date) and month(p.dataCriacao) = month(current_date) " +
//	                " group by c.id";

//	        Total de vendas por cliente
		String jpql = "select c.nome, sum(ip.precoProduto) from ItemPedido ip "
				+ " join ip.pedido p join p.cliente c join ip.pedido p "
				+ " where year(p.dataCriacao) = year(current_date) and month(p.dataCriacao) >= (month(current_date) - 3) "
				+ " group by c.id " + "";

		TypedQuery<Object[]> typedQuery = em.createQuery(jpql, Object[].class);

		List<Object[]> lista = typedQuery.getResultList();

		Assert.assertFalse(lista.isEmpty());

		lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
	}

	@Test
	public void condicionarAgrupamentoComHaving() {

		// util para condicionar os agrupamentos
		//total de vendas por categoria
		//quero somente as categorias que vendem > 100
		// no having so as condicoes de agrupamento ou group by
		
		String jpql = "select cat.nome, sum(ip.precoProduto) from ItemPedido ip "
				+ "join ip.produto pro join pro.categorias cat "
				+ "group by cat.id "
				+ "having sum(ip.precoProduto) > 1500";

		TypedQuery<Object[]> typedQuery = em.createQuery(jpql, Object[].class);

		List<Object[]> lista = typedQuery.getResultList();

		Assert.assertFalse(lista.isEmpty());

		lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
	}

}

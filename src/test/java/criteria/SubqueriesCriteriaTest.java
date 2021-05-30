package criteria;

import com.algaworks.ecommerce.model.*;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.math.BigDecimal;
import java.util.List;

public class SubqueriesCriteriaTest extends EntityManagerTest {

	//gopro e camera 80D q tem q retornar!
	@Test
	public void pesquisarComAllExercicio() {
		//todos os produtos que sempre foram vendidos pelo mesmo preco
		//select distintc p from ItemPedido ip join ip.produto p
		//where ip.precoProduto = ALL
		//(select precoProduto from ItemPedido where produto = p and id <> ip.id)
		
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<ItemPedido> root = criteriaQuery.from(ItemPedido.class);
		Join<ItemPedido, Produto> joinItemPedidoProduto = root.join(ItemPedido_.produto);

		criteriaQuery.select(root.get(ItemPedido_.produto)).distinct(true);

		Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
		Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
		subquery.select(subqueryRoot.get(ItemPedido_.precoProduto));
		
		subquery.where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), joinItemPedidoProduto)
				, criteriaBuilder.notEqual(root.get(ItemPedido_.id), subqueryRoot.get(ItemPedido_.id))
				);

		criteriaQuery.where(criteriaBuilder.equal(root.get(ItemPedido_.precoProduto), criteriaBuilder.all(subquery)));

		TypedQuery<Produto> typedQuery = em.createQuery(criteriaQuery);

		List<Produto> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println("ID: " + obj.getId() + " " + obj.getNome()));
		
	}
	
	//@Test
	public void pesquisarComAny02() {
//	        Todos os produtos que já foram vendidos por um preco diferente do atual
//	        String jpql = "select p from Produto p " +
//	                " where p.preco <> ANY (select precoProduto from ItemPedido where produto = p)";

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.select(root);

		Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
		Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
		subquery.select(subqueryRoot.get(ItemPedido_.precoProduto));
		subquery.where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root));

		criteriaQuery.where(criteriaBuilder.notEqual(root.get(Produto_.preco), criteriaBuilder.any(subquery)));

		TypedQuery<Produto> typedQuery = em.createQuery(criteriaQuery);

		List<Produto> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println("ID: " + obj.getId()));

	}

	//@Test
	public void pesquisarComAny01() {
//	        Todos os produtos que já foram vendidos, pelo menos, uma vez pelo preço atual.
//	        String jpql = "select p from Produto p " +
//	                " where p.preco = ANY (select precoProduto from ItemPedido where produto = p)";

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.select(root);

		Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
		Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
		subquery.select(subqueryRoot.get(ItemPedido_.precoProduto));
		subquery.where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root));

		criteriaQuery.where(criteriaBuilder.equal(root.get(Produto_.preco), criteriaBuilder.any(subquery)));

		TypedQuery<Produto> typedQuery = em.createQuery(criteriaQuery);

		List<Produto> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println("ID: " + obj.getId()));

	}

//	@Test
	public void pesquisarComAll02() {
//        Todos os produtos não foram vendidos mais depois que encareceram
//        String jpql = "select p from Produto p where " +
//                " p.preco > ALL (select precoProduto from ItemPedido where produto = p)";
//                " and exists (select 1 from ItemPedido where produto = p)";

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.select(root);

		Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
		Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
		subquery.select(subqueryRoot.get(ItemPedido_.precoProduto));
		subquery.where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root));

		criteriaQuery.where(criteriaBuilder.greaterThan(root.get(Produto_.preco), criteriaBuilder.all(subquery)),
				criteriaBuilder.exists(subquery));

		TypedQuery<Produto> typedQuery = em.createQuery(criteriaQuery);

		List<Produto> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
	}

//	@Test
	public void pesquisarComAll01() {
//        Todos os produtos que SEMPRE foram vendidos pelo preco atual.
//        String jpql = "select p from Produto p where " +
//                " p.preco = ALL (select precoProduto from ItemPedido where produto = p)";

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.select(root);

		Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
		Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
		subquery.select(subqueryRoot.get(ItemPedido_.precoProduto));
		subquery.where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root));

		criteriaQuery.where(criteriaBuilder.equal(root.get(Produto_.preco), criteriaBuilder.all(subquery)));

		TypedQuery<Produto> typedQuery = em.createQuery(criteriaQuery);

		List<Produto> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
	}

	// retornar todos os produtos que ja foram vendidos por preco diferente do atual
	// produtos com preco diferente do itemPedido
	// @Test
	public void pesquisarComExistsExercicioo() {

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.select(root);

		Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
		Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
		subquery.select(criteriaBuilder.literal(1));

		Predicate precoProdutoNotEqualItemPedidoPreco = criteriaBuilder.notEqual(root.get(Produto_.preco),
				subqueryRoot.get(ItemPedido_.precoProduto));
		Predicate equal = criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root);

		subquery.where(criteriaBuilder.and(equal, precoProdutoNotEqualItemPedidoPreco));

		criteriaQuery.where(criteriaBuilder.exists(subquery));

		TypedQuery<Produto> typedQuery = em.createQuery(criteriaQuery);

		List<Produto> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
	}

	// @Test
	public void pesquisarComInExercicio() {
		// retornar todos os pedidos que contenham um produto da categoria de id = 2
		// select p from Pedido join Produto pro join Categoria c where c.id in (select
		// c2.id from Categoria c2);

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);

		criteriaQuery.select(root);

		Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
		Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);

		Join<ItemPedido, Produto> subqueryJoinProduto = subqueryRoot.join(ItemPedido_.produto);
		Join<Produto, Categoria> subqueryJoinProdutoCategoria = subqueryJoinProduto.join(Produto_.categorias);

		subquery.select(subqueryRoot.get(ItemPedido_.id).get(ItemPedidoId_.pedidoId));
		subquery.where(criteriaBuilder.equal(subqueryJoinProdutoCategoria.get(Categoria_.id), 2));

		criteriaQuery.where(root.get(Pedido_.id).in(subquery));

		TypedQuery<Pedido> typedQuery = em.createQuery(criteriaQuery);

		List<Pedido> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
	}

	// @Test
	public void perquisarComSubqueryExercicio() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
		Root<Cliente> root = criteriaQuery.from(Cliente.class);

		criteriaQuery.select(root);

		Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
		Root<Pedido> subqueryRoot = subquery.from(Pedido.class);
		subquery.select(criteriaBuilder.count(criteriaBuilder.literal(1)));
		subquery.where(criteriaBuilder.equal(subqueryRoot.get(Pedido_.cliente), root));

		criteriaQuery.where(criteriaBuilder.greaterThan(subquery, 2L));

		TypedQuery<Cliente> typedQuery = em.createQuery(criteriaQuery);

		List<Cliente> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Nome: " + obj.getNome()));
	}

	// @Test
	public void pesquisarSubqueriesComExists() {
		// todos os produtos que ja foram vendidos
		// select p from Produto p where exists
		// (select 1 from ItemPedido ip2 join ip2.produto p2 where p2 = p)

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.select(root);

		Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
		Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
		subquery.select(criteriaBuilder.literal(1));

		subquery.where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root));

		criteriaQuery.where(criteriaBuilder.exists(subquery));

		TypedQuery<Produto> typedQuery = em.createQuery(criteriaQuery);

		List<Produto> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
	}

	// @Test
	public void pesquisarSubqueriesComIn() {
		// select p from Pedido p where p.id in
		// (select p2.id from ItemPedido i2 join i2.pedido join i2.produto pro2 where
		// pro2.preco > 100)

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);

		criteriaQuery.select(root);

		Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
		Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
		Join<ItemPedido, Pedido> joinItemPedidoPedido = subqueryRoot.join(ItemPedido_.pedido);
		Join<ItemPedido, Produto> joinItemProduto = subqueryRoot.join(ItemPedido_.produto);

		subquery.select(joinItemPedidoPedido.get(Pedido_.id));
		subquery.where(criteriaBuilder.greaterThan(joinItemProduto.get(Produto_.preco), new BigDecimal(100)));

		// TODO: TESTAR ASSIM NO PSAT VE SE FUNCIONA!!!
		// LA ESTOU COMPARANDO SE UM ARRAY ESTA EM OUTRO ARRAY, TENTAR USAR SE INT IN
		// ARRAY!

		criteriaQuery.where(root.get(Pedido_.id).in(subquery));

		TypedQuery<Pedido> typedQuery = em.createQuery(criteriaQuery);

		List<Pedido> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
	}

	// @Test
	public void pesquisarSubqueries03() {
//         bons clientes
		// select c from Cliente c where
		// 500 < (select sum(p.total) from Pedido p where p.cliente = c)

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
		Root<Cliente> root = criteriaQuery.from(Cliente.class);

		criteriaQuery.select(root);

		Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
		Root<Pedido> subqueryRoot = subquery.from(Pedido.class);
		subquery.select(criteriaBuilder.sum(subqueryRoot.get(Pedido_.total)));
		subquery.where(criteriaBuilder.equal(subqueryRoot.get(Pedido_.cliente), root));

		criteriaQuery.where(criteriaBuilder.greaterThan(subquery, new BigDecimal(1300)));

		TypedQuery<Cliente> typedQuery = em.createQuery(criteriaQuery);

		List<Cliente> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Total: " + obj.getNome()));
	}

	// @Test
	public void pesquisarSubqueries02() {
//         Todos os pedidos acima da média de vendas
//        String jpql = "select p from Pedido p where " +
//                " p.total > (select avg(total) from Pedido)";

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);

		criteriaQuery.select(root);

		Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
		Root<Pedido> subqueryRoot = subquery.from(Pedido.class);
		subquery.select(criteriaBuilder.avg(subqueryRoot.get(Pedido_.total)).as(BigDecimal.class));

		criteriaQuery.where(criteriaBuilder.greaterThan(root.get(Pedido_.total), subquery));

		TypedQuery<Pedido> typedQuery = em.createQuery(criteriaQuery);

		List<Pedido> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Total: " + obj.getTotal()));
	}

	// @Test
	public void pesquisarSubqueries01() {
//         O produto ou os produtos mais caros da base.
//        String jpql = "select p from Produto p where " +
//                " p.preco = (select max(preco) from Produto)";

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.select(root);

		Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
		Root<Produto> subqueryRoot = subquery.from(Produto.class);
		subquery.select(criteriaBuilder.max(subqueryRoot.get(Produto_.preco)));

		criteriaQuery.where(criteriaBuilder.equal(root.get(Produto_.preco), subquery));

		TypedQuery<Produto> typedQuery = em.createQuery(criteriaQuery);

		List<Produto> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out
				.println("ID: " + obj.getId() + ", Nome: " + obj.getNome() + ", Preço: " + obj.getPreco()));
	}
}
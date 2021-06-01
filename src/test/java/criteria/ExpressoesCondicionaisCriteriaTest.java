package criteria;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Cliente_;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Pedido_;
import com.algaworks.ecommerce.model.Produto;
import com.algaworks.ecommerce.model.Produto_;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class ExpressoesCondicionaisCriteriaTest extends EntityManagerTest {

	@Test
	public void usarExpressaoIN02() {
		Cliente cliente01 = em.find(Cliente.class, 1);

		Cliente cliente02 = new Cliente();
		cliente02.setId(2);

		List<Cliente> clientes = Arrays.asList(cliente01, cliente02);

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);

		criteriaQuery.select(root);

		criteriaQuery.where(root.get(Pedido_.cliente).in(clientes));

		TypedQuery<Pedido> typedQuery = em.createQuery(criteriaQuery);

		List<Pedido> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
	}

	@Test
	public void usarExpressaoIN01() {
		List<Integer> ids = Arrays.asList(1, 3, 4, 6);

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);

		criteriaQuery.select(root);

		criteriaQuery.where(root.get(Pedido_.id).in(ids));

		TypedQuery<Pedido> typedQuery = em.createQuery(criteriaQuery);

		List<Pedido> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
	}

	@Test
	public void usarExpressaoCase() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);

		criteriaQuery.multiselect(root.get(Pedido_.id),
//	                criteriaBuilder.selectCase(root.get(Pedido_.STATUS))
//	                        .when(StatusPedido.PAGO.toString(), "Foi pago.")
//	                        .when(StatusPedido.AGUARDANDO.toString(), "Está aguardando.")
//	                        .otherwise(root.get(Pedido_.status))
				criteriaBuilder.selectCase(root.get(Pedido_.pagamento).type().as(String.class))
						.when("boleto", "Foi pago com boleto.").when("cartao", "Foi pago com cartão")
						.otherwise("Não identificado"));

		TypedQuery<Object[]> typedQuery = em.createQuery(criteriaQuery);

		List<Object[]> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
	}

	// @Test
	public void usandoDiferente() {

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);

		criteriaQuery.select(root);

		criteriaQuery.where(criteriaBuilder.notEqual(root.get(Pedido_.total), new BigDecimal(499)));

		TypedQuery<Pedido> typedQuery = em.createQuery(criteriaQuery);

		List<Pedido> lista = typedQuery.getResultList();
		Assert.assertNotNull(lista);
		Assert.assertFalse(lista.isEmpty());
		lista.forEach(p -> System.out.println(p.getId()));

	}

	// @Test
	public void usarBetweenComNumeros() {

		// 499 e
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);

		criteriaQuery.select(root);

		criteriaQuery
				.where(criteriaBuilder.between(root.get(Pedido_.total), new BigDecimal(499), new BigDecimal(2398)));

		TypedQuery<Pedido> typedQuery = em.createQuery(criteriaQuery);

		List<Pedido> lista = typedQuery.getResultList();
		Assert.assertNotNull(lista);
		Assert.assertFalse(lista.isEmpty());
		lista.forEach(p -> System.out.println(p.getId()));

	}

	// @Test
	public void usarMaiorMenorComDatas() {
		// todos os pedidos cadastrados nos ultimos 3 dias
		// serao 2 pedidos com PAGO Q VAO RETORNAR
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);

		criteriaQuery.select(root);

		criteriaQuery.where(
				criteriaBuilder.greaterThanOrEqualTo(root.get(Pedido_.dataCriacao), LocalDateTime.now().minusDays(3)));

		TypedQuery<Pedido> typedQuery = em.createQuery(criteriaQuery);

		List<Pedido> lista = typedQuery.getResultList();
		Assert.assertNotNull(lista);
		Assert.assertFalse(lista.isEmpty());
		Assert.assertEquals(lista.size(), 2);
		lista.forEach(p -> System.out.println(p.getId()));

	}

	// @Test
	public void retornaTodosOsProdutos() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
		Root<Cliente> root = criteriaQuery.from(Cliente.class);

		criteriaQuery.select(root);

		criteriaQuery.where(criteriaBuilder.like(root.get(Cliente_.nome), "%a%"));

		TypedQuery<Cliente> typedQuery = em.createQuery(criteriaQuery);
		List<Cliente> clientes = typedQuery.getResultList();
		Assert.assertNotNull(clientes);
		Assert.assertFalse(clientes.isEmpty());
		clientes.forEach(p -> System.out.println(p.getNome()));
	}

	// @Test
	public void isNull() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.select(root);

		// criteriaQuery.where(root.get(Produto_.foto).isNull());

		criteriaQuery.where(criteriaBuilder.isNull(root.get(Produto_.foto)));

		TypedQuery<Produto> typedQuery = em.createQuery(criteriaQuery);
		List<Produto> lista = typedQuery.getResultList();
		Assert.assertNotNull(lista);
		Assert.assertFalse(lista.isEmpty());
		lista.forEach(p -> System.out.println(p.getNome()));
	}

	// @Test
	public void isEmpty() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.select(root);

		//criteriaQuery.where(criteriaBuilder.isEmpty(root.get(Produto_.itemPedido)));

		TypedQuery<Produto> typedQuery = em.createQuery(criteriaQuery);
		List<Produto> lista = typedQuery.getResultList();
		Assert.assertNotNull(lista);
		Assert.assertFalse(lista.isEmpty());
		lista.forEach(p -> System.out.println(p.getNome()));
	}

	// @Test
	public void maior() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.select(root);

		criteriaQuery.where(criteriaBuilder.greaterThan(root.get(Produto_.preco), new BigDecimal(799)));

		TypedQuery<Produto> typedQuery = em.createQuery(criteriaQuery);
		List<Produto> lista = typedQuery.getResultList();
		Assert.assertNotNull(lista);
		Assert.assertFalse(lista.isEmpty());
		lista.forEach(p -> System.out.println(p.getNome()));
	}

	// @Test
	public void menor() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.select(root);

		criteriaQuery.where(criteriaBuilder.lessThan(root.get(Produto_.preco), new BigDecimal(799)));

		TypedQuery<Produto> typedQuery = em.createQuery(criteriaQuery);
		List<Produto> lista = typedQuery.getResultList();
		Assert.assertNotNull(lista);
		Assert.assertFalse(lista.isEmpty());
		lista.forEach(p -> System.out.println(p.getNome()));
	}

	// @Test
	public void maiorQueMenorQue() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.select(root);

		criteriaQuery.where(criteriaBuilder.greaterThan(root.get(Produto_.preco), new BigDecimal(799)),
				criteriaBuilder.lessThan(root.get(Produto_.preco), new BigDecimal(3500)));

		TypedQuery<Produto> typedQuery = em.createQuery(criteriaQuery);
		List<Produto> lista = typedQuery.getResultList();
		Assert.assertNotNull(lista);
		Assert.assertFalse(lista.isEmpty());
		lista.forEach(p -> System.out.println(p.getNome()));
	}

}

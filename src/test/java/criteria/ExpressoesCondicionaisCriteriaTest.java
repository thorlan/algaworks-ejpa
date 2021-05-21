package criteria;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Cliente_;
import com.algaworks.ecommerce.model.Produto;
import com.algaworks.ecommerce.model.Produto_;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class ExpressoesCondicionaisCriteriaTest extends EntityManagerTest {

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

		criteriaQuery.where(criteriaBuilder.isEmpty(root.get(Produto_.itemPedido)));

		TypedQuery<Produto> typedQuery = em.createQuery(criteriaQuery);
		List<Produto> lista = typedQuery.getResultList();
		Assert.assertNotNull(lista);
		Assert.assertFalse(lista.isEmpty());
		lista.forEach(p -> System.out.println(p.getNome()));
	}

	//@Test
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

	//@Test
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
	
	@Test
	public void maiorQueMenorQue() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.select(root);

		criteriaQuery.where(
				criteriaBuilder.greaterThan(root.get(Produto_.preco), new BigDecimal(799)),
				criteriaBuilder.lessThan(root.get(Produto_.preco), new BigDecimal(3500)));

		TypedQuery<Produto> typedQuery = em.createQuery(criteriaQuery);
		List<Produto> lista = typedQuery.getResultList();
		Assert.assertNotNull(lista);
		Assert.assertFalse(lista.isEmpty());
		lista.forEach(p -> System.out.println(p.getNome()));
	}

}

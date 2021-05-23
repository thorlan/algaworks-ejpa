package criteria;

import java.util.List;

import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.dto.ProdutoDTO;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Cliente_;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class BasicoCriteriaTest extends EntityManagerTest {

	@Test
	public void ordenarResultados() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
		Root<Cliente> root = criteriaQuery.from(Cliente.class);

		criteriaQuery.select(root);
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get(Cliente_.nome))); //desc

		TypedQuery<Cliente> typedQuery = em.createQuery(criteriaQuery);
		List<Cliente> lista = typedQuery.getResultList();
		Assert.assertNotNull(lista);
		lista.forEach(c -> System.out.println(c.getId() + " " + c.getNome()));
	}
	
	
	
	//@Test
	public void projetarOResultadoDTO() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<ProdutoDTO> criteriaQuery = criteriaBuilder.createQuery(ProdutoDTO.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		//usa o construtor do ProdutoDTO
		criteriaQuery.select(criteriaBuilder.construct(ProdutoDTO.class, root.get("id"), root.get("nome")));

		TypedQuery<ProdutoDTO> typedQuery = em.createQuery(criteriaQuery);
		List<ProdutoDTO> produtos = typedQuery.getResultList();

		Assert.assertNotNull(produtos);
		Assert.assertFalse(produtos.isEmpty());
		produtos.forEach(p -> System.out.println(p.getId() + " " + p.getNome()));
	}

	// @Test
	public void projetarOResultadoTuple() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createQuery(Tuple.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		// criteriaQuery.multiselect(root.get("id"), root.get("nome")); TANTO FAZ!
		criteriaQuery.select(criteriaBuilder.tuple(root.get("id").alias("id"), root.get("nome").alias("nome")));

		TypedQuery<Tuple> typedQuery = em.createQuery(criteriaQuery);
		List<Tuple> produtos = typedQuery.getResultList();

		Assert.assertNotNull(produtos);
		Assert.assertFalse(produtos.isEmpty());
		produtos.forEach(p -> System.out.println(p.get("id") + " " + p.get("nome")));
	}

	// @Test
	public void projetarOResultado() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.multiselect(root.get("id"), root.get("nome"));

		TypedQuery<Object[]> typedQuery = em.createQuery(criteriaQuery);
		List<Object[]> produtos = typedQuery.getResultList();

		Assert.assertNotNull(produtos);
		Assert.assertFalse(produtos.isEmpty());
		produtos.forEach(p -> System.out.println(p[0] + " " + p[1]));
	}

	// @Test
	public void retornaTodosOsProdutos() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.select(root);

		TypedQuery<Produto> typedQuery = em.createQuery(criteriaQuery);
		List<Produto> produtos = typedQuery.getResultList();
		Assert.assertNotNull(produtos);
		Assert.assertFalse(produtos.isEmpty());
		produtos.forEach(p -> System.out.println(p.getNome()));
	}

	// @Test
	public void selectionaUmAtributoParaORetorno() {

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);

		criteriaQuery.select(root.get("cliente"));

		criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));

		TypedQuery<Cliente> typedQuery = em.createQuery(criteriaQuery);
		Cliente cliente = typedQuery.getSingleResult();
		Assert.assertNotNull(cliente);
		Assert.assertEquals("Fernando Medeiros", cliente.getNome());
	}

	// @Test
	public void buscarPorIdentificador() {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = cb.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);

		criteriaQuery.select(root);

		criteriaQuery.where(cb.equal(root.get("id"), 1));

		TypedQuery<Pedido> typedQuery = em.createQuery(criteriaQuery);
		Pedido pedido = typedQuery.getSingleResult();
		Assert.assertNotNull(pedido);
	}

}

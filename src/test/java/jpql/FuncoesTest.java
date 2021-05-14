package jpql;

import java.util.List;
import java.util.TimeZone;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Pedido;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class FuncoesTest extends EntityManagerTest {

	// @Test
	public void aplicarFuncaoString() {

		String jpql = "select c.nome, concat('Categoria: ', c.nome) from Categoria c";

		jpql = "select c.nome, length(c.nome) from Categoria c";

		jpql = "select c.nome, locate('a', c.nome) from Categoria c";

		jpql = "select c.nome, substring(c.nome, 1, 2) from Categoria c";

		jpql = "select c.nome, lower(c.nome) from Categoria c";

		jpql = "select c.nome, upper(c.nome) from Categoria c";

		jpql = "select c.nome, trim(c.nome) from Categoria c";

		TypedQuery<Object[]> typedQuery = em.createQuery(jpql, Object[].class);

		List<Object[]> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(arr -> System.out.println(arr[0] + " - " + arr[1]));

	}

	// @Test
	public void aplicarFuncaoData() {

		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

		String jpql = "select current_date, current_time, current_timestamp from Pedido p";

		jpql = "select year(p.dataCriacao), month(p.dataCriacao), day(p.dataCriacao) from Pedido p";

		TypedQuery<Object[]> typedQuery = em.createQuery(jpql, Object[].class);

		List<Object[]> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(arr -> System.out.println(arr[0] + " - " + arr[1] + " - " + arr[2]));

	}

	// @Test
	public void aplicarFuncaoNumero() {

		String jpql = "select abs(-10), mod(3,2), sqrt(9) from Pedido p";

		TypedQuery<Object[]> typedQuery = em.createQuery(jpql, Object[].class);

		List<Object[]> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(arr -> System.out.println(arr[0] + " | " + arr[1] + " | " + arr[2]));

	}

	// @Test
	public void aplicarFuncaoParaColecao() {

		String jpql = "select size(p.itens) from Pedido p where size(p.itens) > 1";

		TypedQuery<Integer> typedQuery = em.createQuery(jpql, Integer.class);

		List<Integer> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(size -> System.out.println(size));

	}

	//@Test
	public void aplicarFuncaoNativas() {

		String jpql = "select p from Pedido p where function('acima_media_faturamento', p.total) = 1";

		TypedQuery<Pedido> typedQuery = em.createQuery(jpql, Pedido.class);

		List<Pedido> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println(obj));

	}
	
	@Test
	public void aplicarFuncaoAgregacao() {

		//avg, count, min, max, sum
		String jpql = "select avg(p.total) from Pedido p";
		
		jpql = "select count(p.total) from Pedido p";

		TypedQuery<Number> typedQuery = em.createQuery(jpql, Number.class);

		List<Number> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println(obj));

	}
}

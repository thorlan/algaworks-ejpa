package criteria;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Produto;
import com.algaworks.ecommerce.model.Produto_;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class MetaModelTest extends EntityManagerTest {
	
	@Test
	public void retornaTodosOsProdutos() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.select(root);
		
		criteriaQuery.where(criteriaBuilder.or(
				criteriaBuilder.like(root.get(Produto_.nome),"%C%"),
				criteriaBuilder.like(root.get(Produto_.descricao), "%C%")
		));

		TypedQuery<Produto> typedQuery = em.createQuery(criteriaQuery);
		List<Produto> produtos = typedQuery.getResultList();
		Assert.assertNotNull(produtos);
		Assert.assertFalse(produtos.isEmpty());
		produtos.forEach(p -> System.out.println(p.getNome()));
	}

	
}

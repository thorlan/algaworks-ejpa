package criteria;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Pedido;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class BasicoCriteriaTest extends EntityManagerTest {

	
	@Test
	public void buscarPorIdentificador() {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = cb.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		
		criteriaQuery.select(root);
		
		criteriaQuery.where(cb.equal(root.get("id"),1 ));
		
		
		TypedQuery<Pedido> typedQuery = em.createQuery(criteriaQuery);
		Pedido pedido = typedQuery.getSingleResult();
		Assert.assertNotNull(pedido);
	}
}

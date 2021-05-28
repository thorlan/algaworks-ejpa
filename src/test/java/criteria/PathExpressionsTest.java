package criteria;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Cliente_;
import com.algaworks.ecommerce.model.ItemPedido;
import com.algaworks.ecommerce.model.ItemPedido_;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Pedido_;
import com.algaworks.ecommerce.model.Produto_;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class PathExpressionsTest extends EntityManagerTest {

	
	@Test
	public void buscarProdutosDeIDIgual1Exercicio() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		Join<Pedido, ItemPedido> joinItemPedido = root.join(Pedido_.ITENS);
		
		criteriaQuery.select(root);

		criteriaQuery.where(criteriaBuilder.equal(joinItemPedido.get(ItemPedido_.produto).get(Produto_.id), 1));

		TypedQuery<Pedido> typedQuery = em.createQuery(criteriaQuery);
		List<Pedido> lista = typedQuery.getResultList();

		Assert.assertFalse(lista.isEmpty());
	}
	
	
	//@Test
	public void usarPathExpression() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);

		criteriaQuery.select(root);

		criteriaQuery.where(criteriaBuilder.like(root.get(Pedido_.cliente).get(Cliente_.nome), "M%"));

		TypedQuery<Pedido> typedQuery = em.createQuery(criteriaQuery);
		List<Pedido> lista = typedQuery.getResultList();

		Assert.assertFalse(lista.isEmpty());
	}
}
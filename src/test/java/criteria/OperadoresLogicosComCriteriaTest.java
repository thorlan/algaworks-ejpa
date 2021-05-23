package criteria;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.NotaFiscal;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Pedido_;
import com.algaworks.ecommerce.model.StatusPedido;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class OperadoresLogicosComCriteriaTest extends EntityManagerTest {

	
	@Test
	public void usandoOr() {
		//todos os pedidos com status aguardando ou pago e com o preco maior ou igual a 499
		
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        criteriaQuery.where(
        		criteriaBuilder.or(
        				criteriaBuilder.equal(
    	        				root.get(Pedido_.status), StatusPedido.AGUARDANDO),
	        		criteriaBuilder.equal(
	        				root.get(Pedido_.status), StatusPedido.PAGO)
        		),
        		criteriaBuilder.greaterThan(root.get(Pedido_.total), new BigDecimal(499)));

		TypedQuery<Pedido> typedQuery = em.createQuery(criteriaQuery);
		
		List<Pedido> pedidos = typedQuery.getResultList();

		Assert.assertNotNull(pedidos);
		Assert.assertFalse(pedidos.isEmpty());

		pedidos.forEach(p -> System.out.println(p.getId()));
	}
	
	
	@Test
	public void uarOperadoresAndComVirgulaFormaExplicita() {
		
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        criteriaQuery.where(
        		criteriaBuilder.and(
	        		criteriaBuilder.greaterThan(
	        				root.get(Pedido_.total),new BigDecimal(499)),
	        		criteriaBuilder.equal(
	        				root.get(Pedido_.status), StatusPedido.PAGO)
        		));

		TypedQuery<Pedido> typedQuery = em.createQuery(criteriaQuery);
		
		List<Pedido> pedidos = typedQuery.getResultList();

		Assert.assertNotNull(pedidos);
		Assert.assertFalse(pedidos.isEmpty());

		pedidos.forEach(p -> System.out.println(p.getId()));
	}
	
	@Test
	public void uarOperadoresAndComVirgulaFormaImplicita() {
		
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        criteriaQuery.where(
        		criteriaBuilder.greaterThan(
        				root.get(Pedido_.total),new BigDecimal(499)),
        		criteriaBuilder.equal(
        				root.get(Pedido_.status), StatusPedido.PAGO)
        		);

		TypedQuery<Pedido> typedQuery = em.createQuery(criteriaQuery);
		
		List<Pedido> pedidos = typedQuery.getResultList();

		Assert.assertNotNull(pedidos);
		Assert.assertFalse(pedidos.isEmpty());

		pedidos.forEach(p -> System.out.println(p.getId()));
	}
	

}

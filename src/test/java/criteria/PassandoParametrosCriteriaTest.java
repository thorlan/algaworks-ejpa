package criteria;

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

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class PassandoParametrosCriteriaTest extends EntityManagerTest {

	@Test
	public void passarParametroDate() {
		
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<NotaFiscal> criteriaQuery = criteriaBuilder.createQuery(NotaFiscal.class);
		Root<NotaFiscal> root = criteriaQuery.from(NotaFiscal.class);

        criteriaQuery.select(root);
        
        ParameterExpression<Date> dataInicialParameter = criteriaBuilder.parameter(Date.class, "dataInicial");

        criteriaQuery.where(criteriaBuilder.greaterThan(root.get("dataEmissao"), dataInicialParameter));

		TypedQuery<NotaFiscal> typedQuery = em.createQuery(criteriaQuery);
		
		Calendar dataInicial = Calendar.getInstance();
		dataInicial.add(Calendar.DATE, -30);
		
		//para comparar TemporalType.DATE só data! tmeStamp etc etc!
		typedQuery.setParameter("dataInicial", dataInicial.getTime(), TemporalType.TIMESTAMP);
		
		List<NotaFiscal> notas = typedQuery.getResultList();

		Assert.assertNotNull(notas);
		Assert.assertFalse(notas.isEmpty());

		notas.forEach(p -> System.out.println(p.getId()));
	}
	
	
	//@Test
	public void passarParametro() {
		
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);
        
        ParameterExpression<Integer> parameterExpressionId = criteriaBuilder.parameter(Integer.class);
       // ParameterExpression<Integer> parameterExpressionIdComNome = criteriaBuilder.parameter(Integer.class, "id");

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"),parameterExpressionId));

		TypedQuery<Pedido> typedQuery = em.createQuery(criteriaQuery);
		typedQuery.setParameter(parameterExpressionId, 1);
		//typedQuery.setParameter("id", 1); com nome
		
		List<Pedido> pedidos = typedQuery.getResultList();

		Assert.assertNotNull(pedidos);
		Assert.assertFalse(pedidos.isEmpty());

		pedidos.forEach(p -> System.out.println(p.getId()));
	}

}

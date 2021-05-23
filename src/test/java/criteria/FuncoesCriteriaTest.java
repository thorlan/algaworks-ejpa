package criteria;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Cliente_;
import com.algaworks.ecommerce.model.Pagamento;
import com.algaworks.ecommerce.model.PagamentoBoleto;
import com.algaworks.ecommerce.model.PagamentoBoleto_;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Pedido_;
import com.algaworks.ecommerce.model.StatusPedido;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class FuncoesCriteriaTest extends EntityManagerTest {
	
	@Test
    public void aplicarFuncaoData() {
        // current_date, current_time, current_timestamp

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);
        Join<Pedido, Pagamento> joinPagamento = root.join(Pedido_.pagamento);
        Join<Pedido, PagamentoBoleto> joinPagamentoBoleto = criteriaBuilder
                .treat(joinPagamento, PagamentoBoleto.class);

        criteriaQuery.multiselect(
                root.get(Pedido_.id),
                criteriaBuilder.currentDate(),
                criteriaBuilder.currentTime(),
                criteriaBuilder.currentTimestamp()
        );

        criteriaQuery.where(
                criteriaBuilder.between(criteriaBuilder.currentDate(),
                        root.get(Pedido_.dataCriacao).as(java.sql.Date.class),
                        joinPagamentoBoleto.get(PagamentoBoleto_.dataVencimento).as(java.sql.Date.class)),
                criteriaBuilder.equal(root.get(Pedido_.status), StatusPedido.AGUARDANDO)
        );

        TypedQuery<Object[]> typedQuery = em.createQuery(criteriaQuery);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(
                arr[0]
                        + ", current_date: " + arr[1]
                        + ", current_time: " + arr[2]
                        + ", current_timestamp: " + arr[3]));
    }
	
	
	//@Test
	public void aplicarFuncaoString() {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = cb.createQuery(Object[].class);
		Root<Cliente> root = criteriaQuery.from(Cliente.class);
		
		criteriaQuery.multiselect(
				root.get(Cliente_.nome),
				cb.concat("Nome do Cliente: ", root.get(Cliente_.nome)),
				cb.length(root.get(Cliente_.nome)),
				cb.locate(root.get(Cliente_.nome), "a"),
				cb.substring(root.get(Cliente_.nome), 1,2),
				cb.lower(root.get(Cliente_.nome)),
				cb.upper(root.get(Cliente_.nome)),
				cb.trim(root.get(Cliente_.nome))
		);
		
		//posso usar no where tbm as funcoes!
		criteriaQuery.where(cb.equal(
                cb.substring(root.get(Cliente_.nome), 1, 1), "M"));
		
		TypedQuery<Object[]> typedQuery = em.createQuery(criteriaQuery);
		
		List<Object[]> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
		
		 lista.forEach(arr -> System.out.println(
	                arr[0]
	                        + ", concat: " + arr[1]
	                        + ", length: " + arr[2]
	                        + ", locate: " + arr[3]
	                        + ", substring: " + arr[4]
	                        + ", lower: " + arr[5]
	                        + ", upper: " + arr[6]
	                        + ", trim: |" + arr[7] + "|"));
	
	}
	

}

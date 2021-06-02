package hibernatevalidator;

import javax.validation.ConstraintViolationException;

import org.junit.Test;

import com.algaworks.ecommerce.model.Cliente;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class HibernateValidatorTest extends EntityManagerTest {

	@Test(expected = ConstraintViolationException.class)
	public void testaClienteValidator() {
		
		Cliente cliente = new Cliente();
		cliente.setCpf("1234567");
		
		em.getTransaction().begin();
		
		em.persist(cliente);
		
		em.getTransaction().commit();
		
		
	}
}

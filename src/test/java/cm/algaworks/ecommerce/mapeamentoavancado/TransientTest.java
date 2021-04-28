package cm.algaworks.ecommerce.mapeamentoavancado;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Cliente;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class TransientTest extends EntityManagerTest{

	@Test
	public void validarPrimeiroNome() {
		Cliente cliente = entityManager.find(Cliente.class, 1);
		Assert.assertEquals("Fernando" , cliente.getPrimeiroNome());
	}
	
}

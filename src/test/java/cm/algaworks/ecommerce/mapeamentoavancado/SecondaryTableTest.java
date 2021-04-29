package cm.algaworks.ecommerce.mapeamentoavancado;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.SexoCliente;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class SecondaryTableTest extends EntityManagerTest{

	@Test
	public void inserirPagamento() {
		
		Cliente cliente = new Cliente();
		cliente.setNome("Carlos Finotti");
		cliente.setSexo(SexoCliente.MASCULINO);
		cliente.setDataNascimento(LocalDate.of(1990, 1, 1));
		
		entityManager.getTransaction().begin();
		entityManager.persist(cliente);
		entityManager.getTransaction().commit();
		entityManager.clear();
		
		Cliente clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
		Assert.assertNotNull(clienteVerificacao.getSexo());
		Assert.assertEquals(clienteVerificacao.getSexo(), SexoCliente.MASCULINO);
	}
	

}

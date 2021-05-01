package relacionamentos;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Pedido;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class RemovendoEntidadesReferenciadasTest extends EntityManagerTest{

	@Test
	public void removeEntidadeRelacionada() {
		
		Pedido pedido = em.find(Pedido.class, 1);
		
		Assert.assertFalse(pedido.getItens().isEmpty());
		
		em.getTransaction().begin();
		pedido.getItens().forEach(i -> em.remove(i));
		em.remove(pedido);
		em.getTransaction().commit();
		
		em.clear();
		
		Pedido pedidoVerificacao = em.find(Pedido.class, 1);
		Assert.assertNull(pedidoVerificacao);
		
	}
}

package com.algaworks.ecommece.conhecendoentitymanager;

import org.junit.Test;

import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.StatusPedido;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class FlushTest extends EntityManagerTest{
	
	@Test(expected = Exception.class)
	public void chamarFlush() {
		
		try {
			em.getTransaction().begin();
			
			Pedido pedido = em.find(Pedido.class, 1);
			pedido.setStatus(StatusPedido.PAGO);
			
			//com o flush o pedido vai para o banco mesmo que ocorra a exception! flush força ida ao banco!
			//forca o que ta na memoria ir ao banco. 
			//o rollback vai tirar o que foi inserido no banco.
			//flush dificilmente sera usado!
		//	entityManager.flush();
			
			if(pedido.getPagamento() == null) {
				throw new RuntimeException("Pedido ainda não foi pago.");
			}
			
			em.getTransaction().commit();
			
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
		
	}
}

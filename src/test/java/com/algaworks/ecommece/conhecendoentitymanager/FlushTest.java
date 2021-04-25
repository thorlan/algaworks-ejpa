package com.algaworks.ecommece.conhecendoentitymanager;

import javax.management.RuntimeErrorException;

import org.junit.Test;

import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;
import com.algaworks.ecommerce.model.StatusPedido;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class FlushTest extends EntityManagerTest{
	
	@Test(expected = Exception.class)
	public void chamarFlush() {
		
		try {
			entityManager.getTransaction().begin();
			
			Pedido pedido = entityManager.find(Pedido.class, 1);
			pedido.setStatus(StatusPedido.PAGO);
			
			//com o flush o pedido vai para o banco mesmo que ocorra a exception! flush força ida ao banco!
			//forca o que ta na memoria ir ao banco. 
			//o rollback vai tirar o que foi inserido no banco.
			//flush dificilmente sera usado!
		//	entityManager.flush();
			
			if(pedido.getPagamento() == null) {
				throw new RuntimeException("Pedido ainda não foi pago.");
			}
			
			entityManager.getTransaction().commit();
			
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw e;
		}
		
	}
}

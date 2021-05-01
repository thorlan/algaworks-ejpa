package com.algaworks.ecommece.conhecendoentitymanager;

import org.junit.Test;

import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.StatusPedido;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class GerenciamentoTransacoesTest extends EntityManagerTest {

	//@Test
	public void abrirFecharCancelarTransacao() {

		Pedido pedido = em.find(Pedido.class, 1);
		em.getTransaction().begin();

//		pedido.setStatus(StatusPedido.PAGO);
//		
//		if(pedido.getPagamentoCartao() != null) {
//			entityManager.getTransaction().commit();
//		} else {
//			entityManager.getTransaction().rollback();
//		}
//		

		if (pedido.getPagamento() != null) {
			pedido.setStatus(StatusPedido.PAGO);
		}

		em.getTransaction().rollback();

	}
	
	
	
	@Test(expected = Exception.class)
	public void abrirFecharCancelarTransacaoMetodoProfissional() {
		try {
			em.getTransaction().begin();
			regraDeNegocio();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}

	}
	
	private void regraDeNegocio() {
		Pedido pedido = em.find(Pedido.class, 1);
		pedido.setStatus(StatusPedido.PAGO);
		if (pedido.getPagamento() == null) {
			throw new RuntimeException("O pagamento não deve ser null");
		}

	}
}

package com.algaworks.ecommece.conhecendoentitymanager;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.StatusPedido;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class ListenerTest extends EntityManagerTest{
	
	@Test
	public void acionarCallBacks() {
		Cliente cliente = em.find(Cliente.class, 1);
		
		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);
		pedido.setStatus(StatusPedido.AGUARDANDO);
		pedido.setDataCriacao(LocalDateTime.now());
		pedido.setTotal(BigDecimal.ONE);
		
		em.getTransaction().begin();
		
		em.persist(pedido);
		
		em.flush();
		
		pedido.setStatus(StatusPedido.PAGO);
		
		em.getTransaction().commit();
		
		em.clear();
		
		Pedido pedidoVerificacao = em.find(Pedido.class, pedido.getId());
		
		Assert.assertNotNull(pedidoVerificacao.getDataCriacao());
		Assert.assertNotNull(pedidoVerificacao.getDataUltimaAtualizacao());
		
	}
}

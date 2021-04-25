package com.algaworks.ecommece.conhecendoentitymanager;

import org.junit.Test;

import com.algaworks.ecommerce.model.Produto;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class CachePrimeiroNivelTest extends EntityManagerTest{
	
	@Test
	public void verificaCache() {
		
		Produto produto = entityManager.find(Produto.class, 1);
		
		System.out.println(produto.getNome());
		
		System.out.println("--------------------------");
		//ve que o produto de id 1 ja esta na memoria e nao faz duas consultas
		
		
		Produto produtoResgatado = entityManager.find(Produto.class, produto.getId());
		System.out.println(produtoResgatado.getNome());
		
		System.out.println("--------------------------");
	}
}

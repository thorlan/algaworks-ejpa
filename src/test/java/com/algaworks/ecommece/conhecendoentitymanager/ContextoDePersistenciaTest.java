package com.algaworks.ecommece.conhecendoentitymanager;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Test;

import com.algaworks.ecommerce.model.Produto;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class ContextoDePersistenciaTest extends EntityManagerTest {

	@Test
	public void usarContextoDePersistencia() {
		em.getTransaction().begin();

		Produto produto = em.find(Produto.class, 1);
		produto.setPreco(new BigDecimal(100.0));
		// mesmo sem o save, ja vai alterar no banco por o produto estar em estado
		// gerenciado
		// nao preciso do persist/merge para salvar a alteração no banco

		Produto produto2 = new Produto();
		produto2.setNome("Caneca para café");
		produto2.setPreco(new BigDecimal(100.0));
		produto2.setDescricao("Boa caneca para café");
		produto2.setDataCriacao(LocalDateTime.now());
		em.persist(produto2);

		Produto produto3 = new Produto();
		produto3.setNome("Caneca para chá");
		produto3.setPreco(new BigDecimal(100.0));
		produto3.setDescricao("Boa caneca para chá");
		produto3.setDataCriacao(LocalDateTime.now());
		produto3 = em.merge(produto3);

		em.flush();

		produto3.setDescricao("Alterando descrição");

		em.getTransaction().commit();

	}
}

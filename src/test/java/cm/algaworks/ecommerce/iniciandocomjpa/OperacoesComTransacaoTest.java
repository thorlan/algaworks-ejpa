package cm.algaworks.ecommerce.iniciandocomjpa;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Produto;

public class OperacoesComTransacaoTest extends EntityManagerTest {

	@Test
	public void impedirOperacaoComBancoDeDados() { 
		
		Produto produto = em.find(Produto.class, 1);
		String nome = produto.getNome();
		em.detach(produto); //o em deixa de gerenciar o estado do objeto, desanexa
		
		em.getTransaction().begin();
		produto.setNome("Kindle Paperwhite 2 Geração");
		em.getTransaction().commit();
		
		em.clear();
		
		Produto produtoVerificacao = em.find(Produto.class, 1);
		
		Assert.assertEquals(nome, produtoVerificacao.getNome());
		Assert.assertNotNull(produtoVerificacao.getPreco());
	}
	
	@Test
	public void mostrarDiferencaPersistMerge() {
		//persist faz o objeto ser gerenciado, podemos fazer setNome e ele vai ser alterado na base de dados!
		Produto produtoPersist = new Produto();
		produtoPersist.setNome("Smartphone One Plus");
		produtoPersist.setDescricao("O processador mais rápido.");
		produtoPersist.setPreco(new BigDecimal(2000));
		produtoPersist.setDataCriacao(LocalDateTime.now());
		
		em.getTransaction().begin();
		
		em.persist(produtoPersist);
		produtoPersist.setNome("Smartphone Two Plus");
		em.getTransaction().commit();
		
		em.clear();
		
		Produto produtoVerificacao = em.find(Produto.class, produtoPersist.getId());
		
		Assert.assertEquals(produtoPersist.getNome(), produtoVerificacao.getNome());
		
		
		
		Produto produtoMerge = new Produto();
		produtoMerge.setId(6);
		produtoMerge.setNome("Smartphone 3 Plus");
		produtoMerge.setDescricao("O processador mais rápido3x.");
		produtoMerge.setPreco(new BigDecimal(2000));
		produtoMerge.setDataCriacao(LocalDateTime.now());
		
		em.getTransaction().begin();
		
		em.merge(produtoMerge);
		produtoMerge.setNome("Smartphone 4 Plus");  //NOME NAO É ATUALIZADO NO BANCO DE DADOS!!!! O MERGE NAO USA UMA INSTANCIA GERENCIADA!
		em.getTransaction().commit();
		
		em.clear();
		
		//Produto produtoVerificacaMerge = entityManager.find(Produto.class, produtoMerge.getId());
		
		//Assert.assertEquals(produtoMerge.getNome(), produtoVerificacaMerge.getNome());
	}
	
	
	@Test
	public void inserirObjetoComMerge() {
		
		Produto produto = new Produto();
		produto.setNome("Microfone Rode Videmic");
		produto.setId(4);
		produto.setDescricao("A melhor qualidade de som");
		produto.setPreco(new BigDecimal(1000));
		
		em.getTransaction().begin();
		
		em.merge(produto);
		
		em.getTransaction().commit();
		
		em.clear();
		
		Produto produtoVerificaca = em.find(Produto.class, produto.getId());
		
		Assert.assertEquals(produto.getNome(), produtoVerificaca.getNome());
	}
	
	@Test
	public void atualizarObjetoGerenciado() {
		Produto produto = em.find(Produto.class, 1);
		produto.setNome("Kindle Paperwhite 2 Geração");
		
		em.getTransaction().begin();
		//nao preciso do merge, ja esta sendo gerenciado pelo EM com o find, ao fechar a transacao ele ja salva o cara q esta em memoria no banco!
		em.getTransaction().commit();
		
		em.clear();
		
		Produto produtoVerificacao = em.find(Produto.class, 1);
		
		Assert.assertEquals(produto.getNome(), produtoVerificacao.getNome());
		Assert.assertNotNull(produtoVerificacao.getPreco());
	}
	
	@Test
	public void atualizarObjeto() {
		Produto produto = new Produto();
		produto.setId(1);
		produto.setNome("Kindle Paperwhite");
		produto.setDescricao("Conheça o novo kindle.");
		produto.setPreco(new BigDecimal(599));
		
		em.getTransaction().begin();
		em.merge(produto);
		em.getTransaction().commit();
		
		em.clear();
		
		Produto produtoVerificacao = em.find(Produto.class, 1);
		
		Assert.assertEquals("Kindle Paperwhite", produtoVerificacao.getNome());
		
	}
	
	@Test
	public void removerObjeto() {
		Produto produto = em.find(Produto.class, 3);
		
		em.getTransaction().begin();
		em.remove(produto);
		em.getTransaction().commit();
		
		Produto produtoVerificacao = em.find(Produto.class, 3);
		
		Assert.assertNull(produtoVerificacao);
	}
	
	
	@Test
	public void inserirOPrimeiroObjeto() {
		Produto produto = new Produto();
		produto.setNome("Câmera Canon");
		produto.setDescricao("A melhor definição para as suas fotos");
		produto.setPreco(new BigDecimal(5000));
		produto.setDataCriacao(LocalDateTime.now());
		
		em.getTransaction().begin();
		
		em.persist(produto);
		
		em.getTransaction().commit();
		
		em.clear();
		
		Produto produtoVerificaca = em.find(Produto.class, produto.getId());
		
		Assert.assertEquals(produto.getNome(), produtoVerificaca.getNome());
	}
	
	
	public void abrirEFecharATransacao() {
		
		em.getTransaction().begin();
		
		
//		entityManager.persist(produto);
//		entityManager.merge(produto);
//		entityManager.remove(produto);
		
		em.getTransaction().commit();
	}
}

package cm.algaworks.ecommerce.iniciandocomjpa;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Produto;

public class OperacoesComTransacaoTest extends EntityManagerTest {

	@Test
	public void impedirOperacaoComBancoDeDados() { 
		
		Produto produto = entityManager.find(Produto.class, 1);
		String nome = produto.getNome();
		entityManager.detach(produto); //o em deixa de gerenciar o estado do objeto, desanexa
		
		entityManager.getTransaction().begin();
		produto.setNome("Kindle Paperwhite 2 Geração");
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Produto produtoVerificacao = entityManager.find(Produto.class, 1);
		
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
		
		entityManager.getTransaction().begin();
		
		entityManager.persist(produtoPersist);
		produtoPersist.setNome("Smartphone Two Plus");
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Produto produtoVerificacao = entityManager.find(Produto.class, produtoPersist.getId());
		
		Assert.assertEquals(produtoPersist.getNome(), produtoVerificacao.getNome());
		
		
		
		Produto produtoMerge = new Produto();
		produtoMerge.setId(6);
		produtoMerge.setNome("Smartphone 3 Plus");
		produtoMerge.setDescricao("O processador mais rápido3x.");
		produtoMerge.setPreco(new BigDecimal(2000));
		
		entityManager.getTransaction().begin();
		
		entityManager.merge(produtoMerge);
		produtoMerge.setNome("Smartphone 4 Plus");  //NOME NAO É ATUALIZADO NO BANCO DE DADOS!!!! O MERGE NAO USA UMA INSTANCIA GERENCIADA!
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
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
		
		entityManager.getTransaction().begin();
		
		entityManager.merge(produto);
		
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Produto produtoVerificaca = entityManager.find(Produto.class, produto.getId());
		
		Assert.assertEquals(produto.getNome(), produtoVerificaca.getNome());
	}
	
	@Test
	public void atualizarObjetoGerenciado() {
		Produto produto = entityManager.find(Produto.class, 1);
		produto.setNome("Kindle Paperwhite 2 Geração");
		
		entityManager.getTransaction().begin();
		//nao preciso do merge, ja esta sendo gerenciado pelo EM com o find, ao fechar a transacao ele ja salva o cara q esta em memoria no banco!
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Produto produtoVerificacao = entityManager.find(Produto.class, 1);
		
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
		
		entityManager.getTransaction().begin();
		entityManager.merge(produto);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Produto produtoVerificacao = entityManager.find(Produto.class, 1);
		
		Assert.assertEquals("Kindle Paperwhite", produtoVerificacao.getNome());
		
	}
	
	@Test
	public void removerObjeto() {
		Produto produto = entityManager.find(Produto.class, 3);
		
		entityManager.getTransaction().begin();
		entityManager.remove(produto);
		entityManager.getTransaction().commit();
		
		Produto produtoVerificacao = entityManager.find(Produto.class, 3);
		
		Assert.assertNull(produtoVerificacao);
	}
	
	
	@Test
	public void inserirOPrimeiroObjeto() {
		Produto produto = new Produto();
		produto.setNome("Câmera Canon");
		produto.setDescricao("A melhor definição para as suas fotos");
		produto.setPreco(new BigDecimal(5000));
		
		entityManager.getTransaction().begin();
		
		entityManager.persist(produto);
		
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Produto produtoVerificaca = entityManager.find(Produto.class, produto.getId());
		
		Assert.assertEquals(produto.getNome(), produtoVerificaca.getNome());
	}
	
	
	public void abrirEFecharATransacao() {
		
		entityManager.getTransaction().begin();
		
		
//		entityManager.persist(produto);
//		entityManager.merge(produto);
//		entityManager.remove(produto);
		
		entityManager.getTransaction().commit();
	}
}

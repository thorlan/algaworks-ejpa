package cm.algaworks.ecommerce.iniciandocomjpa;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Produto;

public class ConsultandoRegistrosTest extends EntityManagerTest {
	
	@Test
	public void buscarPrIdentificador() {
		Produto produto = entityManager.find(Produto.class, 1);
		
		Assert.assertNotNull(produto);
		Assert.assertEquals("Kindle", produto.getNome());
	}
	
	@Test
	public void atualizarAReferencia() {
		Produto produto = entityManager.find(Produto.class, 1);
		produto.setNome("Microfone Samson");
		
		//atualiza a referencia, busca do banco novamente!
		entityManager.refresh(produto);
		
		Assert.assertEquals("Kindle", produto.getNome());
	}
	
	
}

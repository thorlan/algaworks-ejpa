package cm.algaworks.ecommerce.mapeamentoavancado;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Atributo;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Produto;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class EllementCollectionTest extends EntityManagerTest{

	@Test
	public void aplicarTags() {
		em.getTransaction().begin();
		Produto produto = em.find(Produto.class, 1);
		
		produto.setTags(Arrays.asList("ebook","livro-digital"));
		
		
		em.getTransaction().commit();
		
		em.clear();

		Produto produtoVerificacao = em.find(Produto.class, produto.getId());
		
		Assert.assertEquals(2, produtoVerificacao.getTags().size());
		
	}
	
	@Test
	public void aplicarAtributos() {
		em.getTransaction().begin();
		Produto produto = em.find(Produto.class, 1);
		
		produto.setAtributos((Arrays.asList(new Atributo("tela", "320x600"))));
		
		
		em.getTransaction().commit();
		
		em.clear();

		Produto produtoVerificacao = em.find(Produto.class, produto.getId());
		
		Assert.assertEquals(1, produtoVerificacao.getAtributos().size());
		
	}
	
	@Test
	public void aplicarContato() {
		em.getTransaction().begin();
		Cliente cliente = em.find(Cliente.class, 1);
		
		cliente.setContatos(Collections.singletonMap("email", "fernando@email.com"));
		
		
		em.getTransaction().commit();
		
		
		em.clear();

		Cliente clienteVerificacao = em.find(Cliente.class, cliente.getId());
		
		Assert.assertEquals("fernando@email.com", clienteVerificacao.getContatos().get("email"));
		
	}
	
}

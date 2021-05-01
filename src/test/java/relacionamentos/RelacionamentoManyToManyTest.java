package relacionamentos;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Categoria;
import com.algaworks.ecommerce.model.Produto;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class RelacionamentoManyToManyTest extends EntityManagerTest{

	@Test
	public void verificaRelacionamento() {
		
		Produto produto = em.find(Produto.class, 1);
		Categoria categoria = em.find(Categoria.class, 1);
		
		em.getTransaction().begin();
		
		//categoria.setProdutos(Arrays.asList(produto));
		produto.setCategorias(Arrays.asList(categoria));
		
		em.getTransaction().commit();
		em.clear();
		
		Categoria categoriaVerificacao = em.find(Categoria.class, categoria.getId());
		
		Assert.assertFalse(categoriaVerificacao.getProdutos().isEmpty());
	}
	

}

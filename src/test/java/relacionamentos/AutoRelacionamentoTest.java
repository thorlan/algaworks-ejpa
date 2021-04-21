package relacionamentos;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Categoria;
import com.algaworks.ecommerce.model.Pedido;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class AutoRelacionamentoTest extends EntityManagerTest{

	@Test
	public void verificaRelacionamento() {
		
		Categoria categoriaPai = new Categoria();
		categoriaPai.setNome("Eletrônicos");
		
		Categoria categoriaFilho = new Categoria();
		categoriaFilho.setNome("Celulares");
		categoriaFilho.setCategoriaPai(categoriaPai);
		
		
		
		entityManager.getTransaction().begin();
		entityManager.persist(categoriaPai);
		entityManager.persist(categoriaFilho);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Categoria categoriaVerificacao = entityManager.find(Categoria.class, categoriaFilho.getId());
		Categoria categoriaPaiVerificacao = entityManager.find(Categoria.class, categoriaPai.getId());
		
		Assert.assertNotNull(categoriaVerificacao);
		Assert.assertNotNull(categoriaVerificacao.getCategoriaPai());
		Assert.assertFalse(categoriaPaiVerificacao.getCategoriasFilha().isEmpty());
	}
	
}

package relacionamentos;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Categoria;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class AutoRelacionamentoTest extends EntityManagerTest{

	@Test
	public void verificaRelacionamento() {
		
		Categoria categoriaPai = new Categoria();
		categoriaPai.setNome("Futebol");
		
		Categoria categoriaFilho = new Categoria();
		categoriaFilho.setNome("Uniformes");
		categoriaFilho.setCategoriaPai(categoriaPai);
		
		
		
		em.getTransaction().begin();
		em.persist(categoriaPai);
		em.persist(categoriaFilho);
		em.getTransaction().commit();
		
		em.clear();
		
		Categoria categoriaVerificacao = em.find(Categoria.class, categoriaFilho.getId());
		Categoria categoriaPaiVerificacao = em.find(Categoria.class, categoriaPai.getId());
		
		Assert.assertNotNull(categoriaVerificacao);
		Assert.assertNotNull(categoriaVerificacao.getCategoriaPai());
		Assert.assertFalse(categoriaPaiVerificacao.getCategoriasFilha().isEmpty());
	}
	
}

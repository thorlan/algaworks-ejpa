package jpql;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Categoria;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class PaginacaoJPQLTest extends EntityManagerTest {
	
	@Test
	public void paginarResultados() {
		
		String jpql = "select c from Categoria c order by c.nome";
		
		TypedQuery<Categoria> typedQuery = em.createQuery(jpql, Categoria.class);
		
		//firstResult = maxResult * (pagina - 1) para paginar 
		typedQuery.setFirstResult(0);
		typedQuery.setMaxResults(2);
		
		List<Categoria> categorias = typedQuery.getResultList();
		
		Assert.assertFalse(categorias.isEmpty());
		
		categorias.forEach(c -> System.out.println(c.getId() + " , " + c.getNome()));
		
	}
}

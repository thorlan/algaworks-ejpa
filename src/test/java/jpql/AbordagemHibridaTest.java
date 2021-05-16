package jpql;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.algaworks.ecommerce.model.Categoria;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class AbordagemHibridaTest extends EntityManagerTest {
	
	@BeforeClass
	public static void setUpBeforeClass() {
		entityManagerFactory = Persistence.createEntityManagerFactory("Ecommerce-PU");
		EntityManager em2 = entityManagerFactory.createEntityManager();
		
		String jpql = "Select c from Categoria c";
		TypedQuery<Categoria> typedQuery = em2.createQuery(jpql, Categoria.class);
		
		entityManagerFactory.addNamedQuery("Categoria.listar", typedQuery);
	}
	
	@Test
    public void usarAbordagemHibrida() {
		
		TypedQuery<Categoria> typedQuery = em.createNamedQuery("Categoria.listar", Categoria.class);
		List<Categoria> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
    }

   

}

package jpql;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class NamedQueryTest extends EntityManagerTest {
	
	
	//@Test
    public void executarConsulta() {
		
		TypedQuery<Produto> typedQuery = em.createNamedQuery("Produto.listar", Produto.class);
		List<Produto> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void executarConsultaArquivoXml() {
    	
    	TypedQuery<Pedido> typedQuery = em.createNamedQuery("Pedido.listar", Pedido.class);
		List<Pedido> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
    }
    
    @Test
    public void executarConsultaArquivoXml2() {
    	
    	TypedQuery<Pedido> typedQuery = em.createNamedQuery("Pedido.todos", Pedido.class);
		List<Pedido> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
    }

}

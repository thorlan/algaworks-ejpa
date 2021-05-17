package jpql;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.dto.ProdutoDTO;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class BasicoJPQLTest extends EntityManagerTest {
	
	
	@Test
	public void usarDistinct() {
		//evita duplicacao nas consultas!
	
		String jpql = "Select distinct p from Pedido p "
				+ "join p.itens i join i.produto pro "
				+ "where pro.id in (1,2,3,4) ";
	
		TypedQuery<Pedido> typedQuery = em.createQuery(jpql, Pedido.class);
		List<Pedido> pedidos = typedQuery.getResultList();
		
		Assert.assertFalse(pedidos.isEmpty());
		
		System.out.println(pedidos.size());
	}
	
	
	//@Test
	public void ordenarResultados() {
		//asc desc
		String jpql = "select c from Cliente c order by c.nome asc";
		
		TypedQuery<Object[]> typedQuery = em.createQuery(jpql, Object[].class);
		List<Object[]> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
		
	}
	
	@Test
	public void projetarOResultadoDTO() {
		String jpql = "select new com.algaworks.ecommerce.dto.ProdutoDTO(id, nome) from Produto";
		
		TypedQuery<ProdutoDTO> typedQuery = em.createQuery(jpql, ProdutoDTO.class);
		List<ProdutoDTO> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
		
		System.out.println("printando");
		lista.forEach(l -> System.out.println(l));
	}
	
	//@Test
	public void projetarOResultado() {
		String jpql = "select id, nome from Produto";
		
		TypedQuery<Object[]> typedQuery = em.createQuery(jpql, Object[].class);
		List<Object[]> lista = typedQuery.getResultList();
		
		Assert.assertTrue(lista.get(0).length == 2);
		
		lista.forEach(array-> System.out.println(array[0] + " , " + array[1]));
	}
	
	//@Test
	public void selecionarUmAtributoParaRetorno() {
		String jpql = "select p.nome from Produto p";
		
		TypedQuery<String> typedQuery = em.createQuery(jpql, String.class);
		List<String> lista = typedQuery.getResultList();
		Assert.assertTrue(String.class.equals(lista.get(0).getClass()));
		
		String jpqlCliente = "select p.cliente from Pedido p";
		
		TypedQuery<Cliente> typedQueryCliente = em.createQuery(jpqlCliente, Cliente.class);
		List<Cliente> listaCliente = typedQueryCliente.getResultList();
		Assert.assertTrue(Cliente.class.equals(listaCliente.get(0).getClass()));
		
	}
	
	
	//@Test
	public void buscarPorIdentificador() {
		
		TypedQuery<Pedido> typedQuery = em.createQuery("select p from Pedido p where p.id = 1", Pedido.class);
		Pedido pedido = typedQuery.getSingleResult();
		
		Assert.assertNotNull(pedido);
	}
	
	//@Test
	public void mostrarDiferencaQueries() {
		String jpql = "select p from Pedido p where p.id = 1";
		
		TypedQuery<Pedido> typedQuery = em.createQuery(jpql, Pedido.class);
		Pedido pedido1 = typedQuery.getSingleResult();
		Assert.assertNotNull(pedido1);
		
		Query query = em.createQuery(jpql);
		Pedido pedido2 = (Pedido) query.getSingleResult();
		Assert.assertNotNull(pedido2);
		
	}
}

package jpql;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class PathExpressionTest extends EntityManagerTest {
	
//	@Test
	public void usarPatchExpressions() {
		String jpql = "Select p.cliente.nome from Pedido p";
		
		TypedQuery<Object[]> typedQuery = em.createQuery(jpql, Object[].class);
		
		List<Object[]> nomes = typedQuery.getResultList();
		//no [0] vai ser o nome!
		Assert.assertFalse(nomes.isEmpty());
	}
	
	@Test
	public void buscarPedidoComProdutoEspecifico() {
	    //   String jpql = "select p from Pedido p join p.itens i where i.id.produtoId = 1";
		//	 String jpql = "select p from Pedido p join p.itens i where i.produto.id = 1";
		//	 String jpql = "select p from Pedido p join p.itens i join i.produto pro where pro.id = 1";
		
		String jpql = "Select p from Pedido p join p.itens i where i.produto.id = 1";
		
		TypedQuery<Pedido> typedQuery = em.createQuery(jpql, Pedido.class);
		
		List<Pedido> pedidos = typedQuery.getResultList();
		
		Assert.assertFalse(pedidos.isEmpty());
		pedidos.forEach(p -> {
			//Assert.assertTrue(p.getId() == 1);
			System.out.println("printando id");
			System.out.println(p.getId());
		});
	}
	
	
}

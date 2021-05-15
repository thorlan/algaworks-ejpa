package jpql;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class SubqueriesTest extends EntityManagerTest {

	// @Test
	public void produtosComMaiorPreco() {

		// quando precisar a partir dos dados que eu tenho a partir gerar alguma
		// informação!
		// na subquery so pode voltar um item!

		// produto mais caros da base
		String jpql = "Select p from Produto p where " + "p.preco = (select max(preco) from Produto)";

		// pedidos com

		TypedQuery<Produto> typedQuery = em.createQuery(jpql, Produto.class);

		List<Produto> produtos = typedQuery.getResultList();
		Assert.assertFalse(produtos.isEmpty());

		produtos.forEach(p -> System.out.println(p.getId() + " " + p.getPreco()));
	}

	// @Test
	public void pedidosAcimaDaMediaDeVendas() {

		String jpql = "Select p from Pedido p where " + "p.total > (select avg(total) from Pedido)";

		TypedQuery<Pedido> typedQuery = em.createQuery(jpql, Pedido.class);

		List<Pedido> pedidos = typedQuery.getResultList();
		Assert.assertFalse(pedidos.isEmpty());

		pedidos.forEach(p -> System.out.println(p.getId() + " " + p.getTotal()));
	}

	// @Test
	public void clientesQueGastamMaisQue100Reais() {

		String jpql = "Select c from Cliente c " + "where 100 < (select sum(p.total) from c.pedidos p)";

		TypedQuery<Cliente> typedQuery = em.createQuery(jpql, Cliente.class);

		List<Cliente> clientes = typedQuery.getResultList();
		Assert.assertFalse(clientes.isEmpty());

		clientes.forEach(c -> System.out.println(c.getId()));
	}

	 //@Test
	public void clientesQueGastamMaisQue100Reais2ponto0() {

		String jpql = "Select c from Cliente c " 
				+ "where "
				+ "500 < (select sum(p.total) from Pedido p where "
				+ "p.cliente = c)";

		TypedQuery<Cliente> typedQuery = em.createQuery(jpql, Cliente.class);

		List<Cliente> clientes = typedQuery.getResultList();
		Assert.assertFalse(clientes.isEmpty());

		clientes.forEach(c -> System.out.println(c.getId()));
	}
	
	 //@Test
	public void subQuerieComIn() {

		String jpql = "Select p from Pedido p where p.id in "
				+ " (select p2.id from ItemPedido i2 join i2.pedido p2 join i2.produto pro2 where pro2.preco > 100)";

		TypedQuery<Pedido> typedQuery = em.createQuery(jpql, Pedido.class);

		List<Pedido> pedidos = typedQuery.getResultList();
		Assert.assertFalse(pedidos.isEmpty());

		pedidos.forEach(c -> System.out.println(c.getId()));
	}
	 
	 //@Test
	public void subQuerieComExists() {
		//selecionar todos os produtos que ja tenham sido vendidos uma vez!
		 //POSSO USAR EXISTS E NOT EXISTS PENSAR NISSO NO STATUS!!! QUERO TODOS AS SOLICITACOES
		 //QUE TENHAM STATUS <= X E NUNCA STATUS > X  
		 // EXISTS TEM QUE COMPARAR O SELECT 1 COM O 2, MELHOR Q SEJA ASSIM!
		String jpql = "Select p from Produto p where exists "
				+ "(select 1 from ItemPedido ip2 join ip2.produto p2 "
				+ "join ip2.pedido ped where ped.status = 'PAGO' and p2 = p )";

		TypedQuery<Produto> typedQuery = em.createQuery(jpql, Produto.class);

		List<Produto> produtos = typedQuery.getResultList();
		Assert.assertFalse(produtos.isEmpty());

		produtos.forEach(obj -> System.out.println(obj.getId()));
	}
	 
	
	 //trazer todos os pedidos que contenham um produto da categoria 2
	//@Test
	public void trazerPedidosQueContenhamProdutoDaCategoria2() {
		
		String jpql = "Select p from Pedido p where p.id in "
				+ "(select p2.id from ItemPedido i2 "
					+ "join i2.pedido p2 "
					+ "join i2.produto pro2 "
					+ "join pro2.categorias c2 "
				+ "where c2.id = 2)";
		
		//pedido p.itens i ItemPedido
		//i.produto pro Produto
		//pro.categoria c Categoria
		// c.id = 2
		
		TypedQuery<Pedido> typedQuery = em.createQuery(jpql, Pedido.class);

		List<Pedido> pedidos = typedQuery.getResultList();
		Assert.assertFalse(pedidos.isEmpty());

		pedidos.forEach(c -> System.out.println(c.getId()));
	}
	
	//trazer clientes q fizeram dois ou mais pedidos
	//@Test
	public void trazerTodosOsClientesQueFizeramDoisOuMaisPedidos() {
		
//		String jpql = "Select c from Cliente c " 
//				+ "where "
//				+ "2 <= (select count(id) from Pedido p where "
//				+ "p.cliente = c)";
		
		 String jpql = "select c from Cliente c where " +
	                " (select count(cliente) from Pedido where cliente = c) >= 2";

		TypedQuery<Cliente> typedQuery = em.createQuery(jpql, Cliente.class);

		List<Cliente> clientes = typedQuery.getResultList();
		Assert.assertFalse(clientes.isEmpty());

		clientes.forEach(c -> System.out.println(c.getId()));
	}
	
	//retornar produtos que nao foram vendidos com o preco atual!
	//preco em produto e nao em ItemPedido
	//@Test
	public void produtosQueNaoForamVendidosComOPrecoAtual() {
		
		 String jpql2 = "select p from Produto p " +
	                " where exists " +
	                " (select 1 from ItemPedido where produto = p and precoProduto <> p.preco)";

		
		String jpql = "Select p from Produto p where exists "
				+ "(select 1 from ItemPedido where produto = p and precoProduto <> p.preco)";
		
		String teste = "Select s from Solicitacao s where s.id in "
				+ "(select st2.id from Status st2 join s2.solicitacao s2 join st2.tipoStatus ts2 where ts2.id <= 2) "
				+ "and s.id not in "
				+ "(select st3.id from Status st3 join s3.solicitacao s3 join st3.tipoStatus ts3 where ts3.id > 2) ";
/*		
		+ "(select p2.id from ItemPedido i2 "
		+ "join i2.pedido p2 "
		+ "join i2.produto pro2 "
		+ "join pro2.categorias c2 "
	+ "where c2.id = 2)";
	*/

		TypedQuery<Produto> typedQuery = em.createQuery(jpql, Produto.class);

		List<Produto> produtos = typedQuery.getResultList();
		Assert.assertFalse(produtos.isEmpty());

		produtos.forEach(obj -> System.out.println(obj.getId()));
	}
	
	//@Test
	public void produtosQueForamVendidosPeloPrecoAtualComAll() {
		//todos os produtos que sempre foram vendidos pelo preco atual!
		
		String jpql = "select p from Produto p where "
				+ "p.preco = ALL (select precoProduto from ItemPedido where produto = p)";


		TypedQuery<Produto> typedQuery = em.createQuery(jpql, Produto.class);

		List<Produto> produtos = typedQuery.getResultList();
		Assert.assertFalse(produtos.isEmpty());

		produtos.forEach(obj -> System.out.println(obj.getId()));
	}
	
	@Test
	public void produtosQueFramVendidosEDepoisFicamCaros() {
		
		String jpql = "select p from Produto p where "
				+ "p.preco > ALL (select precoProduto from ItemPedido where produto = p)";


		TypedQuery<Produto> typedQuery = em.createQuery(jpql, Produto.class);

		List<Produto> produtos = typedQuery.getResultList();
		Assert.assertFalse(produtos.isEmpty());

		produtos.forEach(obj -> System.out.println(obj.getId()));
	}
	 
}

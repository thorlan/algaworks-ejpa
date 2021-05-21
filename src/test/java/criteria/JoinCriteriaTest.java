package criteria;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.ItemPedido;
import com.algaworks.ecommerce.model.Pagamento;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;
import com.algaworks.ecommerce.model.StatusPagamento;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class JoinCriteriaTest extends EntityManagerTest {

	@Test
	public void buscarPedidosComProdutoEspecifoco() {
		//pegar o produto 1
		
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);

		// JOIN VOLTA O SEGUNDO ITEM!
		Join<ItemPedido, Produto> joinItemPedidoProduto = root
                .join("itens")
                .join("produto");

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.equal(
                joinItemPedidoProduto.get("id"), 1));

		TypedQuery<Pedido> typedQuery = em.createQuery(criteriaQuery);
		List<Pedido> pedidos = typedQuery.getResultList();

		Assert.assertNotNull(pedidos);
		Assert.assertFalse(pedidos.isEmpty());

		pedidos.forEach(p -> System.out.println(p.getId()));
	}

	// @Test
	public void joinFetch() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		root.fetch("itens");
		root.fetch("notaFiscal", JoinType.LEFT); // VOLTA TODOS COM NOTA OU SEM NOTA!
		root.fetch("pagamento", JoinType.LEFT);

		criteriaQuery.select(root);

		criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));

		TypedQuery<Pedido> typedQuery = em.createQuery(criteriaQuery);
		Pedido pedido = typedQuery.getSingleResult();

		Assert.assertNotNull(pedido);
		// o fetch evita um segundo sql ser disparado quando chamamos o getItens
		Assert.assertFalse(pedido.getItens().isEmpty());

	}

	// todos os pedidos que tem correspondencia, e todos que nao tem!
	// @Test
	public void fazendoLeftOuterJoin() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);

		Join<Pedido, Pagamento> joinPagamento = root.join("pagamento", JoinType.LEFT);

		criteriaQuery.select(root);

		TypedQuery<Pedido> typedQuery = em.createQuery(criteriaQuery);
		List<Pedido> pedidos = typedQuery.getResultList();

		Assert.assertNotNull(pedidos);
		Assert.assertFalse(pedidos.isEmpty());

		pedidos.forEach(p -> System.out.println(p.getId()));
	}

	// inner join, todos os pedidos retornados tem pagamento
	// @Test
	public void fazerJoinComOn() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);

		Join<Pedido, Pagamento> joinPagamento = root.join("pagamento");
		joinPagamento.on(criteriaBuilder.equal(joinPagamento.get("status"), StatusPagamento.PROCESSANDO));

		criteriaQuery.select(root);

		TypedQuery<Pedido> typedQuery = em.createQuery(criteriaQuery);
		List<Pedido> pedidos = typedQuery.getResultList();

		Assert.assertNotNull(pedidos);
		Assert.assertFalse(pedidos.isEmpty());

		pedidos.forEach(p -> System.out.println(p));
	}

	// @Test
	public void fazerJoin() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);

		// JOIN VOLTA O SEGUNDO ITEM!
		Join<Pedido, Pagamento> joinPagamento = root.join("pagamento");

		criteriaQuery.select(root);

		criteriaQuery.where(criteriaBuilder.equal(joinPagamento.get("status"), StatusPagamento.PROCESSANDO));

		TypedQuery<Pedido> typedQuery = em.createQuery(criteriaQuery);
		List<Pedido> pedidos = typedQuery.getResultList();

		Assert.assertNotNull(pedidos);
		Assert.assertFalse(pedidos.isEmpty());

		pedidos.forEach(p -> System.out.println(p));
	}

}

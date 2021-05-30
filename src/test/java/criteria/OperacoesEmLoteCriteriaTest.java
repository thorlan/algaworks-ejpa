package criteria;

import java.math.BigDecimal;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.junit.Test;

import com.algaworks.ecommerce.model.Categoria;
import com.algaworks.ecommerce.model.Categoria_;
import com.algaworks.ecommerce.model.PagamentoBoleto_;
import com.algaworks.ecommerce.model.Pedido_;
import com.algaworks.ecommerce.model.Produto;
import com.algaworks.ecommerce.model.Produto_;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class OperacoesEmLoteCriteriaTest extends EntityManagerTest {

	@Test
	public void removerEmLoteEx() {
		// criteriaDelete
		// where usaremos o between para remover!
		// String jpql = "delete from Produto p where p.id between 5 and 12";
		em.getTransaction().begin();

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaDelete<Produto> criteriaDelete = criteriaBuilder.createCriteriaDelete(Produto.class);
		
		Root<Produto> root = criteriaDelete.from(Produto.class);

		criteriaDelete.where(criteriaBuilder.between(root.get(Produto_.id), 5, 12));
		

		Query query = em.createQuery(criteriaDelete);
		query.executeUpdate();

		em.getTransaction().commit();
	}

	// @Test
	public void atualizarEmLote() {
		em.getTransaction().begin();

		// USAMOS O CORRELATE PARA PODER UTILIZAR ATRIBUTO DA QUERY PAI NA SUBQUERY!
//        String jpql = "update Produto p set p.preco = p.preco + (p.preco * 0.1) where exists"
//    			+ " (select 1 from p.categorias c2 where c2.id = :categoriaId)";

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaUpdate<Produto> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Produto.class);
		Root<Produto> root = criteriaUpdate.from(Produto.class);

		criteriaUpdate.set(root.get(Produto_.preco),
				criteriaBuilder.prod(root.get(Produto_.preco), new BigDecimal("1.1")));

		Subquery<Integer> subquery = criteriaUpdate.subquery(Integer.class);
		Root<Produto> subqueryRoot = subquery.correlate(root);
		Join<Produto, Categoria> joinCategoria = subqueryRoot.join(Produto_.categorias);
		subquery.select(criteriaBuilder.literal(1));
		subquery.where(criteriaBuilder.equal(joinCategoria.get(Categoria_.id), 2));

		criteriaUpdate.where(criteriaBuilder.exists(subquery));

		Query query = em.createQuery(criteriaUpdate);
		query.executeUpdate();

		em.getTransaction().commit();
	}
}
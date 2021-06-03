package n1problema;

import java.util.List;

import javax.persistence.EntityGraph;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Pedido;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class ProblemaN1Test extends EntityManagerTest {

	//DUAS FORMAS DE RESOLVER ESSE PROBLEMA. COM JOIN FETCH OU ENTITY GRAPH!!!
	
    @Test
    public void resolverComEntityGraph() {
        EntityGraph<Pedido> entityGraph = em.createEntityGraph(Pedido.class);
        entityGraph.addAttributeNodes("cliente", "notaFiscal", "pagamento");

        List<Pedido> lista = em
                .createQuery("select p from Pedido p ", Pedido.class)
                .setHint("javax.persistence.loadgraph", entityGraph)
                .getResultList();

        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void resolverComFetch() {
        List<Pedido> lista = em
                .createQuery("select p from Pedido p " +
                        " join fetch p.cliente c " +
                        " join fetch p.pagamento pag " +
                        " join fetch p.notaFiscal nf", Pedido.class)
                .getResultList();

        Assert.assertFalse(lista.isEmpty());
    }
}
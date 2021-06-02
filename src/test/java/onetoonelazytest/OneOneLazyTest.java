package onetoonelazytest;
import com.algaworks.ecommerce.model.Pedido;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class OneOneLazyTest extends EntityManagerTest {

    @Test
    public void mostrarProblema() {
        System.out.println("BUSCANDO UM PEDIDO:");
        Pedido pedido = em.find(Pedido.class, 1);
        Assert.assertNotNull(pedido);

        System.out.println("----------------------------------------------------");

        System.out.println("BUSCANDO UMA LISTA DE PEDIDOS:");
        List<Pedido> lista = em
                .createQuery("select p from Pedido p", Pedido.class)
                .getResultList();
        Assert.assertFalse(lista.isEmpty());
    }
}
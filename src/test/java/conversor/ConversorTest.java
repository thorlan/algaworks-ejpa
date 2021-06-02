package conversor;

import com.algaworks.ecommerce.model.Produto;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
public class ConversorTest extends EntityManagerTest {

    @Test
    public void converter() {
        Produto produto = new Produto();
        produto.setDataCriacao(LocalDateTime.now());
        produto.setNome("Carregador de Notebook Dell");
        produto.setAtivo(Boolean.TRUE);

        em.getTransaction().begin();

        em.persist(produto);

        em.getTransaction().commit();

        em.clear();

        Produto produtoVerificacao = em.find(Produto.class, produto.getId());
        Assert.assertTrue(produtoVerificacao.getAtivo());
    }
}
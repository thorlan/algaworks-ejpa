package jpql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import javax.persistence.Query;

import org.junit.Test;

import com.algaworks.ecommerce.model.Produto;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class OperacoesEmLoteTest extends EntityManagerTest {
	
	private static final int LIMITE_INSERCOES = 4;

    @Test
    public void inserirEmLote() throws IOException {
        InputStream in = OperacoesEmLoteTest.class.getClassLoader()
                .getResourceAsStream("produtos/importar.txt");

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        em.getTransaction().begin();

        int contadorInsercoes = 0;

        for(String linha: reader.lines().collect(Collectors.toList())) {
            if (linha.isBlank()) {
                continue;
            }

            String[] produtoColuna = linha.split(";");
            Produto produto = new Produto();
            produto.setNome(produtoColuna[0]);
            produto.setDescricao(produtoColuna[1]);
            produto.setPreco(new BigDecimal(produtoColuna[2]));
            produto.setDataCriacao(LocalDateTime.now());

            em.persist(produto);

            if (++contadorInsercoes == LIMITE_INSERCOES) {
                em.flush();
                em.clear();

                contadorInsercoes = 0;

                System.out.println("---------------------------------");
            }
        }

        em.getTransaction().commit();
    }
    
    //@Test
    public void atualizarEmLote() {
    	em.getTransaction().begin();
    	String jpql = "update Produto p set p.preco = p.preco + (p.preco * 0.1) where exists"
    			+ " (select 1 from p.categorias c2 where c2.id = :categoriaId)";
    	
    	Query query = em.createQuery(jpql);
    	query.setParameter("categoriaId",2);
    	query.executeUpdate();
    	em.getTransaction().commit();
    	
    }
    
    @Test
    public void removerEmLote() {
    	em.getTransaction().begin();
    	
    	String jpql = "delete from Produto p where p.id between 5 and 8";
    	
    	Query query = em.createQuery(jpql);
    	query.executeUpdate();
    	em.getTransaction().commit();
    	
    }
    
    
	
	
}

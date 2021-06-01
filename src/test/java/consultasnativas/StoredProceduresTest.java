package consultasnativas;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Cliente;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class StoredProceduresTest extends EntityManagerTest {

	@Test
	public void chamarNamedStoredProcedure() {
		StoredProcedureQuery storedProcedureQuery = em.createNamedStoredProcedureQuery("compraram_acima_media");

		storedProcedureQuery.setParameter("ano", 2021);

		List<Cliente> lista = storedProcedureQuery.getResultList();

		Assert.assertFalse(lista.isEmpty());
	}

	// @Test
	public void exercicioParametrosProcedure() {

		StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("ajustar_preco_produto");
		storedProcedureQuery.registerStoredProcedureParameter("produto_id", Integer.class, ParameterMode.IN);
		storedProcedureQuery.registerStoredProcedureParameter("percentual_ajuste", BigDecimal.class, ParameterMode.IN);
		storedProcedureQuery.registerStoredProcedureParameter("preco_ajustado", BigDecimal.class, ParameterMode.OUT);
		storedProcedureQuery.setParameter("produto_id", 1);
		storedProcedureQuery.setParameter("percentual_ajuste", new BigDecimal(0.1));

		BigDecimal precoAjustado = (BigDecimal) storedProcedureQuery.getOutputParameterValue("preco_ajustado");

		Assert.assertEquals(new BigDecimal("878.9"), precoAjustado);
	}

	// @Test
	public void receberListaDaProcedure() {

		StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("compraram_acima_media",
				Cliente.class);
		storedProcedureQuery.registerStoredProcedureParameter("ano", Integer.class, ParameterMode.IN);
		storedProcedureQuery.setParameter("ano", 2021);
		List<Cliente> lista = storedProcedureQuery.getResultList();

		Assert.assertFalse(lista.isEmpty());
	}

	@Test
	public void usarParametrosInEOut() {

		StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("buscar_nome_produto");
		storedProcedureQuery.registerStoredProcedureParameter("produto_id", Integer.class, ParameterMode.IN);
		storedProcedureQuery.registerStoredProcedureParameter("produto_nome", String.class, ParameterMode.OUT);
		storedProcedureQuery.setParameter("produto_id", 1);
		String nome = (String) storedProcedureQuery.getOutputParameterValue("produto_nome");

		Assert.assertEquals("Kindle", nome);
	}

}
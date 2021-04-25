package cm.algaworks.ecommerce.mapeamentoavancado;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.NotaFiscal;
import com.algaworks.ecommerce.model.Pedido;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class MapsIdTest extends EntityManagerTest{

	@Test
	public void inserirPagamento() {
		entityManager.getTransaction().begin();
		
		Pedido pedido = entityManager.find(Pedido.class, 1);
		
		NotaFiscal notaFiscal = new NotaFiscal();
		//notaFiscal.setId(pedido.getId());
		notaFiscal.setPedido(pedido);
		notaFiscal.setDataEmissao(new Date());
		notaFiscal.setXml("<xml/>");
		
		entityManager.persist(notaFiscal);
		entityManager.getTransaction().commit();
		entityManager.clear();
		
		NotaFiscal notaFiscalVerificacao = entityManager.find(NotaFiscal.class, notaFiscal.getId());
		Assert.assertNotNull(notaFiscalVerificacao);
		Assert.assertEquals(pedido.getId(), notaFiscalVerificacao.getId());
	}
	

}

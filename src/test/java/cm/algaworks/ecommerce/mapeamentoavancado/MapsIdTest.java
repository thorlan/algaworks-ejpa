package cm.algaworks.ecommerce.mapeamentoavancado;

import java.time.LocalDateTime;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.NotaFiscal;
import com.algaworks.ecommerce.model.Pedido;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class MapsIdTest extends EntityManagerTest{

	@Test
	public void inserirPagamento() {
		em.getTransaction().begin();
		
		Pedido pedido = em.find(Pedido.class, 1);
		
		NotaFiscal notaFiscal = new NotaFiscal();
		//notaFiscal.setId(pedido.getId());
		notaFiscal.setPedido(pedido);
		notaFiscal.setDataEmissao(new Date());
		notaFiscal.setXml("<xml/>".getBytes());
		
		em.persist(notaFiscal);
		em.getTransaction().commit();
		em.clear();
		
		NotaFiscal notaFiscalVerificacao = em.find(NotaFiscal.class, notaFiscal.getId());
		Assert.assertNotNull(notaFiscalVerificacao);
		Assert.assertEquals(pedido.getId(), notaFiscalVerificacao.getId());
	}
	

}

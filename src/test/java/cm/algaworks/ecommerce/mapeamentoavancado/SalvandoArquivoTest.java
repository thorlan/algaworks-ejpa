package cm.algaworks.ecommerce.mapeamentoavancado;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Atributo;
import com.algaworks.ecommerce.model.NotaFiscal;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class SalvandoArquivoTest extends EntityManagerTest {

	//@Test
	public void inserirPagamento() {
		em.getTransaction().begin();

		Pedido pedido = em.find(Pedido.class, 1);

		NotaFiscal notaFiscal = new NotaFiscal();
		// notaFiscal.setId(pedido.getId());
		notaFiscal.setPedido(pedido);
		notaFiscal.setDataEmissao(new Date());
		notaFiscal.setXml(carregarNotaFiscal());

		em.persist(notaFiscal);
		em.getTransaction().commit();
		em.clear();

		NotaFiscal notaFiscalVerificacao = em.find(NotaFiscal.class, notaFiscal.getId());
		Assert.assertNotNull(notaFiscalVerificacao.getXml());
		Assert.assertTrue(notaFiscalVerificacao.getXml().length > 0);

		try {
			OutputStream out = new FileOutputStream(
					Files.createFile(Paths.get(System.getProperty("user.home") + "/xml.xml")).toFile());
			out.write(notaFiscalVerificacao.getXml());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private static byte[] carregarNotaFiscal() {
		try {
			return SalvandoArquivoTest.class.getResourceAsStream("/nota-fiscal.xml").readAllBytes();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	//@Test
	public void salvarFotoProduto() {
		
		em.getTransaction().begin();
		Produto produto = em.find(Produto.class, 3);
		produto.setAtributos((Arrays.asList(new Atributo("tela", "520x800"))));
		produto.setFoto(carregaFoto());
		
		em.getTransaction().commit();
		
		em.clear();

		Produto produtoVerificacao = em.find(Produto.class, produto.getId());
		
		Assert.assertNotNull(produtoVerificacao.getFoto());
		Assert.assertTrue(produtoVerificacao.getFoto().length > 0);
		

		try {
			OutputStream out = new FileOutputStream(
					Files.createFile(Paths.get(System.getProperty("user.home") + "/foto.jpg")).toFile());
			out.write(produtoVerificacao.getFoto());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static byte[] carregaFoto() {
		try {
			return SalvandoArquivoTest.class.getResourceAsStream("/produto.jpg").readAllBytes();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}

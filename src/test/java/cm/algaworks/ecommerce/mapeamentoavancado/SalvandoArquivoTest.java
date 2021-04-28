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

	@Test
	public void inserirPagamento() {
		entityManager.getTransaction().begin();

		Pedido pedido = entityManager.find(Pedido.class, 1);

		NotaFiscal notaFiscal = new NotaFiscal();
		// notaFiscal.setId(pedido.getId());
		notaFiscal.setPedido(pedido);
		notaFiscal.setDataEmissao(new Date());
		notaFiscal.setXml(carregarNotaFiscal());

		entityManager.persist(notaFiscal);
		entityManager.getTransaction().commit();
		entityManager.clear();

		NotaFiscal notaFiscalVerificacao = entityManager.find(NotaFiscal.class, notaFiscal.getId());
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

	@Test
	public void salvarFotoProduto() {
		
		entityManager.getTransaction().begin();
		Produto produto = entityManager.find(Produto.class, 3);
		produto.setAtributos((Arrays.asList(new Atributo("tela", "520x800"))));
		produto.setFoto(carregaFoto());
		
		entityManager.getTransaction().commit();
		
		entityManager.clear();

		Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
		
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

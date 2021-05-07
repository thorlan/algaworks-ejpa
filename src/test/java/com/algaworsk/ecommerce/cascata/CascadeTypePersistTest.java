package com.algaworsk.ecommerce.cascata;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Atributo;
import com.algaworks.ecommerce.model.Categoria;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.ItemPedido;
import com.algaworks.ecommerce.model.ItemPedidoId;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;
import com.algaworks.ecommerce.model.SexoCliente;
import com.algaworks.ecommerce.model.StatusPedido;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class CascadeTypePersistTest extends EntityManagerTest {

    // @Test
    public void persistirPedidoComItens() {
        Cliente cliente = em.find(Cliente.class, 1);
        Produto produto = em.find(Produto.class, 1);

        Pedido pedido = new Pedido();
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setCliente(cliente);
        pedido.setTotal(produto.getPreco());
        pedido.setStatus(StatusPedido.AGUARDANDO);

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setId(new ItemPedidoId());
        itemPedido.setPedido(pedido);
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(1);
        itemPedido.setPrecoProduto(produto.getPreco());

        pedido.setItens(Arrays.asList(itemPedido)); // CascadeType.PERSIST

        em.getTransaction().begin();
        em.persist(pedido);
        em.getTransaction().commit();

        em.clear();

        Pedido pedidoVerificacao = em.find(Pedido.class, pedido.getId());
        Assert.assertNotNull(pedidoVerificacao);
        Assert.assertFalse(pedidoVerificacao.getItens().isEmpty());

    }

    //@Test
    public void persistirItemPedidoComPedido() {
        Cliente cliente = em.find(Cliente.class, 1);
        Produto produto = em.find(Produto.class, 1);

        Pedido pedido = new Pedido();
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setCliente(cliente);
        pedido.setTotal(produto.getPreco());
        pedido.setStatus(StatusPedido.AGUARDANDO);

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setId(new ItemPedidoId());
        itemPedido.setPedido(pedido);// Não é necessário CascadeType.PERSIST porque possui @MapsId.
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(1);
        itemPedido.setPrecoProduto(produto.getPreco());

        em.getTransaction().begin();
        em.persist(itemPedido);
        em.getTransaction().commit();

        em.clear();

        Pedido pedidoVerificacao = em.find(Pedido.class, pedido.getId());
        Assert.assertNotNull(pedidoVerificacao);
    }

    // @Test
    public void persistirPedidoComCliente() {
        Cliente cliente = new Cliente();
        cliente.setDataNascimento(LocalDate.of(1980, 1, 1));
        cliente.setSexo(SexoCliente.MASCULINO);
        cliente.setNome("José Carlos");
        cliente.setCpf("01234567890");

        Pedido pedido = new Pedido();
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setCliente(cliente); // CascadeType.PERSIST
        pedido.setTotal(BigDecimal.ZERO);
        pedido.setStatus(StatusPedido.AGUARDANDO);

        em.getTransaction().begin();
        em.persist(pedido);
        em.getTransaction().commit();

        em.clear();

        Cliente clienteVerificacao = em.find(Cliente.class, cliente.getId());
        Assert.assertNotNull(cliente);
    }
    
    @Test//salvar produto com categoria em cascata
    public void persistirProdutoComCategoria() {
    	
    	  Produto produto = new Produto();
    	  produto.setAtributos(Arrays.asList(new Atributo("nome1", "valor1")));
    	  produto.setDataCriacao(LocalDateTime.now());
    	  produto.setDescricao("Descricao do produto");
    	  //produto.setEstoque(estoque);
    	  produto.setFoto("foto".getBytes());
    	  //produto.setItemPedido(itemPedido);
    	  produto.setNome("Nome do produto");
    	  produto.setPreco(BigDecimal.TEN);
    	  produto.setTags(Arrays.asList("tag1","tag2"));
    	  
    	  Categoria categoria = new Categoria();
    	  categoria.setNome("Um Teste de categoria");
    	  produto.setCategorias(Arrays.asList(categoria));
    	  
    	  em.getTransaction().begin();
          em.persist(produto);
          em.getTransaction().commit();

          em.clear();

          Produto produtoVerificacao = em.find(Produto.class, produto.getId());
          Assert.assertNotNull(produtoVerificacao);
          Assert.assertNotNull(produtoVerificacao.getCategorias());
          Assert.assertEquals(produtoVerificacao.getCategorias().get(0).getNome(), categoria.getNome());
          
          Categoria categoriaVerificacao = em.find(Categoria.class, categoria.getId());
          Assert.assertNotNull(categoriaVerificacao);
          Assert.assertEquals(categoriaVerificacao.getNome(), categoria.getNome());
    }
    
    
    
    
}
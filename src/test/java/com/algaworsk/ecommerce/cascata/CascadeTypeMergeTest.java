package com.algaworsk.ecommerce.cascata;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.model.Categoria;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.ItemPedido;
import com.algaworks.ecommerce.model.ItemPedidoId;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;
import com.algaworks.ecommerce.model.StatusPedido;

import cm.algaworks.ecommerce.iniciandocomjpa.EntityManagerTest;

public class CascadeTypeMergeTest extends EntityManagerTest {

     //@Test
    public void atualizarPedidoComItens() {
        Cliente cliente = em.find(Cliente.class, 1);
        Produto produto = em.find(Produto.class, 1);

        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.AGUARDANDO);

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setId(new ItemPedidoId());
        itemPedido.getId().setPedidoId(pedido.getId());
        itemPedido.getId().setProdutoId(produto.getId());
        itemPedido.setPedido(pedido);
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(3);
        itemPedido.setPrecoProduto(produto.getPreco());

        pedido.setItens(Arrays.asList(itemPedido)); // CascadeType.MERGE

        em.getTransaction().begin();
        em.merge(pedido);
        em.getTransaction().commit();

        em.clear();

        ItemPedido itemPedidoVerificacao = em.find(ItemPedido.class, itemPedido.getId());
        Assert.assertTrue(itemPedidoVerificacao.getQuantidade().equals(3));
    }

     //@Test
    public void atualizarItemPedidoComPedido() {
        Cliente cliente = em.find(Cliente.class, 1);
        Produto produto = em.find(Produto.class, 1);

        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.PAGO);

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setId(new ItemPedidoId());
        itemPedido.getId().setPedidoId(pedido.getId());
        itemPedido.getId().setProdutoId(produto.getId());
        itemPedido.setPedido(pedido); // CascadeType.MERGE
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(5);
        itemPedido.setPrecoProduto(produto.getPreco());

        pedido.setItens(Arrays.asList(itemPedido));

        em.getTransaction().begin();
        em.merge(itemPedido);
        em.getTransaction().commit();

        em.clear();

        ItemPedido itemPedidoVerificacao = em.find(ItemPedido.class, itemPedido.getId());
        Assert.assertTrue(StatusPedido.PAGO.equals(itemPedidoVerificacao.getPedido().getStatus()));
    }
    
    //instanciar produto  (pegar da base)
    //instanciar categoria (pegar da base)
    //atualizar o produto e implicitamente atualizar a categoria
    @Test
    public void atualizarCategoriaComProduto() {
//        Produto produto = em.find(Produto.class, 1);
//        Categoria categoria = em.find(Categoria.class, 2);
//        categoria.setNome("Nomereal");
//        produto.getCategorias().add(categoria);
//
//        em.getTransaction().begin();
//        em.merge(produto);
//        em.getTransaction().commit();
//
//        em.clear();
//
//        Categoria categoriaVerificacao = em.find(Categoria.class, 2);
//        Assert.assertTrue(categoriaVerificacao.getNome().equals("Nomereal"));
        
        Produto produto = new Produto();
        produto.setId(1);
        produto.setDataUltimaAtualizacao(LocalDateTime.now());
        produto.setPreco(new BigDecimal(500));
        produto.setNome("Kindle");
        produto.setDescricao("Agora com iluminação embutida ajustável.");

        Categoria categoria = new Categoria();
        categoria.setId(2);
        categoria.setNome("Tablets");

        produto.setCategorias(Arrays.asList(categoria)); // CascadeType.MERGE

        em.getTransaction().begin();
        em.merge(produto);
        em.getTransaction().commit();

        em.clear();

        Categoria categoriaVerificacao = em.find(Categoria.class, categoria.getId());
        Assert.assertEquals("Tablets", categoriaVerificacao.getNome());
        
    }
}
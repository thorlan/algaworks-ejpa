package com.algaworks.ecommerce.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@SecondaryTable(name = "cliente_detalhe", pkJoinColumns = @PrimaryKeyJoinColumn(name = "cliente_id"), foreignKey = @ForeignKey(name = "fk_cliente_detalhe_cliente"))
@Table(name = "cliente", uniqueConstraints = { 
		@UniqueConstraint(name = "unq_cpf", columnNames = { "cpf" })},
		indexes = { @Index(name = "idx_nome", columnList = "nome")})
public class Cliente extends EntitadeBaseInteger {
	
	@Column(length = 100, nullable = false)
	private String nome;
	
	@Column(length = 14, nullable = false)
	private String cpf;
	
	@ElementCollection
	@CollectionTable(name = "cliente_contato", 
					joinColumns = @JoinColumn(name = "cliente_id", nullable = false, foreignKey = @ForeignKey(name ="fk_cliente_contato_cliente")))
	@MapKeyColumn(name = "tipo")
	@Column(name = "descricao")
	private Map<String, String> contatos;
	
	@Transient
	private String primeiroNome;
	
	@Column(table = "cliente_detalhe", nullable = false, length = 30)
	@Enumerated(EnumType.STRING)
	private SexoCliente sexo;
	
	@Column(table = "cliente_detalhe", name = "data_nascimento")
	private LocalDate dataNascimento;
	
	@OneToMany(mappedBy = "cliente")
	private List<Pedido> pedidos;
	
	@PostLoad
	public void configurarPrimeiroNome() {
		if (nome != null && !nome.isBlank()) {
			int index = nome.indexOf(" ");
			if (index > -1) {
				primeiroNome = nome.substring(0, index);
			}
		}
	}
	
}

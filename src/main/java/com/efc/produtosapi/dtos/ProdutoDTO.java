package com.efc.produtosapi.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ProdutoDTO {

    @NotBlank
    private String nome;
    @Min(0)
    private Float preco;

    public ProdutoDTO() {
    }

    public ProdutoDTO(@NotBlank String nome, @Min(0) Float preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }
}

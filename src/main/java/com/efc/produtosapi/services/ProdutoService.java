package com.efc.produtosapi.services;

import com.efc.produtosapi.entities.Produto;
import com.efc.produtosapi.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    public List<Produto> findAll(){
        return produtoRepository.findAll();
    }

    public Optional<Produto> findById(int id){
        return produtoRepository.findById(id);
    }

    public Optional<Produto> findByNome(String nome){
        return produtoRepository.findByNome(nome);
    }

    public void save(Produto produto){
        produtoRepository.save(produto);
    }

    public void delete(int id){
        produtoRepository.deleteById(id);
    }

    public boolean existsById(int id){
        return produtoRepository.existsById(id);
    }

    public boolean existsByNome(String nome){
        return produtoRepository.existsByNome(nome);
    }

}

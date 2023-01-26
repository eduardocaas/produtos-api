package com.efc.produtosapi.controllers;

import com.efc.produtosapi.dtos.Mensagem;
import com.efc.produtosapi.dtos.ProdutoDTO;
import com.efc.produtosapi.entities.Produto;
import com.efc.produtosapi.services.ProdutoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
@CrossOrigin(origins = "http://localhost:4200")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    @GetMapping("/lista")
    public ResponseEntity<List<Produto>> findAll() {
        List<Produto> list = produtoService.findAll();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/ler/{id}")
    public ResponseEntity<Produto> findById(@PathVariable("id") int id) {
        if(!produtoService.existsById(id)) {
            return new ResponseEntity(new Mensagem("Produto não existente"), HttpStatus.NOT_FOUND);
        }
        Produto produto = produtoService.findById(id).get();
        return new ResponseEntity(produto, HttpStatus.OK);
    }

    @GetMapping("/lerNome/{nome}")
    public ResponseEntity<Produto> findByNome(@PathVariable("nome") String nome) {
        if(!produtoService.existsByNome(nome)) {
            return new ResponseEntity(new Mensagem("Produto não existente"), HttpStatus.NOT_FOUND);
        }
        Produto produto = produtoService.findByNome(nome).get();
        return new ResponseEntity(produto, HttpStatus.OK);
    }

    @PostMapping("/criar")
    public ResponseEntity<?> create(@RequestBody ProdutoDTO produtoDTO) {
        if(StringUtils.isBlank(produtoDTO.getNome())){
            return new ResponseEntity(new Mensagem("Nome do produto obrigatório!"), HttpStatus.BAD_REQUEST);
        }
        if(produtoDTO.getPreco() < 0){
            return new ResponseEntity(new Mensagem("O preço do produto deve ser maior que 0"), HttpStatus.BAD_REQUEST);
        }
        if(produtoService.existsByNome(produtoDTO.getNome())){
            return new ResponseEntity(new Mensagem("Nome do produto já existente"), HttpStatus.BAD_REQUEST);
        }
        Produto produto = new Produto(produtoDTO.getNome(), produtoDTO.getPreco());
        produtoService.save(produto);
        return new ResponseEntity(new Mensagem("Produto " + produto.getNome() + " criado com sucesso!"), HttpStatus.CREATED);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody ProdutoDTO produtoDTO){
        if(!produtoService.existsById(id)){
            return new ResponseEntity(new Mensagem("Este produto não existe"), HttpStatus.NOT_FOUND);
        }
        if(produtoService.existsByNome(produtoDTO.getNome()) && produtoService.findByNome(produtoDTO.getNome()).get().getId() != id ){
            return new ResponseEntity(new Mensagem("Este nome já existe"), HttpStatus.BAD_REQUEST);
        }
        if(StringUtils.isBlank(produtoDTO.getNome())){
            return new ResponseEntity(new Mensagem("Nome do produto obrigatório!"), HttpStatus.NOT_FOUND);
        }
        if(produtoDTO.getPreco() < 0){
            return new ResponseEntity(new Mensagem("O preço do produto deve ser maior que 0"), HttpStatus.BAD_REQUEST);
        }

        Produto produto = produtoService.findById(id).get();
        produto.setNome(produtoDTO.getNome());
        produto.setPreco(produtoDTO.getPreco());
        produtoService.save(produto);
        return new ResponseEntity(new Mensagem("Produto " + produto.getNome() + " atualizado com sucesso!"), HttpStatus.OK);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> delete (@PathVariable("id") int id) {
        if(!produtoService.existsById(id)){
            return new ResponseEntity(new Mensagem("Este produto não existe"), HttpStatus.NOT_FOUND);
        }
        produtoService.delete(id);
        return new ResponseEntity(new Mensagem("Produto deletado com sucesso!"), HttpStatus.OK);
    }


}

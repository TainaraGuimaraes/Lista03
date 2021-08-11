/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.dev1.tainara.Contato.controller;

import br.edu.ifrs.restinga.dev1.tainara.Contato.dao.ContatoDAO;
import br.edu.ifrs.restinga.dev1.tainara.Contato.entidade.Contato;
import br.edu.ifrs.restinga.dev1.tainara.Contato.erro.NaoEncontrado;
import br.edu.ifrs.restinga.dev1.tainara.Contato.erro.RequisicaoInvalida;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Suporte
 */
@RestController
@RequestMapping("/api")
public class Contatos {
    @Autowired
    ContatoDAO contatoDAO;
    
    @RequestMapping(path = "/contatos/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Contato cadastrar(@RequestBody Contato contato) {
        
        if(contato.getNomeCompleto() == null || contato.getNomeCompleto().isEmpty()){
            throw new RequisicaoInvalida("Nome deve ser preenchido");
        }if(contato.getTelefone() == null || contato.getTelefone().isEmpty()){
            throw new RequisicaoInvalida("Telefone deve ser preenchido");
        }else {
           
            Contato contatoBanco = contatoDAO.save(contato);
            return contatoBanco;
        }
    }


    @RequestMapping(path = "/contatos/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Contato> listar() {
        return contatoDAO.findAll();    
    }
    
    @RequestMapping(path = "/contatos/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Contato buscar(@PathVariable int id) {
        final Optional<Contato> findById = contatoDAO.findById(id);
        if(findById.isPresent()) {
            return findById.get();
        } else {
            throw new NaoEncontrado("ID não encontrada!");
        }
    }
    
    
    @RequestMapping(path="/contatos/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagar(@PathVariable int id) {

        if(contatoDAO.existsById(id)) {

            contatoDAO.deleteById(id);
            
        } else {
            throw new NaoEncontrado("ID não encontrada!");
        }
                
        
    }
    
    @RequestMapping(path="/contatos/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable int id, @RequestBody Contato contato) {
        final Contato contatoBanco = this.buscar(id);

        if(contato.getNomeCompleto() == null || contato.getNomeCompleto().isEmpty()){
            throw new RequisicaoInvalida("Nome deve ser preenchido");
        }if(contato.getTelefone() == null || contato.getTelefone().isEmpty()){
            throw new RequisicaoInvalida("Telefone deve ser preenchido");
        }{
        contatoBanco.setNomeCompleto(contato.getNomeCompleto());
        contatoBanco.setTelefone(contato.getTelefone());
        contatoDAO.save(contatoBanco);
        }
    }
    
    @RequestMapping(path = "/contatos/pesquisa/nomeCompleto", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Contato> pesquisaNomeCompleto(
            @RequestParam(required = false) String contem){
        if(contem != null)
            return contatoDAO.findByNomeCompletoContaining(contem);
         else
                throw new RequisicaoInvalida("Nome do contato nao encontrado");
        
    }
    
    @RequestMapping(path = "/contatos/pesquisa/telefone", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Contato> pesquisaTelefone(
            @RequestParam(required = false) String contem){
        if(contem != null)
            return contatoDAO.findByTelefoneContaining(contem);
         else
                throw new RequisicaoInvalida("Telefone do contato nao encontrado");
        
    }
    
}

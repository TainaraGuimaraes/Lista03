/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.dev1.tainara.Contato.dao;

import br.edu.ifrs.restinga.dev1.tainara.Contato.entidade.Contato;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Suporte
 */
public interface ContatoDAO extends CrudRepository<Contato, Integer>{
    List <Contato> findByNomeCompletoContaining(String nomeCompleto);
    List <Contato> findByTelefoneContaining(String telefone);
}

package br.com.incidisfy.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.incidisfy.persistence.model.Reclamacao;

public interface ReclamacaoRepository extends JpaRepository<Reclamacao, Long> {

}

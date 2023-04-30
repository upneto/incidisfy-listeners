package br.com.incidisfy.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.incidisfy.persistence.dao.ReclamacaoRepository;
import br.com.incidisfy.persistence.model.Reclamacao;
import br.com.incidisfy.resources.exception.DaoException;
import br.com.incidisfy.service.payload.ReclamacaoPayload;

@Service
public class ReclamacaoService {

	@Autowired
	private ReclamacaoRepository repository;
	
	private static final SimpleDateFormat _SDF = new SimpleDateFormat("ddMMyyyy-HHmmss");

	/**
	 * 
	 * @param reclamacao
	 * @throws DaoException
	 */
	public void insert(ReclamacaoPayload reclamacao) throws DaoException {
		try {
			this.repository.save(Reclamacao.builder()
					.codigo(this.buildCodigoReclamacao(reclamacao))
					.codigoCliente(reclamacao.getReclamacao().getCodigoCliente())
					.codigoCategoria(reclamacao.getReclamacao().getCodigoCategoria())
					.codigoProduto(reclamacao.getReclamacao().getCodigoProduto())
					.dataCriacao(new Date())
					.statusAberto(true)
					.reincidente(false)
					.descricao(reclamacao.getReclamacao().getDescricao())
					.build());
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e);
		}
	}

	/**
	 * Monta ID da reclamacao
	 * @param reclamacao
	 * @return
	 */
	private String buildCodigoReclamacao(ReclamacaoPayload reclamacao) {
		StringBuilder sb = new StringBuilder();
		sb.append(reclamacao.getReclamacao().getCodigoCliente());
		sb.append(reclamacao.getReclamacao().getCodigoCategoria());
		sb.append(reclamacao.getReclamacao().getCodigoProduto());
		return String.format("%020d", Long.parseLong(sb.toString())) + "_" + _SDF.format(new Date());
	}
}

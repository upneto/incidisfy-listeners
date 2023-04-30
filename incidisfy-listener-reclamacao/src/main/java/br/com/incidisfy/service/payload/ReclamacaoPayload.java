package br.com.incidisfy.service.payload;

import br.com.incidisfy.persistence.model.Reclamacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReclamacaoPayload {

	private Reclamacao reclamacao;
	
}

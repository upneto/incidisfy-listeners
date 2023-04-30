package br.com.incidisfy.service.payload;

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
public class ClienteMessage {

	private Long documento;	
	private String nome;
	private String nomeRazaoSocial;
	private int tipoPessoa;
}

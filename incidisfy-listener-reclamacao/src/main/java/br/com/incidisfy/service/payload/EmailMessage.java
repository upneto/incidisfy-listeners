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
public class EmailMessage {

	private String codigoReclamacao;
	private String message;
	private String destinatario;
	
	private ClienteMessage cliente;
}

package br.com.incidisfy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.incidisfy.service.payload.EmailMessage;
import br.com.incidisfy.service.payload.ReclamacaoPayload;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;

@Component
public class MessageReaderService {
	
	/** Logger */
	public static Logger LOGGER = LoggerFactory.getLogger(MessageReaderService.class);
	
	@Autowired
	private MessageSenderService messageSender;
	
	@Autowired
	private ReclamacaoService service;
	
	
	@SqsListener(value= "${spring.messages.queue.reclamacao}", deletionPolicy=SqsMessageDeletionPolicy.ON_SUCCESS)
	public void receiveMessage(String message, @Header("SenderId") String senderId) {
		LOGGER.info(" => Obteve mensagem!!!");
        LOGGER.debug("Message " + message);
        
        try {
        	ReclamacaoPayload reclamacao = new ObjectMapper().readValue(message, ReclamacaoPayload.class);
        	
        	// Insere reclamacao na base de dados
        	this.service.insert(reclamacao);
        	
        	// Envia email
        	this.messageSender.sendCustomerEmail(this.getMessage(reclamacao));
        	
        	LOGGER.info(" => Email enviado!!!");
		} catch (Exception e) {
			LOGGER.error("Não foi possivel enviar o email!", e);
		}  	
	}
	
	/**
	 * 
	 * @param reclamacao
	 * @return
	 */
	private EmailMessage getMessage(ReclamacaoPayload reclamacao) {
		
		return new EmailMessage();
	}
}

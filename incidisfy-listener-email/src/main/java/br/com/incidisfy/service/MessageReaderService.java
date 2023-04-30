package br.com.incidisfy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.incidisfy.service.payload.EmailMessage;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;

@Service
public class MessageReaderService {
	
	/** Logger */
	public static Logger LOGGER = LoggerFactory.getLogger(MessageReaderService.class);
	
	@Autowired
	private MailSenderService mailSender;

	@SqsListener(value= "${spring.messages.queue.email}", deletionPolicy=SqsMessageDeletionPolicy.ON_SUCCESS)
	public void receiveMessage(String message, @Header("SenderId") String senderId) {
		LOGGER.info(" => Obteve mensagem!!!");
        LOGGER.debug("Message " + message);
        
        try {
        	EmailMessage ordemServico = new ObjectMapper().readValue(message, EmailMessage.class);
        	this.mailSender.sendEmail(ordemServico);
        	LOGGER.info(" => Email enviado!!!");
		} catch (Exception e) {
			LOGGER.error("Não foi possivel enviar o email!", e);
		}  	
		
	}
}

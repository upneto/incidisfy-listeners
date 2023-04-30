package br.com.incidisfy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import br.com.incidisfy.resources.exception.MessageQueueException;
import br.com.incidisfy.service.payload.EmailMessage;
import io.awspring.cloud.messaging.core.QueueMessageChannel;

@Service
public class MessageSenderService {
	
	@Value("${spring.messages.queue.email}")
	private String queueEmail;

	@Autowired
	private AmazonSQSAsync amazonSqs;
	
	/** Conversor JSON */
	private static final ObjectWriter _WRITER = new ObjectMapper().writer().withDefaultPrettyPrinter();
	
	@Autowired
	public MessageSenderService(final AmazonSQSAsync amazonSQSAsync) {
		this.amazonSqs = amazonSQSAsync;
	}

	/**
	 * Sender Reclamacao
	 * @param messagePayload
	 * @return
	 */
	protected boolean sendEmail(final String messagePayload) {
		MessageChannel messageChannel = new QueueMessageChannel(this.amazonSqs, this.queueEmail);
		return this.send(messagePayload, messageChannel);
	}

	/**
	 * 
	 * @param messagePayload
	 * @param messageChannel
	 * @return
	 */
	private boolean send(final String messagePayload, MessageChannel messageChannel) {
		Message<String> message = MessageBuilder.withPayload(messagePayload)
				.setHeader("sender", "app1")
				.setHeaderIfAbsent("country", "AE")
				.build();

		long waitTimeoutMillis = 5000;
		boolean sentStatus = messageChannel.send(message, waitTimeoutMillis);
		return sentStatus;
	}

	/**
	 * 
	 * @param reclamacao
	 * @throws MessageQueueException 
	 */
	public void sendCustomerEmail(EmailMessage messageNody) throws MessageQueueException {
		try {
			final String JSON = _WRITER.writeValueAsString(messageNody);
			this.sendEmail(JSON);
		} catch (JsonProcessingException e) {
			throw new MessageQueueException("Erro na conversão da reclamação em formato JSON!", e);
		}	
	}
}

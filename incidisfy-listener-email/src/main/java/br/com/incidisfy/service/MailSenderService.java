package br.com.incidisfy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import br.com.incidisfy.service.payload.EmailMessage;

@Service
public class MailSenderService {
	
	@Value("${spring.mail.username}")
	private String masterMail = null;
	
	@Value("${spring.mail.password}")
	private String masterMailPass = null;
	
	@Autowired
    private JavaMailSender mailSender = null;
	
	public void sendEmail(EmailMessage mensagem) {
        
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(new String[] { mensagem.getDestinatario(), this.masterMail });
		message.setSubject("INCIDISFY - Reclamacao: " + mensagem.getCodigoReclamacao());
		message.setText(this.getBody(mensagem));

		this.mailSender.send(message);
    }

	/**
	 * 
	 * @param ordemServico
	 * @return
	 */
	private String getBody(EmailMessage mensagem) {
		StringBuilder bodyMessage = new StringBuilder();
		bodyMessage.append("Presado(a) Sr(a) ").append(mensagem.getCliente()).append("\n\n");
		bodyMessage.append(mensagem.getMessage()).append("\n\n\\n\\n");
		bodyMessage.append("Obrigado.");		
		return bodyMessage.toString();
	}
}

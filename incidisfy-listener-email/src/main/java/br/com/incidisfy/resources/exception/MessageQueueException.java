package br.com.incidisfy.resources.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author Ulisses Neto
 *
 */
@Getter
@AllArgsConstructor
public class MessageQueueException extends Exception {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 7859805007260615589L;
	
	/**
	 * CONSTRUTOR
	 * 
	 * @param message
	 */
	public MessageQueueException(String message) {
		super(message);
	}

	/**
	 * CONSTRUTOR
	 * 
	 * @param message
	 * @param throwable
	 */
	public MessageQueueException(String message, Throwable throwable) {
		super(message, throwable);
	}
}

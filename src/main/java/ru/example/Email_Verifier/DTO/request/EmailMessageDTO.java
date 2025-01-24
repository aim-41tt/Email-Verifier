package ru.example.Email_Verifier.DTO.request;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import ru.example.Email_Verifier.entity.enums.MessageType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailMessageDTO {

	private String email;
	@JsonProperty("messageType")
	private MessageType messageType;
	private String link;

	public EmailMessageDTO() {
	}

	/**
	 * @param email
	 * @param messageType
	 */
	public EmailMessageDTO(String email, MessageType messageType) {
		this.email = email;
		this.messageType = messageType;
	}

	/**
	 * @param email
	 * @param messageType
	 * @param link
	 */
	public EmailMessageDTO(String email, MessageType messageType, String link) {
		this.email = email;
		this.messageType = messageType;
		this.link = link;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the messageType
	 */
	public MessageType getMessageType() {
		return messageType;
	}

	/**
	 * @param messageType the messageType to set
	 */
	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public String toString() {
		return "EmailMessageDTO [email=" + email + ", messageType=" + messageType + ", link=" + link + "]";
	}

	@JsonIgnore
	public boolean isEmpty() {
		return email.isEmpty();
	}

}

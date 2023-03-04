package com.masters.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	
	@Autowired private JavaMailSender javaMailSender;
	
	public void sendEmail() {
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("devniattigala@gmail.com");
		message.setTo("devniattigala@gmail.com");
		message.setSubject("Test Email");
		message.setText("This is a test email");
		
		javaMailSender.send(message);
	}
	
	public void sendNotification(String email, String subject, String Message) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("anuthara.attigala@gmail.com");
		message.setTo(email);
		message.setSubject(subject);
		message.setText(Message);
		
		javaMailSender.send(message);
	}
}

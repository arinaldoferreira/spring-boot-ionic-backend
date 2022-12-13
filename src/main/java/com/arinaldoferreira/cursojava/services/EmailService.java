package com.arinaldoferreira.cursojava.services;

import org.springframework.mail.SimpleMailMessage;

import com.arinaldoferreira.cursojava.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}

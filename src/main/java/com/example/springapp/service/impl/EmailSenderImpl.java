package com.example.springapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.springapp.service.EmailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderImpl implements EmailSender {

	@Value("${spring.mail.username}")
	private String applicationEmail;

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void sendEmail(String to, String title, String content) {
		MimeMessage mail = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mail, true);
			helper.setTo(to);
			// helper.setReplyTo("newsletter@codecouple.pl");
			helper.setFrom(applicationEmail);
			helper.setSubject(title);
			helper.setText(content, true);

		} catch (MessagingException e) {
			e.printStackTrace();
		}

		javaMailSender.send(mail);
	}
}
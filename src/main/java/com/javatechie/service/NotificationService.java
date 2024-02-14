package com.javatechie.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
@Slf4j
public class NotificationService {

    @Autowired
    private ReportService reportService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${report.send.email.toList}")
    private String toEMails;

    public String sendDailyReports(byte[] reportContent) throws MessagingException, IOException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom(sender);
        helper.setTo(toEMails.split(","));
        helper.setSubject("Daily User OnBoarding Report - " + System.currentTimeMillis());
        helper.setText("Olá Time\n Por favor verificar a planilha em anexo com a lista de usuarios atualizada !!");

        ByteArrayResource content = new ByteArrayResource(reportContent);
        helper.addAttachment(new Date().getTime()+"_userTransaction.xlsx", content);

        javaMailSender.send(mimeMessage);
        return "EMail enviado com suacesso com o anexo!";
    }


}

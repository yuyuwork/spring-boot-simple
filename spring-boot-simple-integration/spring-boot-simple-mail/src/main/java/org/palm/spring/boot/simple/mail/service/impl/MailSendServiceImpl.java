package org.palm.spring.boot.simple.mail.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.palm.spring.boot.simple.mail.service.MailSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Slf4j
@Service
public class MailSendServiceImpl implements MailSendService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void sendSimpleEmail(String deliver, String[] receiver, String[] carbonCopy, String subject, String content) throws Exception {
        long startTimestamp = System.currentTimeMillis();
        log.info("Start send mail ... ");

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(deliver);
            message.setTo(receiver);
            message.setCc(carbonCopy);
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
            log.info("Send mail success, cost {} million seconds", System.currentTimeMillis() - startTimestamp);
        } catch (MailException e) {
            log.error("Send mail failed, error message is : {} \n", e);
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void sendHtmlEmail(String deliver, String[] receiver, String[] carbonCopy, String subject, String content, boolean isHtml) throws Exception {
        long startTimestamp = System.currentTimeMillis();
        log.info("Start send email ...");

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);

            messageHelper.setFrom(deliver);
            messageHelper.setTo(receiver);
            messageHelper.setCc(carbonCopy);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, isHtml);
            mailSender.send(message);
            log.info("Send email success, cost {} million seconds", System.currentTimeMillis() - startTimestamp);
        } catch (MessagingException e) {
            log.error("Send email failed, error message is {} \n", e);
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void sendAttachmentFileEmail(String deliver, String[] receiver, String[] carbonCopy, String subject, String content, boolean isHtml, String fileName, File file) throws Exception {
        long startTimestamp = System.currentTimeMillis();
        log.info("Start send mail ...");

        try {
            MimeMessage message = mailSender.createMimeMessage();
            //true表示需要创建一个multipart message
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            //MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            messageHelper.setFrom(deliver);
            messageHelper.setTo(receiver);
            messageHelper.setCc(carbonCopy);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, isHtml);

            //注意项目路径问题，自动补用项目路径
            //FileSystemResource fileAttachment = new FileSystemResource(new File("src/main/resources/static/image/picture.jpg"));
            //String fileName = fileAttachment.substring(fileAttachment.lastIndexOf(File.separator));
            messageHelper.addAttachment(fileName, file);
            mailSender.send(message);
            log.info("Send mail success, cost {} million seconds", System.currentTimeMillis() - startTimestamp);
        } catch (MessagingException e) {
            log.error("Send mail failed, error message is {}\n", e);
            throw new Exception(e.getMessage());
        }

        //发送带静态资源的邮件
        //第二个参数指定发送的是HTML格式,同时cid:是固定的写法，添加内联资源，一个id对应一个资源，最终通过id来找到该资源
        //helper.setText("<html><body>带静态资源的邮件内容 图片:<img src='cid:picture' /></body></html>", true);
        //FileSystemResource file = new FileSystemResource(new File("src/main/resources/static/image/picture.jpg"));
        //helper.addInline("picture",file);
    }

    @Override
    public void sendTemplateEmail(String deliver, String[] receiver, String[] carbonCopy, String subject, String template, Context context) throws Exception {
        long startTimestamp = System.currentTimeMillis();
        log.info("Start send mail ...");

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);

            String content = templateEngine.process(template, context);
            messageHelper.setFrom(deliver);
            messageHelper.setTo(receiver);
            messageHelper.setCc(carbonCopy);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
            mailSender.send(message);
            log.info("Send mail success, cost {} million seconds", System.currentTimeMillis() - startTimestamp);
        } catch (MessagingException e) {
            log.error("Send mail failed, error message is {} \n", e.getMessage());
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

}

package org.palm.spring.boot.simple.mail.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.palm.spring.boot.simple.mail.MailApplication;
import org.palm.spring.boot.simple.mail.service.MailSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;

import static org.junit.Assert.assertFalse;

/**
 * 运行需要重新配置下yml中的配置值
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MailApplication.class)
/*@ActiveProfiles("163")*/
public class MailServiceTest {

    @Autowired
    private MailSendService mailSendService;

    @Value("${spring.mail.username}")
    private String Sender;//读取配置文件中的参数

    @Test
    public void sendSimpleEmail() {
        String deliver = "你的邮件地址：dyc87112@qq.com";
        String[] receiver = {"接收者邮件地址：dyc87112@qq.com"};
        String[] carbonCopy = {"抄送者邮件地址"};
        String subject = "This is a simple email";
        String content = "This is a simple content";

        try {
            mailSendService.sendSimpleEmail(deliver, receiver, carbonCopy, subject, content);
        } catch (Exception e) {
            assertFalse("Send simple email failed", true);
            e.printStackTrace();
        }
    }

    @Test
    public void sendHtmlEmail() {
        String deliver = "你的邮件地址";
        String[] receiver = {"接收者邮件地址"};
        String[] carbonCopy = {"抄送者邮件地址"};
        String subject = "This is a HTML content email";
        String content = "<h1>This is HTML content email </h1>";

        boolean isHtml = true;
        try {
            mailSendService.sendHtmlEmail(deliver, receiver, carbonCopy, subject, content, isHtml);
        } catch (Exception e) {
            assertFalse("Send HTML content email failed", true);
            e.printStackTrace();
        }
    }

    @Test
    public void sendAttachmentFileEmail() {
        String FILE_PATH = "文件地址";
        String deliver = "你的邮件地址";
        String[] receiver = {"接收者邮件地址"};
        String[] carbonCopy = {"抄送者邮件地址"};
        String subject = "This is an attachment file email";
        String content = "<h2>This is an attachment file email</h2>";
        boolean isHtml = true;

        File file = new File(FILE_PATH);
        String fileName = FILE_PATH.substring(FILE_PATH.lastIndexOf(File.separator));
        try {
            mailSendService.sendAttachmentFileEmail(deliver, receiver, carbonCopy, subject, content, isHtml, fileName, file);
        } catch (Exception e) {
            assertFalse("Send attachment file email failed", true);
            e.printStackTrace();
        }
    }

    @Test
    public void sendTemplateEmail() {
        String deliver = "你的邮件地址";
        String[] receiver = {"接收者邮件地址"};
        String[] carbonCopy = {"抄送者邮件地址"};
        String template = "mail/InternalServerErrorTemplate";
        String subject = "This is a template email";
        Context context = new Context();
        String errorMessage;

        try {
            throw new NullPointerException();
        } catch (NullPointerException e) {
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            e.printStackTrace(printWriter);
            errorMessage = writer.toString();
        }
        context.setVariable("username", "HelloWood");
        context.setVariable("methodName", "cn.com.hellowood.mail.MailUtilTests.sendTemplateEmail()");
        context.setVariable("errorMessage", errorMessage);
        context.setVariable("occurredTime", new Date());
        try {
            mailSendService.sendTemplateEmail(deliver, receiver, carbonCopy, subject, template, context);
        } catch (Exception e) {
            assertFalse("Send template email failed", true);
            e.printStackTrace();
        }
    }

}

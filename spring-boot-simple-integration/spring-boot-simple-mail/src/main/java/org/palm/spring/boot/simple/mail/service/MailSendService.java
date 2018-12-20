package org.palm.spring.boot.simple.mail.service;

import java.io.File;
import org.thymeleaf.context.Context;

/**
 * 邮件发送服务(可优化为工具类)
 *
 * @author
 * @date 2018/12/19 15:02
 */
public interface MailSendService {

    /**
     * 发送简单的邮件通知
     * @param deliver 发送者
     * @param receiver 接收者
     * @param carbonCopy 抄送
     * @param subject 标题
     * @param content 邮件内容
     */
    void sendSimpleEmail(String deliver, String[] receiver, String[] carbonCopy, String subject, String content) throws Exception;

    /**
     * 发送Html内容的邮件通知
     * @param deliver 发送者
     * @param receiver 接收者
     * @param carbonCopy 抄送
     * @param subject 标题
     * @param content 邮件内容
     * @param isHtml 是否为Html
     */
    void sendHtmlEmail(String deliver, String[] receiver, String[] carbonCopy, String subject, String content, boolean isHtml) throws Exception;

    /**
     * 发送带附件的邮件通知(方法还可优化)
     * @param deliver 发送者
     * @param receiver 接收者
     * @param carbonCopy 抄送
     * @param subject 标题
     * @param content 邮件内容
     * @param isHtml 是否为Html
     * @param fileName 文件名
     * @param file 文件
     */
    void sendAttachmentFileEmail(String deliver, String[] receiver, String[] carbonCopy, String subject, String content, boolean isHtml, String fileName, File file) throws Exception;

    /**
     * 使用Thymeleaf作为模板发送邮件
     * @param deliver
     * @param receiver
     * @param carbonCopy
     * @param subject
     * @param template
     * @param context
     */
    void sendTemplateEmail(String deliver, String[] receiver, String[] carbonCopy, String subject, String template, Context context) throws Exception;

}

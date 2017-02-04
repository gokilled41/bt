package com.hqjl.crm.utils;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EmailUtil {

    private static final Log logger_ = LogFactory.getLog(EmailUtil.class);

    private static final String CRM_ACCOUNT_NAME = "文件管理系统";
    private static final String MAIL_SMTP_PORT = "25"; // 注意：gmail的smtp服务器使用的65端口
    private static final String MAIL_SMTP_AUTH = "true";
    private static final String MAIL_SMTP_SOCKETFACTORY_CLASS = "javax.net.ssl.SSLSocketFactory";
    private static final String MAIL_SMTP_SOCKETFACTORY_FALLBACK = "false";
    private static final String MAIL_SMTP_SOCKETFACTORY_PORT = "465";
    private static final String MAIL_SMTP_SSL = "true";

    // 系统邮箱服务�?
    private static String SMTP;
    // 系统邮箱用户�?
    private static String ACCOUNT;
    // 系统邮箱密码
    private static String PASSWORD;
    // 审批邮件模板
    private static String REVIEWER_EMAIL_TEMPLATE;

    static {
        SMTP = ConfigUtil.get("emailSMTP");
        ACCOUNT = ConfigUtil.get("emailSystemAccount");
        PASSWORD = ConfigUtil.get("emailSystemPassword");
    }

    public static boolean sendEmail(String subject, String body, String mailTo, Map<String, String> headers)
            throws Exception {
        String fromEmail = ACCOUNT;
        String fromPersonName = CRM_ACCOUNT_NAME;
        return sendEmail(subject, body, fromEmail, fromPersonName, mailTo, headers);
    }

    /**
     * 此段代码用来发�?�电子邮�?
     */
    private synchronized static boolean sendEmail(String subject, String body, String fromEmail, String fromPersonName,
            String mailTo, Map<String, String> headers) throws Exception {
        try {
            Properties props = new Properties(); // 获取系统环境
            Authenticator auth = new Email_Autherticator(); // 进行邮件服务器用户认�?

            props.setProperty("mail.smtp.host", SMTP);
            props.setProperty("mail.smtp.socketFactory.class", MAIL_SMTP_SOCKETFACTORY_CLASS);
            props.setProperty("mail.smtp.socketFactory.fallback", MAIL_SMTP_SOCKETFACTORY_FALLBACK);
            props.setProperty("mail.smtp.port", MAIL_SMTP_PORT);
            props.setProperty("mail.smtp.socketFactory.port", MAIL_SMTP_SOCKETFACTORY_PORT);
            props.setProperty("mail.smtp.auth", MAIL_SMTP_AUTH);
            props.setProperty("mail.smtp.ssl", MAIL_SMTP_SSL);

            Session session = Session.getDefaultInstance(props, auth);
            MimeMessage message = new MimeMessage(session);
            message.setSubject(subject); // 设置邮件主题
            message.setContent(body, "text/html;charset=UTF-8");
            // message.setText(body); // 设置邮件正文
            setHeaders(message, headers);
            message.setSentDate(new Date()); // 设置邮件发�?�日�?
            Address address = new InternetAddress(fromEmail, fromPersonName);
            message.setFrom(address); // 设置邮件发�?��?�的地址
            Address toAddress = new InternetAddress(mailTo); // 设置邮件接收方的地址
            message.addRecipient(Message.RecipientType.TO, toAddress);
            Transport.send(message); // 发�?�邮�?
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }
    }

    private static void setHeaders(MimeMessage m, Map<String, String> headers) throws MessagingException {
        if (headers != null && !headers.isEmpty()) {
            for (Entry<String, String> e : headers.entrySet()) {
                m.setHeader(e.getKey(), e.getValue());
            }
        }
    }

    public synchronized static String getReviewerEmailTemplate() {
        if (REVIEWER_EMAIL_TEMPLATE == null) {
            try {
                InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("email.html");
                List<String> ls = Util.getLines(is);
                StringBuilder sb = new StringBuilder();
                for (String l : ls) {
                    sb.append(l).append("\n");
                }
                String template = sb.toString();
                Properties p = new Properties();
                p.setProperty("title", "审批业务详情");
                p.setProperty("review-applyId-name", "申请�?");
                p.setProperty("review-employeeNo-name", "申请人工�?");
                p.setProperty("review-nickName-name", "申请人姓�?");
                p.setProperty("review-department-name", "�?在部�?");
                p.setProperty("review-businessType-name", "业务类型");
                p.setProperty("review-fileType-name", "文件类型");
                p.setProperty("review-name-name", "评审文件名称");
                p.setProperty("review-version-name", "文件版本�?");
                p.setProperty("review-formId-name", "表单�?");
                p.setProperty("review-description-name", "备注");
                p.setProperty("btn", "�? �?");
                template = Util.formatTemplate(template, p);
                REVIEWER_EMAIL_TEMPLATE = template;
            } catch (Exception e) {
                if (logger_.isErrorEnabled()) {
                    logger_.error("Cannot get reviewer email template", e);
                }
            }
        }
        return REVIEWER_EMAIL_TEMPLATE;
    }

    /**
     * 用来进行服务器对用户的认�?
     */
    public static class Email_Autherticator extends Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(ACCOUNT, PASSWORD);
        }
    }
}

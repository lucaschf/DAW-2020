package br.edu.ifsudestemg.barbacena.daw.museumschedule.service;

import br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.EmailSenderDAO;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.EmailAccount;
import br.edu.ifsudestemg.barbacena.daw.museumschedule.model.EmailSendingResult;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailSenderService {

    private final EmailAccount emailAccount;
    private final String subject;
    private final String recipient;
    private final String content;

    public EmailSenderService(EmailAccount emailAccount, String subject, String recipient, String content) {
        this.emailAccount = emailAccount;
        this.subject = subject;
        this.recipient = recipient;
        this.content = content;
    }

    @SuppressWarnings("UnusedReturnValue")
    protected EmailSendingResult sendEmail() {
        EmailSendingResult result;

        try {
            // create message
            MimeMessage mimeMessage = new MimeMessage(emailAccount.getSession());
            mimeMessage.setFrom(emailAccount.getAddress());
            mimeMessage.setRecipients(Message.RecipientType.TO, recipient);
            mimeMessage.setSubject(subject);

            // set content
            Multipart multipart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(content, "text/html");
            multipart.addBodyPart(messageBodyPart);
            mimeMessage.setContent(multipart);

            // send message
            Transport transport = emailAccount.getSession().getTransport();
            transport.connect(
                    emailAccount.getOutgoingHost(),
                    emailAccount.getAddress(),
                    emailAccount.getPassword()
            );

            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            transport.close();
            result = EmailSendingResult.SUCCESS;
        } catch (MessagingException e) {
            e.printStackTrace();
            result = EmailSendingResult.FAILED_BY_PROVIDER;
        } catch (Exception e) {
            e.printStackTrace();
            result = EmailSendingResult.FAILED_BY_UNEXPECTED_ERROR;
        }

        return result;
    }

    public static void sendEmailMessage(String subject, String recipient, String content) {
        new Thread(() -> {
            try {
                var sender = new EmailSenderDAO().fetchAll().get(0);
                new EmailSenderService(sender, subject, recipient, content).sendEmail();
            } catch (Exception ignored) {
            }
        }).start();
    }
}

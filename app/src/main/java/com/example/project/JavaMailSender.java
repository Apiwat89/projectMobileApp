package com.example.project;

import android.os.AsyncTask;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailSender {

    private final String senderEmail = "s6506021620211@email.kmutnb.ac.th";
    private final String senderPassword = "fyxw fimu ywwb xmev";

    public void sendVerificationCode(String recipientEmail, String otpCode) {
        new SendEmailTask().execute(recipientEmail, otpCode);
    }

    private class SendEmailTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            String recipientEmail = params[0];
            String otpCode = params[1];

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, senderPassword);
                }
            });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(senderEmail));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
                message.setSubject("รหัสยืนยันการเปลี่ยนรหัสผ่าน");
                message.setText("รหัส OTP ของคุณคือ: " + otpCode);

                Transport.send(message);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}

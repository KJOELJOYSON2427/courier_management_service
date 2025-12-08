package com.example.Scheduling.service;

import com.example.Scheduling.CronJob.GmailProperties;
import com.example.Scheduling.accessToken.GmailAccessToken;
import com.example.Scheduling.refresh_token.GmailRefreshTokenForSheduler;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import com.google.api.client.json.gson.GsonFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Map;
import java.util.Properties;

@Service
public class GmailService {

    private final TemplateEngine templateEngine;

    private final GmailRefreshTokenForSheduler tokenService;
    public GmailService(GmailRefreshTokenForSheduler tokenService, TemplateEngine templateEngine) {


        this.templateEngine = templateEngine;
        this.tokenService = tokenService;
    }

    public void sendDeliveredEmail(
            String toEmail,
            String senderName,
            String from,
            String to,
            String recipientName,
            double cost,
            String weight,
            String note
    ) throws Exception {

        // 1️⃣ Get valid token (refreshes if expired)
        String accessToken = tokenService.getValidAccessToken();
        System.out.println("Using Access Token = " + accessToken);

        // 2️⃣ Transport
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        HttpRequestInitializer initializer =
                request -> request.getHeaders().setAuthorization("Bearer " + accessToken);

        // 3️⃣ Gmail Client
        Gmail gmail = new Gmail.Builder(
                httpTransport,
                GsonFactory.getDefaultInstance(),
                initializer
        ).setApplicationName("SendIt Courier").build();

        // 4️⃣ Template
        Context ctx = new Context();
        ctx.setVariable("sendername", senderName);
        ctx.setVariable("from", from);
        ctx.setVariable("to", to);
        ctx.setVariable("recipientname", recipientName);
        ctx.setVariable("cost", cost);
        ctx.setVariable("weight", weight);
        ctx.setVariable("note", note);

        String html = templateEngine.process("deliveredParcelEmail", ctx);

        // 5️⃣ Email
        MimeMessage email = createEmail(toEmail, "me", "Your Parcel Has Been Delivered", html);

        // 6️⃣ Convert & Send
        Message message = createMessageWithEmail(email);
        gmail.users().messages().send("me", message).execute();

        System.out.println("✔ EMAIL SENT SUCCESSFULLY!");
    }



    private MimeMessage createEmail(String to, String from, String subject, String bodyHtml) throws Exception {


        Session session = Session.getDefaultInstance(new Properties(), null);

        String cleanTo = to == null ? "" : to.trim();
        String cleanFrom = from == null ? "" : from.trim();

        System.out.println("Clean To after trim: [" + cleanTo + "]");

        // Validate email
        if (!cleanTo.contains("@")) {
            throw new IllegalArgumentException("❌ Invalid 'To' email: " + cleanTo);
        }
        if (cleanFrom.equals("me")) {
            cleanFrom = "yhhj8036@gmail.com";  // YOUR Gmail API account
        }

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(cleanFrom));
        message.addRecipient(jakarta.mail.Message.RecipientType.TO, new InternetAddress(cleanTo));
        message.setSubject(subject, "UTF-8");
        message.setContent(bodyHtml, "text/html; charset=UTF-8");

        System.out.println("Message is created " + message);

        return message;
    }


    private Message createMessageWithEmail(MimeMessage email) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        email.writeTo(baos);

        String encodedEmail = Base64.getUrlEncoder().encodeToString(baos.toByteArray());

        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }

    public void sendWelcomeEmail(String toEmail,
                                 String subject,
                                 String templateName,
                                 Map<String, Object> model){
         try {
             // 1️⃣ LOAD VALID ACCESS TOKEN
             String accessToken = tokenService.getValidAccessToken();

             // 2️⃣ RENDER THYMELEAF TEMPLATE
             Context ctx = new Context();


             model.forEach(ctx::setVariable);

             String html = templateEngine.process(templateName, ctx);

             // 3️⃣ Build MimeMessage
             MimeMessage email = createEmail(
                     toEmail,
                     "me",  // use me so Gmail API uses authenticated account
                     subject,
                     html
             );

             // 4️⃣ Convert to Gmail Message
             Message gmailMessage = createMessageWithEmail(email);


             // 5️⃣ Build Gmail client using refreshed token
             Gmail gmailService = new Gmail.Builder(
                     new NetHttpTransport(),
                     new GsonFactory(),
                     request -> {
                         request.getHeaders().setAuthorization("Bearer " + accessToken);
                     }
             ).setApplicationName("SendIt Courier").build();
             // 6️⃣ SEND EMAIL 🎉
             gmailService
                     .users()
                     .messages()
                     .send("me", gmailMessage)
                     .execute();

             System.out.println("✅ Welcome email sent to: " + toEmail);

         } catch (Exception e) {
             e.printStackTrace();
             throw new RuntimeException("❌ Failed to send Welcome Email");
         }


}
}

package com.example.Scheduling.service;

import com.example.Scheduling.CronJob.GmailProperties;
import com.example.Scheduling.accessToken.GmailAccessToken;
import com.example.Scheduling.parcelModels.Parcel;
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
            Parcel parcel
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
        ctx.setVariable("sendername", parcel.getSenderName());
        ctx.setVariable("from", parcel.getSenderAddress());
        ctx.setVariable("to", parcel.getRecieverAddress());
        ctx.setVariable("recipientname", parcel.getRecieverName());
        ctx.setVariable("cost", parcel.getCost());
        ctx.setVariable("weight", parcel.getWeight());

        String html = templateEngine.process("deliveredParcelEmail", ctx);

        // 5️⃣ Email
        MimeMessage SenderEmailMessage = createEmail(
                parcel.getSenderEmail(),
                "me",
                "Your Parcel Has Been Delivered",
                html
        );
        // 5️⃣ Email
        MimeMessage RecieverEmailMessage = createEmail(
                parcel.getReceiverEmail(),
                "me",
                "Your parcel was successfully delivered!",
                html
        );
        System.out.println("Delivery confirmation sent for parcel: " + parcel.getId());

        // 6️⃣ Convert & Send
        Message SenderMessage = createMessageWithEmail(SenderEmailMessage);
        gmail.users().messages().send("me", SenderMessage).execute();
        // 6️⃣ Convert & Send
        Message RecieverMessage = createMessageWithEmail(RecieverEmailMessage);
        gmail.users().messages().send("me", RecieverMessage).execute();
        System.out.println("✔ EMAIL SENT SUCCESSFULLY!");
    }


    // STATUS 0 → Just created → Only notify SENDER (Order Confirmation)
    public void sendParcelCreatedEmail(Parcel parcel) throws Exception {
        String html = renderTemplate("emails/parcel-created-sender", parcel);

        sendEmail(
                parcel.getSenderEmail(),
                "Your parcel request has been received! ✔",
                html
        );
        System.out.println("Parcel created email sent to sender: " + parcel.getSenderEmail());
    }

    // STATUS 1 → Picked up by courier → Notify RECIPIENT for the first time
    public void sendParcelPickedUpEmail(Parcel parcel) throws Exception {
        String html = renderTemplate("emails/parcel-on-the-way", parcel);

        sendEmail(
                parcel.getReceiverEmail(),
                "Good news! A parcel is on its way to you! 🚚",
                html
        );
        System.out.println("On-the-way email sent to recipient: " + parcel.getReceiverEmail());
    }

    // STATUS 2 → Out for delivery today
    public void sendOutForDeliveryEmail(Parcel parcel) throws Exception {
        String html = renderTemplate("emails/parcel-out-for-delivery", parcel);

        sendEmail(
                parcel.getReceiverEmail(),
                "Your parcel is out for delivery today! 📦",
                html
        );
    }

    // Reusable template renderer
    private String renderTemplate(String templateName, Parcel parcel) {
        Context ctx = new Context();
        ctx.setVariable("senderName", parcel.getSenderName());
        ctx.setVariable("recipientName", parcel.getRecieverName());
        ctx.setVariable("from", parcel.getSenderAddress());
        ctx.setVariable("to", parcel.getRecieverAddress());
        ctx.setVariable("cost", parcel.getCost());
        ctx.setVariable("weight", parcel.getWeight());
        ctx.setVariable("trackingId", parcel.getTrackingNumber());
        return templateEngine.process(templateName, ctx);
    }

    // STATUS 3 → Delivered → Final confirmation
    public void sendParcelDeliveredEmail(Parcel parcel) throws Exception {
        String html = renderTemplate("emails/parcel-delivered", parcel);

        // Notify both sender and recipient
        sendEmail(parcel.getReceiverEmail(), "Your parcel has been delivered! ✔", html);
        sendEmail(parcel.getSenderEmail(),    "Your parcel was successfully delivered!", html);

        System.out.println("Delivery confirmation sent for parcel: " + parcel.getId());
    }
    // Reusable Gmail sender (your original logic, cleaned up)
    private void sendEmail(String toEmail, String subject, String htmlBody) throws Exception {
        String accessToken = tokenService.getValidAccessToken();

        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        HttpRequestInitializer initializer = request ->
                request.getHeaders().setAuthorization("Bearer " + accessToken);

        Gmail gmail = new Gmail.Builder(httpTransport, GsonFactory.getDefaultInstance(), initializer)
                .setApplicationName("SendIt Courier")
                .build();

        MimeMessage email = createEmail(toEmail, "me", subject, htmlBody);
        Message message = createMessageWithEmail(email);

        gmail.users().messages().send("me", message).execute();
        System.out.println("Email sent to: " + toEmail + " | Subject: " + subject);
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

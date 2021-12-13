package com.propositive.tradewaale.SendMail;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {

    String mail = "tradewaale@gmail.com";
    String pass = "Proluck@2797";

    String response;

    Context context;

    public String Mail(String to, String subject, String message){

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.post", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mail, pass);
            }
        });



        try{
            Message message1 = new MimeMessage(session);
            message1.setFrom(new InternetAddress(mail));
            message1.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message1.setSubject(subject);
            message1.setText(message);

            new SendMail().execute(message1);

        }catch (MessagingException e){
            e.printStackTrace();
        }


        return response;
    }

    private class SendMail extends AsyncTask<Message, String, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show( context, "Please Wait", "Sending Mail...", true, false);
        }

        @Override
        protected String doInBackground(Message... messages) {

            try {
                Transport.send(messages[0]);
                return "Success";
            } catch (MessagingException e) {
                e.printStackTrace();
                return "Error";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();

            if (s.equals("Success")){
                Toast.makeText(context, "mail send!...", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "mail send failed!...", Toast.LENGTH_SHORT).show();
            }

        }



    }

}

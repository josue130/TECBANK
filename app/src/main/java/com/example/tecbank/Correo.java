package com.example.tecbank;


import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Correo {

    //List<Integer> Codigos;
    //Codigos = new ArrayList<>();
    //Codigos.add(171501);
    //Codigos.add(181312);
    //Codigos.add(190511);
    //Codigos.add(170903);

    private String correoDeOrigen;
    private String correoDeDestino;
    private String asunto;
    private String mensajeDeTexto;
    private String contraseñaCorreo;

    public Correo(String origen,String destino,String asunto, String txt,String contraseñaCorreo){
        this.correoDeOrigen = origen;
        this.correoDeDestino = destino;
        this.asunto = asunto;
        this.mensajeDeTexto = txt;
        this.contraseñaCorreo = contraseñaCorreo;

    }
    public void envioDeCorreos(){
        envioDeMensajes();
    }
    private void envioDeMensajes(){
        try{
            Properties p = new Properties();
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.setProperty("mail.smtp.starttls.enable", "true");
            p.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            p.setProperty("mail.smtp.port", "587");
            p.setProperty("mail.smtp.user",correoDeOrigen);
            p.setProperty("mail.smtp.auth", "true");
            Session s = Session.getDefaultInstance(p);
            MimeMessage mensaje = new MimeMessage(s);
            mensaje.setFrom(new InternetAddress(correoDeOrigen));
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(correoDeDestino));
            mensaje.setSubject(asunto);
            mensaje.setText(mensajeDeTexto);

            Transport t = s.getTransport("smtp");
            t.connect(correoDeOrigen,contraseñaCorreo);
            t.sendMessage(mensaje, mensaje.getAllRecipients());
            t.close();

        } catch (MessagingException e) {

        }
    }
}

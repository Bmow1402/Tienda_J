package com.tienda.service.impl;

import com.tienda.service.CorreoService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class CorreoServiceImpl implements CorreoService { //Implementamos la interfaz de correoservice

    @Autowired
    private JavaMailSender mailSender; //esta info sale del springframework para poder hacer el envio de los correos
    
    @Override
      public void enviarCorreoHtml(
              String para, 
              String asunto, 
              String contenidoHtml) 
              throws MessagingException {
          
        MimeMessage message = mailSender.createMimeMessage(); //es parte del codigo de la libreria
        MimeMessageHelper helper 
                = new MimeMessageHelper(message, 
                        true); //crea un nuevo msj(correo)
        helper.setTo(para);
        helper.setSubject(asunto);
        helper.setText(contenidoHtml,true); //si no le queremos poner el contenido, lo  cambiamos a false
        mailSender.send(message); //variable message el que se crea al final el correo 
    }
}
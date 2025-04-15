package com.tienda.service;

import jakarta.mail.MessagingException;

public interface CorreoService {
    public void enviarCorreoHtml(
            String para, //destinatario
            String asunto, //subject - titulo del email
            String contenidoHtml) //contenido del mensaje en HTML
            throws MessagingException; //envia un error en caso de fallar
}
package com.tienda.service;

import com.tienda.domain.Usuario;
import jakarta.mail.MessagingException;
import org.springframework.ui.Model; //recibir acciones o información que el usuario hace por medio de la interfaz de la aplicación  
import org.springframework.web.multipart.MultipartFile;

public interface RegistroService { //recuerda la info del user

    public Model activar(Model model, String usuario, String clave); //nos funciona con el correo

    public Model crearUsuario(Model model, Usuario usuario) throws MessagingException;
    
    public void activar(Usuario usuario, MultipartFile imagenFile);
    
    public Model recordarUsuario(Model model, Usuario usuario) throws MessagingException;
}
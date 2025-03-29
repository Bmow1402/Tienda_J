package com.tienda.dao;
import com.tienda.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoDao extends JpaRepository <Producto, Long>  {
    
}

//aqui generalmente se hacen las partes de los filtros
//tipo de clase de java que permite que tengamos metodos abstractos, o sea, definimos los metodos y mas adelante le dire en algun punto de mi codigo
//es como para definir el metodo, parametros a recibir y que voy a retornar algo, es para darle una mejor estructura a nuestro codigo
//es como un back up cuando algo falla, solo se tienen los metodos 
//por medio de JPA no tengo que crear un codigo tan grande, solo usamos palabras reservadas [delete, say] y hace la logica por detras, a eso se le llama metodo abstracto 
//es como para que los metodos sean globales y que si se me ocurre crear una clase de java que yo igual pueda usar estos metodos en otras clases, y para reutilizar

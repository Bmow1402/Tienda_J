//el controlador, controlar lo que estoy solicitando en la pagina, si el usuario quiere buscar un producto o elimianar, el controlador busca cuales seran las solicitudes del usuario
//ROUTING BASICAMENTE

//lo que esta en verde siempre hace referencia a las variables del HTML
//de aca se saca lo que monstamos en las vistas, osea aqui se crea el "modifica" y esto mismo se crea en el folder de templates.producto
package com.tienda.controller;

import com.tienda.domain.Producto;
import com.tienda.service.CategoriaService;
import com.tienda.service.ProductoService;
import com.tienda.service.impl.FirebaseStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller //aqui se especifica que esto es un controlador

@RequestMapping("/producto") // se le pide al brwoser la pagina a mostrar
public class ProductoController {
  
    @Autowired
    private ProductoService productoService;
    @Autowired
    private CategoriaService categoriaService;
    
    @GetMapping("/listado") // html (variables) == verde
    private String listado(Model model) { 
        var productos = productoService.getProductos(false); //trae el listado de los productos
        model.addAttribute("productos", productos); //(5 --> html va a mostrar 5 productos
        
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("categorias", categorias);
        
        model.addAttribute("totalProductos",productos.size());
        return "/producto/listado";
    }
    
     @GetMapping("/nuevo") 
    public String productoNuevo(Producto producto) { //agregar un producto nuevo
        return "/producto/modifica";
    }

    @Autowired
    private FirebaseStorageServiceImpl firebaseStorageService;
    
    @PostMapping("/guardar") //guardar la imagen de  un producto
    public String productoGuardar(Producto producto,
            @RequestParam("imagenFile") MultipartFile imagenFile) {        
        if (!imagenFile.isEmpty()) {
            productoService.save(producto);
            producto.setRutaImagen(
                    firebaseStorageService.cargaImagen(
                            imagenFile, 
                            "producto", 
                            producto.getIdProducto()));
        }
        productoService.save(producto);
        return "redirect:/producto/listado";
    }

    @GetMapping("/eliminar/{idProducto}")
    public String productoEliminar(Producto producto) { //metodo llamado productoEliminar que recibe el producto y el ID
        productoService.delete(producto);
        return "redirect:/producto/listado";
    }

    @GetMapping("/modificar/{idProducto}")
    public String productoModificar(Producto producto, Model model) {
        producto = productoService.getProducto(producto);
        model.addAttribute("producto", producto);
        
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("categorias", categorias);
        
        return "/producto/modifica"; // estan enlazados a las vistas que estamos creando
    }   
}

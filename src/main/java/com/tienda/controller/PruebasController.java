package com.tienda.controller;
import com.tienda.domain.Categoria;
import com.tienda.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.tienda.service.ProductoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pruebas") // localhost:pruebas
public class PruebasController {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/listado")
    public String listado(Model model) { //metodo
        var productos = productoService.getProductos(false); //obtiene todos los productos
        var categorias = categoriaService.getCategorias(false);//obtiene todas las categorias
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());
        model.addAttribute("categorias", categorias);
        return "/pruebas/listado"; //return de la lista de listado
    }

    @GetMapping("/listado/{idCategoria}") //localhost/listado/2
    public String listado(Model model, Categoria categoria) {
        var productos = categoriaService.getCategoria(categoria).getProductos(); //busca todos los productos disponibles para las categorias que estoy buscando
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());
        model.addAttribute("categorias", categorias);
        return "/pruebas/listado";
    }
}
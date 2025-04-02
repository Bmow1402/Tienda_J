package com.tienda.controller; // Define el paquete donde se encuentra este controlador.

import com.tienda.domain.Categoria; // Importa la clase Categoria, que representa la entidad de categorías.
import com.tienda.service.CategoriaService; // Importa el servicio que maneja la lógica de negocio de las categorías.
import com.tienda.service.impl.FirebaseStorageServiceImpl; // Importa el servicio para manejar el almacenamiento de imágenes en Firebase.
import org.springframework.beans.factory.annotation.Autowired; // Permite la inyección de dependencias.
import org.springframework.stereotype.Controller; // Indica que esta clase es un controlador en Spring MVC.
import org.springframework.ui.Model; // Permite pasar datos desde el controlador a la vista.
import org.springframework.web.bind.annotation.GetMapping; // Asocia métodos con solicitudes HTTP GET.
import org.springframework.web.bind.annotation.PostMapping; // Asocia métodos con solicitudes HTTP POST.
import org.springframework.web.bind.annotation.RequestMapping; // Define la URL base para este controlador.
import org.springframework.web.bind.annotation.RequestParam; // Permite acceder a los parámetros de la solicitud HTTP.
import org.springframework.web.multipart.MultipartFile; // Representa un archivo cargado por el usuario (por ejemplo, imágenes).

@Controller // Declara esta clase como un controlador en Spring MVC.
@RequestMapping("/categoria") // Define la URL base para las rutas manejadas por este controlador (por ejemplo, localhost/categoria).
public class CategoriaController {

    @Autowired // Inyección de dependencias para usar el servicio CategoriaService.
    private CategoriaService categoriaService;

    @GetMapping("/listado") // Maneja solicitudes GET en la URL /categoria/listado.
    private String listado(Model model) { 
        // Obtiene una lista de categorías desde el servicio.
        var categorias = categoriaService.getCategorias(false); // false indica que queremos categorías activas.
        
        // Añade la lista de categorías al modelo para que se pueda mostrar en la vista.
        model.addAttribute("categorias", categorias);
        
        // Añade el número total de categorías al modelo.
        model.addAttribute("totalCategorias", categorias.size());
        
        // Devuelve el nombre de la vista que se debe mostrar (listado.html en la carpeta categoria).
        return "/categoria/listado";
    }

    @GetMapping("/nuevo") // Maneja solicitudes GET en la URL /categoria/nuevo.
    public String categoriaNuevo(Categoria categoria) {
        // Devuelve la vista para agregar o modificar una categoría (modifica.html en la carpeta categoria).
        return "/categoria/modifica";
    }

    @Autowired // Inyección de dependencias para usar el servicio FirebaseStorageServiceImpl.
    private FirebaseStorageServiceImpl firebaseStorageService;

    @PostMapping("/guardar") // Maneja solicitudes POST en la URL /categoria/guardar.
    public String categoriaGuardar(Categoria categoria, 
            @RequestParam("imagenFile") MultipartFile imagenFile) { 
        // Verifica si se cargó un archivo de imagen.
        if (!imagenFile.isEmpty()) {
            // Guarda inicialmente la categoría en la base de datos.
            categoriaService.save(categoria);
            
            // Carga la imagen en Firebase y asigna su ruta a la categoría.
            categoria.setRutaImagen(
                    firebaseStorageService.cargaImagen(
                            imagenFile, 
                            "categoria", 
                            categoria.getIdCategoria()));
        }
        
        // Guarda nuevamente la categoría con la ruta de la imagen actualizada.
        categoriaService.save(categoria);
        
        // Redirige al listado de categorías después de guardar.
        return "redirect:/categoria/listado";
    }

    @GetMapping("/eliminar/{idCategoria}") // Maneja solicitudes GET en la URL /categoria/eliminar/{idCategoria}.
    public String categoriaEliminar(Categoria categoria) { 
        // Elimina la categoría especificada por el id.
        categoriaService.delete(categoria);
        
        // Redirige al listado de categorías después de eliminar.
        return "redirect:/categoria/listado";
    }

    @GetMapping("/modificar/{idCategoria}") // Maneja solicitudes GET en la URL /categoria/modificar/{idCategoria}.
    public String categoriaModificar(Categoria categoria, Model model) { 
        // Obtiene la categoría desde la base de datos utilizando su id.
        categoria = categoriaService.getCategoria(categoria);
        
        // Añade la categoría al modelo para que se muestre en la vista.
        model.addAttribute("categoria", categoria);
        
        // Devuelve la vista para modificar una categoría existente (modifica.html).
        return "/categoria/modifica";
    }
}
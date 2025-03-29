package com.tienda.domain;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name="producto") //nombre de la tabla en nuestra BD
public class Producto implements Serializable { 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_producto") 
    private Long idProducto; 
    //private Long idCategoria;  ya no se usa por el @manyToOne
    private String descripcion;
    private String detalle;
    private double precio;
    private int existencias;
    private String rutaImagen;
    private boolean activo;

    @ManyToOne // si no quiero que producto se asocie con categoria nada mas comento esta linea
    @JoinColumn(name="id_categoria") 
    Categoria categoria; 

    public Producto() {
    }

    public Producto(String descripcion, boolean activo) {
        this.descripcion = descripcion;
        this.activo = activo;
    }
}

//tenemos que ponerle el sintax del manytoone en la clase de categoria y producto

package com.tienda.service.impl;
import com.tienda.dao.ProductoDao;
import java.util.List;
import com.tienda.domain.Producto;
import com.tienda.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//en las interfaces no se usa esto: @service o asi 

@Service
public class ProductoServiceImpl implements ProductoService {
@Autowired
    private ProductoDao productoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> getProductos(boolean activos) {
        var lista = productoDao.findAll(); //busca un producto en especifico por su ID
        if (activos) {
            lista.removeIf(e -> !e.isActivo());
        }
        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public Producto getProducto(Producto producto) {
        return productoDao.findById(producto.getIdProducto()).orElse(null);
    } //busca un producto por id y si no lo encuentra lo pone null

    @Override
    @Transactional
    public void save(Producto producto) {
        productoDao.save(producto);
    } //guarda el id del producto en la lista del producto

    @Override
    @Transactional
    public void delete(Producto producto) {
        productoDao.delete(producto);
    }

    
}

package com.bebidas.tiendaBebidas.services;

import com.bebidas.tiendaBebidas.dto.CrearProductoDTO;
import com.bebidas.tiendaBebidas.dto.ProductoDTO;
import com.bebidas.tiendaBebidas.models.Categoria;
import com.bebidas.tiendaBebidas.models.Producto;
import com.bebidas.tiendaBebidas.repository.CategoriaRepository;
import com.bebidas.tiendaBebidas.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {
    @Autowired
    private final ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
    }


    public ProductoDTO crearProducto(CrearProductoDTO crearProductoDTO) {

        Categoria categoria = categoriaRepository.findById(crearProductoDTO.categoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Producto producto = new Producto();
        producto.setNombre(crearProductoDTO.nombre());
        producto.setPrecio(crearProductoDTO.precio());
        producto.setDescripcion(crearProductoDTO.descripcion());
        producto.setCategoria(categoria);
        Producto productoGuardado = productoRepository.save(producto);
        return new ProductoDTO(
                productoGuardado.getId(),
                productoGuardado.getNombre(),
                productoGuardado.getDescripcion(),
                productoGuardado.getPrecio(),
                productoGuardado.getCategoria().getNombre()
        );
    }

    public ProductoDTO actualizarProducto(int id, CrearProductoDTO crearProductoDTO) {
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Categoria categoria = categoriaRepository.findById(crearProductoDTO.categoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        productoExistente.setNombre(crearProductoDTO.nombre());
        productoExistente.setPrecio(crearProductoDTO.precio());
        productoExistente.setStock(crearProductoDTO.stock());
        productoExistente.setDescripcion(crearProductoDTO.descripcion());
        productoExistente.setCategoria(categoria);

        // Guardar el producto actualizado
        Producto productoActualizado = productoRepository.save(productoExistente);

        // Convertir a ProductoDTO antes de devolver
        return new ProductoDTO(
                productoActualizado.getId(),
                productoActualizado.getNombre(),
                productoActualizado.getDescripcion(),
                productoActualizado.getPrecio(),
                productoActualizado.getCategoria().getNombre()
        );
    }

    public List<ProductoDTO> listarProductos() {
        return productoRepository.findAll().stream()
                .map(producto -> new ProductoDTO(
                        producto.getId(),
                        producto.getNombre(),
                        producto.getDescripcion(),
                        producto.getPrecio(),
                        producto.getCategoria().getNombre()
                ))
                .toList();
    }

    public ProductoDTO buscarProductoPorId(int id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return new ProductoDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getCategoria().getNombre()
        );
    }

    public void eliminarProducto(int id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("El producto no existe");
        }
        productoRepository.deleteById(id);
    }

}

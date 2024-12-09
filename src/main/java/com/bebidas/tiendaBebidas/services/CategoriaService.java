package com.bebidas.tiendaBebidas.services;

import com.bebidas.tiendaBebidas.dto.CategoriaDTO;
import com.bebidas.tiendaBebidas.models.Categoria;
import com.bebidas.tiendaBebidas.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository){
        this.categoriaRepository = categoriaRepository;
    }

    public CategoriaDTO crearCategoria(CategoriaDTO categoriaDTO){
        Categoria categoria= new Categoria();
        categoria.setNombre(categoriaDTO.nombre());
        Categoria categoriaGuardada = categoriaRepository.save(categoria);
        return new CategoriaDTO(categoriaGuardada.getId(), categoriaGuardada.getNombre());
    }

    public CategoriaDTO obtenerCategoriaPorId(int id){
        Categoria categoria = categoriaRepository.findById(id).
        orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
        return new CategoriaDTO(
                categoria.getId(),
                categoria.getNombre()
        );
    }

    public List<CategoriaDTO> listarCategorias() {
        return categoriaRepository.findAll().stream()
                .map(categoria -> new CategoriaDTO(
                        categoria.getId(),
                        categoria.getNombre())) // Convierte Categoria a CategoriaDTO
                .toList(); // Recolecta la lista como List<CategoriaDTO>
    }

    public CategoriaDTO actualizarCategoria(Integer id, CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        categoria.setNombre(categoriaDTO.nombre());


        Categoria categoriaActualizada = categoriaRepository.save(categoria);
        return new CategoriaDTO(
                categoriaActualizada.getId(),
                categoriaActualizada.getNombre());
    }
    public void eliminarCategoria(Integer id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoría no encontrada");
        }
        categoriaRepository.deleteById(id);
    }
}

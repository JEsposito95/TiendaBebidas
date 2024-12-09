package com.bebidas.tiendaBebidas.services;

import com.bebidas.tiendaBebidas.dto.CrearUsuarioDTO;
import com.bebidas.tiendaBebidas.dto.UsuarioDTO;
import com.bebidas.tiendaBebidas.models.Usuario;
import com.bebidas.tiendaBebidas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioDTO crearUsuario(CrearUsuarioDTO crearUsuarioDTO){
        Usuario usuario = new Usuario();
        usuario.setNombre(crearUsuarioDTO.nombre());
        usuario.setEmail(crearUsuarioDTO.email());
        usuario.setPassword(crearUsuarioDTO.password());
        usuario.setRol(crearUsuarioDTO.rol());

        Usuario usuarioGuardado= usuarioRepository.save(usuario);

        return new UsuarioDTO(
                usuarioGuardado.getId(),
                usuarioGuardado.getNombre(),
                usuarioGuardado.getEmail(),
                usuario.getRol()
        );
    }

    public List<UsuarioDTO> listarUsuarios(){
        return usuarioRepository.findAll().stream()
                .map(usuario -> new UsuarioDTO(
                        usuario.getId(),
                        usuario.getNombre(),
                        usuario.getEmail(),
                        usuario.getRol()
                )).toList();
    }

    public UsuarioDTO obtenerUsuarioporId(int id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol()
        );
    }

    public void eliminarUsuario(int id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    public UsuarioDTO actualizarUsuario(int id, CrearUsuarioDTO crearUsuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombre(crearUsuarioDTO.nombre());
        usuario.setEmail(crearUsuarioDTO.email());
        usuario.setPassword(crearUsuarioDTO.password());
        usuario.setRol(crearUsuarioDTO.rol());

        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        return new UsuarioDTO(
                usuarioActualizado.getId(),
                usuarioActualizado.getNombre(),
                usuarioActualizado.getEmail(),
                usuarioActualizado.getRol()
        );
    }
}

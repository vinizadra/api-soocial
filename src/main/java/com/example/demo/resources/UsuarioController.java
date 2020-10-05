package com.example.demo.resources;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.dto.assember.UsuarioAssember;
import com.example.demo.entity.Usuario;
import com.example.demo.services.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

    @GetMapping("{id}")
    public ResponseEntity<Usuario> getById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(usuarioServiceImpl.findById(id));
    }

    @GetMapping("username/{username}")
    public ResponseEntity<Usuario> getByUsername(@PathVariable(value = "username") String username) {
        return ResponseEntity.ok(usuarioServiceImpl.findByUsername(username));
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getAll() {
        return ResponseEntity.ok(usuarioServiceImpl.findAll());
    }

    @PostMapping
    public ResponseEntity<Usuario> add(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = UsuarioAssember.dtoToEntityModel(usuarioDTO);
        return new ResponseEntity<>(usuarioServiceImpl.save(usuario), CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Usuario> update(@PathVariable(value = "id") Long id, @Valid @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = UsuarioAssember.dtoToEntityModel(usuarioDTO);

        if(!usuarioServiceImpl.existsById(id)) return ResponseEntity.notFound().build();
        usuario.setId(id);

        return new ResponseEntity<>(usuarioServiceImpl.update(usuario), NO_CONTENT);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable(value = "id") Long id) {
        Usuario usuario = usuarioServiceImpl.findById(id);
        usuarioServiceImpl.delete(usuario);
    }

//    @GetMapping("seguidores/{id}")
//    public ResponseEntity<List<Usuario>> getPosts(@PathVariable(value = "id") Long id) {
//        List<Usuario> seguindo = service.findSeguidoresById(id);
//
//        if(seguindo != null) {
//            return ResponseEntity.ok(seguindo);
//        }
//
//        return ResponseEntity.notFound().build();
//    }
}
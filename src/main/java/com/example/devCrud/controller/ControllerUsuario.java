package com.example.devCrud.controller;


import com.example.devCrud.entity.Usuario;
import com.example.devCrud.repository.RepositoryUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ControllerUsuario {

    @Autowired
    RepositoryUsuario repositoryUsuario;

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> obtenerUsuarios() {
        try {
            List<Usuario> listar = repositoryUsuario.findAll();
            if(listar.isEmpty()){
                return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return  new ResponseEntity<>(listar, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/usuarios")
    public  ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario) {
        try {
            return  new ResponseEntity<>(repositoryUsuario.save(usuario), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> actualizarUsuario( @PathVariable Long id, @RequestBody Usuario usuario) throws ResourceNotFoundException {
        try {
            Usuario user = repositoryUsuario.findById(id).orElseThrow(
                    ()-> new ResourceNotFoundException("Usuario con el soguiente id no fue encontrado ::"
                            + id) );

            user.setNombre(usuario.getNombre());
            user.setFechaNacimiento(usuario.getFechaNacimiento());
            user.setCorreo(usuario.getCorreo());
            user.setGenero(usuario.getGenero());
            user.setHobbies(usuario.getHobbies());

            final Usuario usuarioUpdate = repositoryUsuario.save(user);
            return ResponseEntity.ok(usuarioUpdate);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<HttpStatus> eliminarUsuario(@PathVariable Long id) {
        try {
            Optional<Usuario> usuario = repositoryUsuario.findById(id);
            if (usuario.isPresent()) {
                repositoryUsuario.delete(usuario.get());
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

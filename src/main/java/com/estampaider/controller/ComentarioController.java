package com.estampaider.controller;

import com.estampaider.model.Comentario;
import com.estampaider.repositories.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comentarios")
@CrossOrigin(origins = "*")
public class ComentarioController {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @GetMapping
    public List<Comentario> getComentarios() {
        return comentarioRepository.findAll();
    }

    @PostMapping
    public Comentario agregarComentario(@RequestBody Comentario comentario) {
        return comentarioRepository.save(comentario);
    }
}

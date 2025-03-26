package com.estampaider.service;

import com.estampaider.model.Comentario;
import com.estampaider.repositories.ComentarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    public List<Comentario> obtenerComentarios() {
        return comentarioRepository.findAll();
    }

    public Comentario guardarComentario(Comentario comentario) {
        return comentarioRepository.save(comentario);
    }
}

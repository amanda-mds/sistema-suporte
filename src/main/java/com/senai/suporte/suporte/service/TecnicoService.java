package com.senai.suporte.suporte.service;

import com.senai.suporte.suporte.model.Tecnico;
import com.senai.suporte.suporte.repository.TecnicoRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TecnicoService {

    private final TecnicoRepository tecnicoRepository;
    private final PasswordEncoder passwordEncoder;

    public TecnicoService(
            TecnicoRepository tecnicoRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.tecnicoRepository = tecnicoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Tecnico cadastrar(Tecnico tecnico) {

        String email = tecnico.getEmail().trim().toLowerCase();
        tecnico.setEmail(email);

        if (tecnicoRepository.findByEmail(email).isPresent()) {
            throw new IllegalStateException(
                    "Já existe um técnico cadastrado com esse e-mail."
            );
        }

        tecnico.setSenha(
                passwordEncoder.encode(tecnico.getSenha())
        );

        return tecnicoRepository.save(tecnico);
    }

    @Transactional(readOnly = true)
    public String nomePorEmail(String email) {
        if (email == null || email.isBlank()) {
            return "";
        }

        return tecnicoRepository
                .findByEmail(email.trim().toLowerCase())
                .map(Tecnico::getNome)
                .orElse("");
    }
}
package com.senai.suporte.suporte.service;

import java.util.List;

import com.senai.suporte.suporte.model.Tecnico;
import com.senai.suporte.suporte.repository.TecnicoRepository;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TecnicoUserDetailsService
        implements UserDetailsService {

    private final TecnicoRepository tecnicoRepository;

    public TecnicoUserDetailsService(
            TecnicoRepository tecnicoRepository
    ) {
        this.tecnicoRepository = tecnicoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {

        if (login == null || login.isBlank()) {
            throw new UsernameNotFoundException(
                    "Informe o nome ou e-mail."
            );
        }

        String loginLimpo = login.trim();

        List<Tecnico> encontrados =
                tecnicoRepository.buscarPorEmailOuNome(loginLimpo);

        if (encontrados.isEmpty()) {
            throw new UsernameNotFoundException(
                    "Técnico não encontrado: " + loginLimpo
            );
        }

        if (encontrados.size() > 1) {
            throw new UsernameNotFoundException(
                    "Existe mais de um técnico com esse nome. Entre com o e-mail."
            );
        }

        Tecnico tecnico = encontrados.get(0);

        return User.builder()
                .username(tecnico.getEmail())
                .password(tecnico.getSenha())
                .roles("TECNICO")
                .build();
    }
}
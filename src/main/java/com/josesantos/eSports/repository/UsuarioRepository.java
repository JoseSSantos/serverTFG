package com.josesantos.eSports.repository;

import com.josesantos.eSports.entity.UsuarioEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    UsuarioEntity findByLoginAndPassword(String login, String password);
    
    UsuarioEntity findByLogin(String login);
    

//    		Page<UsuarioEntity> findByLoginIgnoreCaseContaining(
//			String nombre, Pageable oPageable);

    Page<UsuarioEntity> findByLoginIgnoreCaseContaining(Long filtertype, String login, Pageable oPageable);
    
    Page<UsuarioEntity>findByEquipoId(Long equipo, Pageable oPageable);
    
    Page<UsuarioEntity>findByLoginIgnoreCaseContainingOrSummonernameIgnoreCaseContainingOrEmailIgnoreCaseContainingOrTwitterIgnoreCaseContainingOrDiscordIgnoreCaseContaining(
        String login, String summonername, String email, String twitter, String discord, Pageable oPageable);
}

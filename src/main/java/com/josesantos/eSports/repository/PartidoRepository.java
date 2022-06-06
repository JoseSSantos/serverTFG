/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.josesantos.eSports.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.josesantos.eSports.entity.PartidoEntity;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Saslt
 */
public interface PartidoRepository extends JpaRepository<PartidoEntity, Long> {
    
       public Page<PartidoEntity> findByIdIgnoreCaseContaining(Long filtertype, Long id, Pageable oPageable);
       
       public Page<PartidoEntity> findByIdIgnoreCaseContainingOrEquipo1IdIgnoreCaseContainingOrEquipo2IdIgnoreCaseContainingOrFechaIgnoreCaseContaining(
       Long id, String equipo1, String equipo2, LocalDateTime fecha, Pageable oPageable);




}

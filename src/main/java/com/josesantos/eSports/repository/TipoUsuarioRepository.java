/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.josesantos.eSports.repository;

import com.josesantos.eSports.entity.TipoUsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuarioEntity, Long> {
    
}

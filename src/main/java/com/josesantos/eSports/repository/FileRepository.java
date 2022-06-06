package com.josesantos.eSports.repository;

import com.josesantos.eSports.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FileRepository extends JpaRepository<FileEntity, Long> {
    
}

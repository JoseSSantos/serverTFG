/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.josesantos.eSports.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

/**
 *
 * @author Saslt
 */
@Service
public class PartidoService {
    
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        LocalDateTime date = LocalDateTime.now();
        
        public boolean checkDate(LocalDateTime diaServer){
            
            if(diaServer.isAfter(date)){
            return true;
            }else return false;
        }
        
        public boolean checkDoubleTeam(){
        return false;
        }
    
}

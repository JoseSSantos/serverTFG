package com.josesantos.eSports.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name="partido")
@JsonIgnoreProperties ({"hibernateLazyInitialize", "handler"})

public class PartidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "id_equipo1")
    private EquipoEntity equipo1;
    
    @ManyToOne
    @JoinColumn(name = "id_equipo2", nullable=true)
    private EquipoEntity equipo2;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime fecha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EquipoEntity getEquipo1() {
        return equipo1;
    }

    public void setEquipo1(EquipoEntity equipo1) {
        this.equipo1 = equipo1;
    }

    public EquipoEntity getEquipo2() {
        return equipo2;
    }

    public void setEquipo2(EquipoEntity equipo2) {
        this.equipo2 = equipo2;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sft.model.domain;

import com.jmoordb.core.annotation.Domain;
import lombok.Data;

/**
 *
 * @author avbravo
 */
@Data
@Domain(commentary = "Se usa para calular los totales del proyecto")
public class ProyectoEstadistica {
    private Integer totalSprint;
    private Integer totalTarjetasBacklog;
    private Integer totalTarjetasPendiente;
    private Integer totalTarjetasProgreso;
    private Integer totalTarjetasFinalizado;
    private Long idproyecto;

    public ProyectoEstadistica() {
    }

    public ProyectoEstadistica(Integer totalSprint, Integer totalTarjetasBacklog, Integer totalTarjetasPendiente, Integer totalTarjetasProgreso, Integer totalTarjetasFinalizado, Long idproyecto) {
        this.totalSprint = totalSprint;
        this.totalTarjetasBacklog = totalTarjetasBacklog;
        this.totalTarjetasPendiente = totalTarjetasPendiente;
        this.totalTarjetasProgreso = totalTarjetasProgreso;
        this.totalTarjetasFinalizado = totalTarjetasFinalizado;
        this.idproyecto = idproyecto;
    }

    
   

  
    
    
    
    
    
}

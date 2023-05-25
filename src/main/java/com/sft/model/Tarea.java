/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sft.model;

import com.jmoordb.core.annotation.Column;
import com.jmoordb.core.annotation.DocumentEmbeddable;
import java.util.Objects;

/**
 *
 * @author avbravo
 */
@DocumentEmbeddable
public class Tarea {
    @Column
    private String tarea;
    @Column
    private Boolean completado;
    @Column
    private Boolean active;
    @Column
    private Integer orden;
     
  

    public Tarea() {
    }

    public Tarea(String tarea, Boolean completado, Boolean active, Integer orden) {
        this.tarea = tarea;
        this.completado = completado;
        this.active = active;
        this.orden = orden;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public Boolean getCompletado() {
        return completado;
    }

    public void setCompletado(Boolean completado) {
        this.completado = completado;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tarea{");
        sb.append("tarea=").append(tarea);
        sb.append(", completado=").append(completado);
        sb.append(", active=").append(active);
        sb.append(", orden=").append(orden);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.tarea);
        hash = 89 * hash + Objects.hashCode(this.completado);
        hash = 89 * hash + Objects.hashCode(this.active);
        hash = 89 * hash + Objects.hashCode(this.orden);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tarea other = (Tarea) obj;
        if (!Objects.equals(this.tarea, other.tarea)) {
            return false;
        }
        if (!Objects.equals(this.completado, other.completado)) {
            return false;
        }
        if (!Objects.equals(this.active, other.active)) {
            return false;
        }
        return Objects.equals(this.orden, other.orden);
    }

    
    
   

    
    
    
    
}

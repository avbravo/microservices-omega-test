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
public class DiasLaborable {
    @Column
    private Boolean lunes;
    @Column
    private Boolean martes;
    @Column
    private Boolean miercoles;
    @Column
    private Boolean jueves;
    @Column
    private Boolean viernes;
    @Column
    private Boolean sabado;
    @Column
    private Boolean domingo;
    
    
    public DiasLaborable() {
    }

    public DiasLaborable(Boolean lunes, Boolean martes, Boolean miercoles, Boolean jueves, Boolean viernes, Boolean sabado, Boolean domingo) {
        this.lunes = lunes;
        this.martes = martes;
        this.miercoles = miercoles;
        this.jueves = jueves;
        this.viernes = viernes;
        this.sabado = sabado;
        this.domingo = domingo;
    }

    public Boolean getLunes() {
        return lunes;
    }

    public void setLunes(Boolean lunes) {
        this.lunes = lunes;
    }

    public Boolean getMartes() {
        return martes;
    }

    public void setMartes(Boolean martes) {
        this.martes = martes;
    }

    public Boolean getMiercoles() {
        return miercoles;
    }

    public void setMiercoles(Boolean miercoles) {
        this.miercoles = miercoles;
    }

    public Boolean getJueves() {
        return jueves;
    }

    public void setJueves(Boolean jueves) {
        this.jueves = jueves;
    }

    public Boolean getViernes() {
        return viernes;
    }

    public void setViernes(Boolean viernes) {
        this.viernes = viernes;
    }

    public Boolean getSabado() {
        return sabado;
    }

    public void setSabado(Boolean sabado) {
        this.sabado = sabado;
    }

    public Boolean getDomingo() {
        return domingo;
    }

    public void setDomingo(Boolean domingo) {
        this.domingo = domingo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DiasLaborable{");
        sb.append("lunes=").append(lunes);
        sb.append(", martes=").append(martes);
        sb.append(", miercoles=").append(miercoles);
        sb.append(", jueves=").append(jueves);
        sb.append(", viernes=").append(viernes);
        sb.append(", sabado=").append(sabado);
        sb.append(", domingo=").append(domingo);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.lunes);
        hash = 79 * hash + Objects.hashCode(this.martes);
        hash = 79 * hash + Objects.hashCode(this.miercoles);
        hash = 79 * hash + Objects.hashCode(this.jueves);
        hash = 79 * hash + Objects.hashCode(this.viernes);
        hash = 79 * hash + Objects.hashCode(this.sabado);
        hash = 79 * hash + Objects.hashCode(this.domingo);
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
        final DiasLaborable other = (DiasLaborable) obj;
        if (!Objects.equals(this.lunes, other.lunes)) {
            return false;
        }
        if (!Objects.equals(this.martes, other.martes)) {
            return false;
        }
        if (!Objects.equals(this.miercoles, other.miercoles)) {
            return false;
        }
        if (!Objects.equals(this.jueves, other.jueves)) {
            return false;
        }
        if (!Objects.equals(this.viernes, other.viernes)) {
            return false;
        }
        if (!Objects.equals(this.sabado, other.sabado)) {
            return false;
        }
        return Objects.equals(this.domingo, other.domingo);
    }

   
    
    
    
    
}

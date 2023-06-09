/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sft.repository;

import com.jmoordb.core.annotation.date.ExcludeTime;
import com.jmoordb.core.annotation.repository.Count;
import com.jmoordb.core.annotation.repository.Find;
import com.jmoordb.core.annotation.repository.Lookup;
import com.jmoordb.core.annotation.repository.Repository;
import com.jmoordb.core.model.Search;
import com.jmoordb.core.repository.CrudRepository;
import com.sft.model.Proyecto;
import java.util.Date;
import java.util.List;

/**
 *
 * @author avbravo
 */
@Repository(database = "{mongodb.database1}", entity = Proyecto.class)
public interface ProyectoRepository extends CrudRepository<Proyecto, Long> {

      
    @Find
    public List<Proyecto> findByFechaGreaterThanEqualAndFechaLessThanEqual(@ExcludeTime Date start, @ExcludeTime Date end);

    @Find
    public List<Proyecto> findByProyecto(String proyecto);

    @Lookup
    public List<Proyecto> lookup(Search search);

    @Count()
    public Long count(Search... search);
    
  
}

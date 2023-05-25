/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sft.repository;

import com.jmoordb.core.annotation.repository.Count;
import com.jmoordb.core.annotation.repository.Find;
import com.jmoordb.core.annotation.repository.Lookup;
import com.jmoordb.core.annotation.repository.Repository;
import com.jmoordb.core.model.Search;
import com.jmoordb.core.repository.CrudRepository;
import com.sft.model.Tipotarjeta;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author avbravo
 */
@Repository(database = "{mongodb.database1}",entity = Tipotarjeta.class)
public interface TipotarjetaRepository extends CrudRepository<Tipotarjeta, Long>{
    
      @Find
    public Optional<Tipotarjeta> findByTipotarjeta(String icono);
    
    @Lookup
public List<Tipotarjeta> lookup(Search search);
  @Count()
    public Long count(Search... search);
    
}

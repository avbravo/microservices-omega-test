/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sft.repository;

import com.jmoordb.core.annotation.repository.Count;
import com.jmoordb.core.annotation.repository.Lookup;
import com.jmoordb.core.annotation.repository.Repository;
import com.jmoordb.core.model.Search;
import com.jmoordb.core.repository.CrudRepository;
import com.sft.model.Building;
import java.util.List;

/**
 *
 * @author avbravo
 */
@Repository(entity = Building.class)
public interface BuildingRepository extends CrudRepository<Building, Long> {
       @Lookup
public List<Building> lookup(Search search);

  @Count()
    public Long count(Search... search);
}

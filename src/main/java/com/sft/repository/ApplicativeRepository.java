/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sft.repository;

import com.jmoordb.core.annotation.repository.Count;
import com.jmoordb.core.annotation.repository.Lookup;
import com.jmoordb.core.annotation.repository.Ping;
import com.jmoordb.core.annotation.repository.Repository;
import com.jmoordb.core.model.Search;
import com.jmoordb.core.repository.CrudRepository;
import com.sft.model.Applicative;
import java.util.List;

/**
 *
 * @author avbravo
 */
@Repository(entity = Applicative.class)
public interface ApplicativeRepository extends CrudRepository<Applicative, Long> {
       @Ping
    public Boolean ping();
       @Lookup
public List<Applicative> lookup(Search search);
  @Count()
    public Long count(Search... search);
}

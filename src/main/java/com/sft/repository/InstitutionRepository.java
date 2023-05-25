/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sft.repository;

import com.jmoordb.core.annotation.repository.Count;
import com.jmoordb.core.annotation.repository.Find;
import com.jmoordb.core.annotation.repository.Lookup;
import com.jmoordb.core.annotation.repository.Repository;
import com.jmoordb.core.model.Search;
import com.jmoordb.core.repository.CrudRepository;
import com.sft.model.Institution;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author avbravo
 */
@Repository(entity = Institution.class)
public interface InstitutionRepository extends CrudRepository<Institution, Long> {
       @Lookup
public List<Institution> lookup(Search search);
@Find
public Stream<Institution> findByInstitutionAndActive(String institutio, Boolean active);
  @Count()
    public Long count(Search... search);

}

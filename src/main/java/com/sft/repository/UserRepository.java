/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sft.repository;

import com.jmoordb.core.annotation.enumerations.CaseSensitive;
import com.jmoordb.core.annotation.enumerations.TypeOrder;
import com.jmoordb.core.annotation.repository.Count;
import com.jmoordb.core.annotation.repository.Find;
import com.jmoordb.core.annotation.repository.LikeBy;
import com.jmoordb.core.annotation.repository.Lookup;
import com.jmoordb.core.annotation.repository.Repository;
import com.jmoordb.core.model.Search;
import com.jmoordb.core.repository.CrudRepository;
import com.sft.model.User;
import com.sft.model.UserView;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author avbravo
 */
@Repository(entity = User.class)
public interface UserRepository extends CrudRepository<User, Long> {

    @Find
    public Optional<User> findByUsername(String username);

    @Lookup
    public List<User> lookup(Search search);

    @Count()
    public Long count(Search... search);

    @LikeBy(caseSensitive = CaseSensitive.NO, typeOrder = TypeOrder.ASC)
    public List<User> likeByName(String name);
}

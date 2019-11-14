package com.learn.springboot.dataProvider.repo;

import com.learn.springboot.dataProvider.model.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {
    AppUser findByUsername(String username);

//    @Query(value = "select c.* from customer c where c.test->>'myLast'=:LAST", nativeQuery = true)
//    List<Customer> findByJsonbLastName(@Param("LAST") String lastName);

}

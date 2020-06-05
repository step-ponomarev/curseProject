package com.spbstu.curse.repository;

import com.spbstu.curse.domain.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
  public Person findByGroupName(String name);
}

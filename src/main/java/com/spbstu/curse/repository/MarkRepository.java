package com.spbstu.curse.repository;

import com.spbstu.curse.domain.Mark;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkRepository extends CrudRepository<Mark, Long> {
  public List<Mark> findAllByTeacherFirstname(String firstname);
}

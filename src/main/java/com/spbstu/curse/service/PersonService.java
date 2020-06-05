package com.spbstu.curse.service;

import com.spbstu.curse.domain.Person;
import com.spbstu.curse.dto.PersonDto;
import com.spbstu.curse.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class PersonService {
  private final PersonRepository repository;
  private final ModelMapper modelMapper;

  public PersonDto findById(Long id) {
    return modelMapper.map(repository.findById(id).get(), PersonDto.class);
  }

  public List<PersonDto> findAll() {
    return StreamSupport.stream(repository.findAll().spliterator(), false)
        .map(person -> modelMapper.map(person, PersonDto.class))
        .collect(Collectors.toList());
  }

  public PersonDto save(PersonDto personDto) {
    return modelMapper.map(repository.save(modelMapper
        .map(personDto, Person.class)), PersonDto.class);
  }

  public PersonDto update(PersonDto personDto) {
    return modelMapper.map(repository.save(modelMapper
        .map(personDto, Person.class)), PersonDto.class);
  }

  public void delete(PersonDto personDto) {
    repository.delete(modelMapper.map(personDto, Person.class));
  }
}

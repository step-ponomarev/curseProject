package com.spbstu.curse.service;

import com.spbstu.curse.domain.Subject;
import com.spbstu.curse.dto.SubjectDto;
import com.spbstu.curse.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class SubjectService {
  private final SubjectRepository repository;
  private final ModelMapper modelMapper;

  public List<SubjectDto> findAll() {
    return StreamSupport.stream(repository.findAll().spliterator(), false)
        .map(subject -> modelMapper.map(subject, SubjectDto.class))
        .collect(Collectors.toList());
  }

  public SubjectDto findById(Long id) {
    return modelMapper.map(repository.findById(id).get(), SubjectDto.class);
  }

  public SubjectDto save(SubjectDto subjectDto) {
    return modelMapper
        .map(repository.save(modelMapper
            .map(subjectDto, Subject.class)), SubjectDto.class);
  }

  public void delete(SubjectDto subjectDto) {
    repository.delete(modelMapper.map(subjectDto, Subject.class));
  }

  public SubjectDto put(SubjectDto subjectDto) {
    return modelMapper
        .map(repository.save(modelMapper
            .map(subjectDto, Subject.class)), SubjectDto.class);
  }
}

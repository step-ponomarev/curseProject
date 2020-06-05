package com.spbstu.curse.service;

import com.spbstu.curse.domain.Mark;
import com.spbstu.curse.dto.MarkDto;
import com.spbstu.curse.repository.MarkRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class MarkService {
  private final MarkRepository repository;
  private final ModelMapper modelMapper;

  public List<MarkDto> findAll() {
    return StreamSupport.stream(repository.findAll().spliterator(), false)
        .map(mark -> modelMapper.map(mark, MarkDto.class))
        .collect(Collectors.toList());
  }

  public MarkDto findById(Long id) {
    return modelMapper.map(repository.findById(id).get(), MarkDto.class);
  }

  public MarkDto save(MarkDto markDto) {
    return modelMapper
        .map(repository.save(modelMapper
            .map(markDto, Mark.class)), MarkDto.class);
  }

  public void delete(MarkDto markDto) {
    repository.delete(modelMapper.map(markDto, Mark.class));
  }

  public MarkDto put(MarkDto markDto) {
    return modelMapper
        .map(repository.save(modelMapper
            .map(markDto, Mark.class)), MarkDto.class);
  }
}

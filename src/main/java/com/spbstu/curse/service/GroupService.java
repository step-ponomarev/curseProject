package com.spbstu.curse.service;

import com.spbstu.curse.domain.Group;
import com.spbstu.curse.dto.GroupDto;
import com.spbstu.curse.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class GroupService {
  private final GroupRepository repository;
  private final ModelMapper modelMapper;

  public List<GroupDto> findAll() {
    return StreamSupport.stream(repository.findAll().spliterator(), false)
        .map(group -> modelMapper.map(group, GroupDto.class))
        .collect(Collectors.toList());
  }

  public GroupDto findById(Long id) {
    return modelMapper.map(repository.findById(id).get(), GroupDto.class);
  }

  public GroupDto save(GroupDto groupDto) {
    return modelMapper
        .map(repository.save(modelMapper
            .map(groupDto, Group.class)), GroupDto.class);
  }

  public void delete(GroupDto groupDto) {
    repository.delete(modelMapper.map(groupDto, Group.class));
  }

  public GroupDto put(GroupDto groupDto) {
    return modelMapper
        .map(repository.save(modelMapper
            .map(groupDto, Group.class)), GroupDto.class);
  }
}

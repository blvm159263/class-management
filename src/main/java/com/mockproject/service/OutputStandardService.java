package com.mockproject.service;

import com.mockproject.repository.OutputStandardRepository;
import com.mockproject.service.interfaces.IOutputStandardService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class OutputStandardService implements IOutputStandardService {

    private final OutputStandardRepository repository;

}

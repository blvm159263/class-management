package com.mockproject.service;

import com.mockproject.repository.FsuRepository;
import com.mockproject.service.interfaces.IFsuService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class FsuService implements IFsuService {

    private final FsuRepository repository;

}

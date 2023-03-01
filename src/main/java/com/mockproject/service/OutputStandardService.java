package com.mockproject.service;

import com.mockproject.service.interfaces.IOutputStandardService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OutputStandardService implements IOutputStandardService {
}

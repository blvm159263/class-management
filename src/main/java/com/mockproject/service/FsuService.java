package com.mockproject.service;

import com.mockproject.service.interfaces.IFsuService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class FsuService implements IFsuService {
}

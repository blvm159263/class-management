package com.mockproject.service;

import com.mockproject.service.interfaces.ITowerService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TowerService implements ITowerService {
}

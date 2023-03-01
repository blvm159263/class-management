package com.mockproject.service;

import com.mockproject.service.interfaces.ILevelService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class LevelService implements ILevelService {
}

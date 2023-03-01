package com.mockproject.service;

import com.mockproject.service.interfaces.IAttendeeService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AttendeeService implements IAttendeeService {
}

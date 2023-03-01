package com.mockproject.service;

import com.mockproject.service.interfaces.IDeliveryTypeService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DeliveryTypeService implements IDeliveryTypeService {
}

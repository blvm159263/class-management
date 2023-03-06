package com.mockproject.service;

import com.mockproject.repository.DeliveryTypeRepository;
import com.mockproject.service.interfaces.IDeliveryTypeService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class DeliveryTypeService implements IDeliveryTypeService {

    private final DeliveryTypeRepository repository;

}

package com.mockproject.service;
import com.mockproject.repository.TrainingMaterialRepository;
import com.mockproject.service.interfaces.IUnitDetailService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {TrainingMaterialService.class})
@ExtendWith(SpringExtension.class)
public class TrainingMaterialServiceTest {
    @MockBean
    private TrainingMaterialRepository trainingMaterialRepository;
    @MockBean
    private IUnitDetailService unitDetailService;


    @Autowired
    private TrainingMaterialService trainingMaterialService;

    
}

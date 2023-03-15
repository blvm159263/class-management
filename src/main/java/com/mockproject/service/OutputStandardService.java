package com.mockproject.service;

import com.mockproject.dto.OutputStandardDTO;
import com.mockproject.entity.OutputStandard;
import com.mockproject.mapper.OutputStandardMapper;
import com.mockproject.repository.OutputStandardRepository;
import com.mockproject.service.interfaces.IOutputStandardService;
import com.mockproject.utils.ListUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OutputStandardService implements IOutputStandardService {

    private final OutputStandardRepository outputStandardRepository;

    @Override
    public OutputStandardDTO getOutputStandardById(long outputStandardId, boolean status){
        Optional<OutputStandard> outputStandard = outputStandardRepository.findByIdAndStatus(outputStandardId, status);
        outputStandard.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));

        OutputStandardDTO outputStandardDTO = OutputStandardMapper.INSTANCE.toDTO(outputStandard.get());
        return outputStandardDTO;
    }

    @Override
    public List<OutputStandardDTO> getOsdBySyllabusId(boolean status, long id) {
        return null;
    }
    @Override
    public List<OutputStandardDTO> getOutputStandard(boolean status) {
        Optional<List<OutputStandard>> outputStandardList = outputStandardRepository.findByStatus(status);
        ListUtils.checkList(outputStandardList);
        List<OutputStandardDTO> outputStandardDTOList = new ArrayList<>();
        for (OutputStandard o: outputStandardList.get()){
            outputStandardDTOList.add(OutputStandardMapper.INSTANCE.toDTO(o));
        }
        return outputStandardDTOList;
    }
}

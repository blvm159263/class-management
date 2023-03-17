package com.mockproject.service;

import com.mockproject.dto.OutputStandardDTO;
import com.mockproject.entity.OutputStandard;
import com.mockproject.mapper.OutputStandardMapper;
import com.mockproject.repository.OutputStandardRepository;
import com.mockproject.repository.UnitDetailRepository;
import com.mockproject.service.interfaces.IOutputStandardService;
import com.mockproject.utils.ListUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OutputStandardService implements IOutputStandardService {

    private final OutputStandardRepository outputStandardRepository;

    private final UnitDetailRepository unitDetailRepo;

    @Override
    public OutputStandardDTO getOutputStandardById(long outputStandardId, boolean status){
        Optional<OutputStandard> outputStandard = outputStandardRepository.findByIdAndStatus(outputStandardId, status);
        outputStandard.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));

        OutputStandardDTO outputStandardDTO = OutputStandardMapper.INSTANCE.toDTO(outputStandard.get());
        return outputStandardDTO;
    }

    @Override
    public List<OutputStandardDTO> getOsdBySyllabusId(boolean status, long id) {
        List<OutputStandard> osd = unitDetailRepo.findUnitDetailBySyllabusId(status, id)
                .stream().filter(distinctByKey(o -> o.getOutputStandard()))
                .filter(g -> g.getOutputStandard().isStatus()== true)
                .map(k -> k.getOutputStandard()).collect(Collectors.toList());
        if(osd.size() > 0){
            return osd.stream().map(OutputStandardMapper.INSTANCE::toDTO).collect(Collectors.toList());
        }else {
            throw new NotFoundException("Output standard not found with syllabus id: " + id);
        }
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
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

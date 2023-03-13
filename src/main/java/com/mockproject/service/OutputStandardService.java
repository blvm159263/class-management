package com.mockproject.service;

import com.mockproject.dto.OutputStandardDTO;
import com.mockproject.entity.OutputStandard;
import com.mockproject.mapper.OutputStandardMapper;
import com.mockproject.repository.OutputStandardRepository;
import com.mockproject.repository.UnitDetailRepository;
import com.mockproject.service.interfaces.IOutputStandardService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OutputStandardService implements IOutputStandardService {

    private final OutputStandardRepository outputStandardRepo;

    private final UnitDetailRepository unitDetailRepo;

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

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}

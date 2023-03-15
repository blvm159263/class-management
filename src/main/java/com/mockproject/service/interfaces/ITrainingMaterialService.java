package com.mockproject.service.interfaces;

import com.mockproject.dto.TrainingMaterialDTO;
import com.mockproject.entity.TrainingMaterial;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.zip.DataFormatException;

public interface ITrainingMaterialService {
    TrainingMaterialDTO uploadFile(MultipartFile file, long unitDetailsId, long userId) throws IOException;
    TrainingMaterialDTO getFile(long id) throws DataFormatException, IOException;
}

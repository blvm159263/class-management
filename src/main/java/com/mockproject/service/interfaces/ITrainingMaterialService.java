package com.mockproject.service.interfaces;

import com.mockproject.dto.TrainingMaterialDTO;
import com.mockproject.entity.TrainingMaterial;
import com.mockproject.entity.UnitDetail;
import com.mockproject.entity.User;

import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;

public interface ITrainingMaterialService {

    TrainingMaterialDTO uploadAFile(TrainingMaterialDTO createDTO, UnitDetail unitDetail, User user) throws IOException;
    TrainingMaterialDTO getFile(long id, boolean status) throws DataFormatException, IOException;
    List<TrainingMaterialDTO> uploadFile(List<TrainingMaterialDTO> createTrainingMaterialDTOList, User user, long unitDetailID);
    TrainingMaterialDTO updateFile(long id, TrainingMaterialDTO createDTO, User user, boolean status) throws IOException;
    List<TrainingMaterialDTO> getFiles(long unitDetailId, boolean status);
    boolean deleteTrainingMaterial(long trainingMaterialId, boolean status);
    boolean deleteTrainingMaterials(long unitDetailId, boolean status);

    List<TrainingMaterial> getListTrainingMaterialByUnitDetailId(long id);
}

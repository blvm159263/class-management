package com.mockproject.service.interfaces;

import java.util.List;

import com.mockproject.dto.AttendeeDTO;
import com.mockproject.dto.ClassScheduleDTO;
import com.mockproject.dto.TrainingClassDTO;
import com.mockproject.dto.TrainingProgramDTO;
import com.mockproject.dto.UserDTO;

public interface ITrainingClassService {
	public List<TrainingClassDTO> findClassDetail(Long id);
	public List<TrainingProgramDTO> findProgramDetail(Long id);
	public List<AttendeeDTO> findAttendee(Long id);
	public List<UserDTO> findUser(Long id);
	public List<ClassScheduleDTO> findClassShedule(Long id);
}

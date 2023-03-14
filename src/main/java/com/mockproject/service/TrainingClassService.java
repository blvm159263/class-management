package com.mockproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mockproject.dto.AttendeeDTO;
import com.mockproject.dto.ClassScheduleDTO;
import com.mockproject.dto.ContactDTO;
import com.mockproject.dto.FsuDTO;
import com.mockproject.dto.SyllabusDTO;
import com.mockproject.dto.TowerDTO;
import com.mockproject.dto.TrainingClassAdminDTO;
import com.mockproject.dto.TrainingClassDTO;
import com.mockproject.dto.TrainingClassUnitInformationDTO;
import com.mockproject.dto.TrainingProgramDTO;
import com.mockproject.dto.TrainingProgramSyllabusDTO;
import com.mockproject.dto.UserDTO;
import com.mockproject.entity.Tower;
import com.mockproject.entity.TrainingClass;
import com.mockproject.entity.TrainingClassUnitInformation;
import com.mockproject.entity.User;
import com.mockproject.mapper.AttendeeMapper;
import com.mockproject.mapper.ClassScheduleMapper;
import com.mockproject.mapper.ContactMapper;
import com.mockproject.mapper.FsuMapper;
import com.mockproject.mapper.SyllabusMapper;
import com.mockproject.mapper.TowerMapper;
import com.mockproject.mapper.TrainingClassAdminMapper;
import com.mockproject.mapper.TrainingClassMapper;
import com.mockproject.mapper.TrainingClassUnitInformationMapper;
import com.mockproject.mapper.TrainingProgramMapper;
import com.mockproject.mapper.TrainingProgramSyllabusMapper;
import com.mockproject.mapper.UserMapper;
import com.mockproject.repository.AttendeeRepository;
import com.mockproject.repository.ClassScheduleRepository;
import com.mockproject.repository.FsuRepository;
import com.mockproject.repository.SyllabusRepository;
import com.mockproject.repository.TowerRepository;
import com.mockproject.repository.TrainingClassAdminRepository;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.repository.TrainingClassUnitInformationRepository;
import com.mockproject.repository.TrainingProgramRepository;
import com.mockproject.repository.TrainingProgramSyllabusRepository;
import com.mockproject.repository.UserRepository;
import com.mockproject.service.interfaces.ITrainingClassService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class TrainingClassService implements ITrainingClassService{

    private final TrainingClassRepository classRepository;
    private final TrainingProgramRepository programRepository;
    private final SyllabusRepository syllabusRepository;
    private final TrainingProgramSyllabusRepository programSyllabusRepository;
    private final AttendeeRepository attendeeRepository;
    private final UserRepository userRepository;
    private final ClassScheduleRepository classScheduleRepository;
    private final TrainingClassUnitInformationRepository classUnitRepository;
    private final TrainingClassAdminRepository classAdminRepository;
    private final TowerRepository towerRepository;
    private final FsuRepository fsuRepository;

	@Override
	public List<TrainingClassDTO> findClassDetail(Long id) {
		List<TrainingClassDTO> classDetail = new ArrayList<>();
		List<TrainingClassDTO> getClass = classRepository.findById(id).stream().map(TrainingClassMapper.INSTANCE::toDTO).collect(Collectors.toList());
		if (!getClass.isEmpty()) {
			getClass.forEach((ele) -> {
				if (ele.isStatus()) {
					System.out.println("Location ID: "+ele.getLocationId());
					classDetail.add(ele);
				}
	        });
		}
		else {
			System.out.println("Empty");
		}
		return classDetail;
	}
	
	@Override
	public List<TrainingProgramDTO> findProgram(Long id) {
		List<TrainingProgramDTO> programDetail = new ArrayList<>();
		long proID = findClassDetail(id).get(0).getTrainingProgramId();
		List<TrainingProgramDTO> getProgram = programRepository.findById(proID).stream().map(TrainingProgramMapper.INSTANCE::toDTO).collect(Collectors.toList());
		getProgram.forEach((program) -> {
			if (program.isStatus()) {
				programDetail.add(program);
			}
        });
		if (programDetail.isEmpty()) {
			System.out.println("Empty");
		}
		return programDetail;
	}
	
	@Override
	public List<SyllabusDTO> findProgramDetail(Long id) {
		List<SyllabusDTO> programSyllabusDetail = new ArrayList<>();
		long proID = findProgram(id).get(0).getId();
		List<TrainingProgramSyllabusDTO> getProgramSyllabus = programSyllabusRepository.findAll().stream().map(TrainingProgramSyllabusMapper.INSTANCE::toDTO).collect(Collectors.toList());
		getProgramSyllabus.forEach((program) -> {
			if (program.getTrainingProgramId() == proID) {
				if (program.isStatus()) {
					long syllabusID = program.getSyllabusId();
					List<SyllabusDTO> getSyllabus = syllabusRepository.findById(syllabusID).stream().map(SyllabusMapper.INSTANCE::toDTO).collect(Collectors.toList());
					getSyllabus.forEach((syllabus) -> {
						if (syllabus.getId() == syllabusID) {
							if (syllabus.isStatus()) {
								programSyllabusDetail.add(syllabus);
							}
						}
					});
				}
			}
        });
		if (programSyllabusDetail.isEmpty()) {
			System.out.println("Empty");
		}
		return programSyllabusDetail;
	}

	@Override
	public List<AttendeeDTO> findAttendee(Long id) {
		List<AttendeeDTO> attendeeDetail = new ArrayList<>();
		long attendeeID = findClassDetail(id).get(0).getAttendeeId();
		List<AttendeeDTO> getAttendee = attendeeRepository.findById(attendeeID).stream().map(AttendeeMapper.INSTANCE::toDTO).collect(Collectors.toList());
		getAttendee.forEach((attendee) -> {
			if (attendee.isStatus()) {
				attendeeDetail.add(attendee);
			}
        });
		if (attendeeDetail.isEmpty()) {
			System.out.println("Empty");
		}
		return attendeeDetail;
	}
	
	@Override
	public List<UserDTO> findUser(Long id) {
		List<UserDTO> classAttendee = new ArrayList<>();
		long attendeeID_1 = findClassDetail(id).get(0).getAttendeeId();
		long attendeeID_user = findAttendee(attendeeID_1).get(0).getId();
		List<UserDTO> userDetail = userRepository.findAll().stream().map(UserMapper.INSTANCE::toDTO).collect(Collectors.toList());
		userDetail.forEach((user) -> {
			if (user.getAttendeeId() == attendeeID_user) {
				if (user.isStatus()) {
					classAttendee.add(user);
				}
			}
        });
		if (classAttendee.isEmpty()) {
			System.out.println("Empty");
		}
		return classAttendee;
	}

	@Override
	public List<ClassScheduleDTO> findClassShedule(Long id) {
		List<ClassScheduleDTO> classSchedule = new ArrayList<>();
		long classID = findClassDetail(id).get(0).getAttendeeId();
		List<ClassScheduleDTO> getClass = classScheduleRepository.findAll().stream().map(ClassScheduleMapper.INSTANCE::toDTO).collect(Collectors.toList());
		getClass.forEach((trainClass) -> {
			if (trainClass.getTrainingClassId() == classID) {
				if (trainClass.isStatus()) {
					classSchedule.add(trainClass);
				}
			}
        });
		if (classSchedule.isEmpty()) {
			System.out.println("Empty");
		}
		return classSchedule;
	}
	
	@Override
	public List<UserDTO> findTrainer(Long id) {
		List<UserDTO> trainer = new ArrayList<>();
		List<TrainingClassUnitInformationDTO> getClassUnit = classUnitRepository.findAll().stream().map(TrainingClassUnitInformationMapper.INSTANCE::toDTO).collect(Collectors.toList());
		getClassUnit.forEach((trainClass) -> {
			if (trainClass.getTrainingClassId() == id) {
				if (trainClass.isStatus()) {
					long trainerID = trainClass.getTrainerId();
					List<UserDTO> userDetail = userRepository.findById(trainerID).stream().map(UserMapper.INSTANCE::toDTO).collect(Collectors.toList());
					if (userDetail.get(0).getId() == trainerID) {
						if (userDetail.get(0).isStatus()) {
							if (trainer.isEmpty()) {
								trainer.add(userDetail.get(0));
							}
							else {
								List<UserDTO> tmpList = new ArrayList<>(trainer);
								tmpList.forEach((userId) -> {
									if (userId.getId() != trainerID) {
										trainer.add(userDetail.get(0));
									}
								});
							}
						}
					}
				}
			}
        });
		if (trainer.isEmpty()) {
			System.out.println("Empty");
		}
		return trainer;
	}
	
	@Override
	public List<UserDTO> findAdmin(Long id) {
		List<UserDTO> admin = new ArrayList<>();
		List<TrainingClassAdminDTO> getClassAdmin = classAdminRepository.findAll().stream().map(TrainingClassAdminMapper.INSTANCE::toDTO).collect(Collectors.toList());
		getClassAdmin.forEach((classAdmin) -> {
			if (classAdmin.getTrainingClassId() == id) {
				if (classAdmin.isStatus()) {
					long adminID = classAdmin.getAdminId();
					List<UserDTO> userDetail = userRepository.findById(adminID).stream().map(UserMapper.INSTANCE::toDTO).collect(Collectors.toList());
					if (userDetail.get(0).getId() == adminID) {
						if (userDetail.get(0).isStatus()) {
							if (admin.isEmpty()) {
								admin.add(userDetail.get(0));
							}
							else {
								List<UserDTO> tmpList = new ArrayList<>(admin);
								tmpList.forEach((userId) -> {
									if (userId.getId() != adminID) {
										admin.add(userDetail.get(0));
									}
								});
							}
						}
					}
				}
			}
        });
		if (admin.isEmpty()) {
			System.out.println("Empty");
		}
		return admin;
	}
	
	public List<TowerDTO> findTower(Long id){
		TrainingClass tc = classRepository.findByIdAndStatus(id, true);

        List<TrainingClassUnitInformation> list = tc.getListTrainingClassUnitInformations()
                .stream()
                .filter(TrainingClassUnitInformation::isStatus)
                .toList();
        
        List<Tower> listTowers = tc.getLocation().getListTowers()
                .stream()
                .filter(Tower::isStatus)
                .toList();

        List<Tower> listTower = list.stream()
                .map(p -> towerRepository.findById(p.getTower().getId())
                        .filter(Tower::isStatus)
                        .orElseThrow())
                .toList();

        return listTowers.stream().map(TowerMapper.INSTANCE::toDTO).toList();

	}
	
	@Override
    public FsuDTO getFsu(long id) {
        TrainingClass tc = classRepository.findByIdAndStatus(id, true);
        if(tc.getFsu().isStatus()){
            return FsuMapper.INSTANCE.toDTO(tc.getFsu());
        }
        return null;
    }


	@Override
    public ContactDTO getContact(long id) {
        TrainingClass tc = classRepository.findByIdAndStatus(id, true);

        if(tc.getContact().isStatus()){
            return ContactMapper.INSTANCE.toDTO(tc.getContact());
        }
        return null;
    }

	@Override
    public UserDTO getCreator(long id) {
        TrainingClass tc = classRepository.findByIdAndStatus(id, true);
        User user = userRepository.findById(tc.getCreator().getId()).filter(User::isStatus).orElseThrow();
        return UserMapper.INSTANCE.toDTO(user);
    }


}

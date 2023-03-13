package com.mockproject.service.interfaces;

import com.mockproject.dto.SearchUserFillerDTO;
import com.mockproject.dto.UserDTO;
import com.mockproject.entity.User;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<UserDTO> getAll();

    Long countAllBy();

    List<UserDTO> getAllByPageAndRowPerPage(long page, long rowPerPage);

    Page<UserDTO> searchByFillter(Long id, LocalDate dob, String email, String fullName, Boolean gender, String phone, List<Integer> stateId, List<Long> atendeeId, List<Long> levelId, List<Long> role_id, Optional<Integer> page, Optional<Integer> size, List<String> sort) throws Exception;

}

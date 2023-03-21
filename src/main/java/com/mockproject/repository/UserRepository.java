package com.mockproject.repository;

import com.mockproject.entity.Role;
import com.mockproject.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

   Optional<User> findByEmailAndStatus(String email, Boolean status);
    Optional<User> findByStatusAndId(boolean status, long id);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    List<User> findAllByStatus(boolean status);

    Optional<User> findByIdAndStatus(Long id, Boolean status);

    Optional<User> findByPhone(String phone);


    List<User> findAllBy();

    @Query(value = "SELECT * FROM tblUser\n" +
            "ORDER BY ID\n" +
            "OFFSET ((?1-1)*?2) ROWS FETCH NEXT ?2 ROWS ONLY", nativeQuery = true)
    List<User> getAllByPageAndRowPerPage(Long page, Long rowPerPage);

    @Query(value = "select u from User u "+
            "where (:id is null or u.id = :id) " +
            "and (:dob is null or u.dob = :dob)" +
            "and (:email is null or u.email like '%'+:email+'%')" +
            "and (:fullname is null or u.fullName like '%'+:fullname+'%')" +
            "and (:gender is null or u.gender = :gender)" +
            "and (:phone is null or u.phone like '%'+:phone+'%')" +
            "and (:#{#state.size()} = 0 or u.state in :state)" +
            "and (:#{#attendeeId.size()} = 0 or u.attendee.id in :attendeeId)" +
            "and (:#{#levelId.size()} = 0 or u.level.id in :levelId)" +
            "and (:#{#roleId.size()} = 0 or u.role.id in :roleId)" +
            "and u.status = true"
    )
    Page<User> searchByFilter(Long id, LocalDate dob, String email, String fullname, Boolean gender, String phone, List<Integer> state, List<Long> attendeeId, List<Long> levelId, List<Long> roleId,Pageable pageable);

    List<User> findByRoleAndStatus(Role role, boolean status);

    Optional<User> findByEmailAndStatus(String email, boolean status);

}

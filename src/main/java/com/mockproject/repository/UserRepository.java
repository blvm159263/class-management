package com.mockproject.repository;

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

   Optional<User> findByEmail(String email);

   Optional<User> findById(Long id);

   List<User> findAllBy();

    @Query(value = "SELECT * FROM tblUser\n" +
            "ORDER BY ID\n" +
            "OFFSET ((?1-1)*?2) ROWS FETCH NEXT ?2 ROWS ONLY", nativeQuery = true)
     List<User> getAllByPageAndRowPerPage(long page, long rowPerPage);

    @Query(value = "select u from User u "+
        "where (:id is null or u.id = :id) " +
            "and (:dob is null or u.dob = :dob)" +
            "and (:email is null or u.email like '%'+:email+'%')" +
            "and (:fullname is null or u.fullName like '%'+:fullname+'%')" +
            "and (:gender is null or u.gender = :gender)" +
            "and (:phone is null or u.phone like '%'+:phone+'%')" +
            "and (:#{#state.size()} = 0 or u.state in :state)" +
            "and (:#{#attendee_id.size()} = 0 or u.attendee.id in :attendee_id)" +
            "and (:#{#level_id.size()} = 0 or u.level.id in :level_id)" +
            "and (:#{#role_id.size()} = 0 or u.role.id in :role_id)" +
            "and u.status = true"
    )
    Page<User> searchByFilter(Long id, LocalDate dob, String email, String fullname, Boolean gender, String phone, List<Integer> state, List<Long> attendee_id, List<Long> level_id, List<Long> role_id,Pageable pageable);
}

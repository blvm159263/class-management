package com.mockproject.repository;

import com.mockproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);

    public Optional<User> findById(Long id);

    public List<User> findAllBy();

    @Query(value = "SELECT * FROM tblUser\n" +
            "ORDER BY ID\n" +
            "OFFSET ((?1-1)*?2) ROWS FETCH NEXT ?2 ROWS ONLY", nativeQuery = true)
    public List<User> getAllByPageAndRowPerPage(long page, long rowPerPage);

    public Long countAllBy();

    @Query(value = "SELECT * from tblUser\n" +
            "where id = ISNULL(?1, id)\n" +
            "and date_of_birth = ISNULL(?2, date_of_birth)\n" +
            "and email like ISNULL(N'%'+?3+'%', N'%%')\n" +
            "and fullname like ISNULL(N'%'+?4+'%', N'%%')\n" +
            "and gender = ISNULL(?5, gender)\n" +
            "and phone like ISNULL(N'%'+?6+'%', N'%%')\n" +
            "and [state] = ISNULL(?7, [state])\n" +
            "and attendee_id = ISNULL(?8, attendee_id)\n" +
            "and level_id = ISNULL(?9, level_id)\n" +
            "and role_id = ISNULL(?10, role_id)\n" +
            "and [status] = 1", nativeQuery = true)
    public List<User> searchByFiller(Long id, LocalDate dob, String email, String fullname, Boolean gender, String phone, Long state, Long attendee_id, Long level_id, Long role_id);

}

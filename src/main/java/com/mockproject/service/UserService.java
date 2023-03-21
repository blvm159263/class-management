package com.mockproject.service;

import com.mockproject.dto.UserDTO;
import com.mockproject.dto.UserDTOCustom;
import com.mockproject.entity.Level;
import com.mockproject.entity.Role;
import com.mockproject.entity.User;
import com.mockproject.mapper.AttendeeMapper;
import com.mockproject.mapper.LevelMapper;
import com.mockproject.mapper.RoleMapper;
import com.mockproject.entity.*;
import com.mockproject.mapper.UserMapper;
import com.mockproject.repository.*;
import com.mockproject.service.interfaces.IUnitService;
import com.mockproject.service.interfaces.IUserService;
import com.opencsv.CSVReader;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.*;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements IUserService {

    private static final Long SUPER_ADMIN = 1L;
    private static final Long CLASS_ADMIN = 2L;
    private static final Long TRAINER = 3L;
    private static final Long STUDENT = 4L;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepo;
    private final LevelRepository levelRepository;
    private final RoleRepository roleRepository;

    private final AttendeeRepository attendeeRepository;

    @Override
    public UserDTO getUserById(boolean status, Long id) {
        User user = userRepo.findByStatusAndId(status, id).orElseThrow(() -> new NotFoundException("Users not found with id: " + id));
        return UserMapper.INSTANCE.toDTO(user);
    }

    @Override
    public int getStateIdByStateName(String name) {
        switch (name) {
            case "De-active":
                return 0;
            case "Active":
                return 1;
            case "In class":
                return 2;
            case "Off class":
                return 3;
            case "On boaring":
                return 4;
            default:
                return -1;

        }
    }


    @Override
    public List<UserDTO> listClassAdminTrue() {
        Role role = new Role();
        role.setId(CLASS_ADMIN);
        return userRepo.findByRoleAndStatus(role, true).stream().map(UserMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> listTrainerTrue() {
        Role role = new Role();
        role.setId(TRAINER);
        return userRepo.findByRoleAndStatus(role, true).stream().map(UserMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        return UserMapper.INSTANCE.toDTO(userRepo.findById(id).orElse(null));
    }

    @Override
    public List<UserDTO> getAllUser(boolean status) {
        return userRepo.findAllByStatus(status).stream().map(UserMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }


    @Override
    public List<UserDTOCustom> getAllByPageAndRowPerPage(Long page, Long rowPerPage) {
        List<User> listUser = userRepo.getAllByPageAndRowPerPage(page, rowPerPage);

        List<UserDTOCustom> mlist = new ArrayList<>();
        for (User u : listUser) {
            UserDTOCustom userDTOCustom = new UserDTOCustom(u.getId(), u.getEmail(), u.getFullName(), u.getImage(), getState(u.getState()), u.getDob(), u.getPhone(), u.isGender(), u.isStatus(),
                    RoleMapper.INSTANCE.toDTO(u.getRole()), LevelMapper.INSTANCE.toDTO(u.getLevel()), AttendeeMapper.INSTANCE.toDTO(u.getAttendee()));
            mlist.add(userDTOCustom);
        }
        return mlist;
    }

    @Override
    public List<UserDTO> getAll() {
        return userRepo.findAllBy().stream().map(UserMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    public Page<UserDTO> searchByFilter(Long id, LocalDate dob, String email, String fullName, Boolean gender, String phone, List<Integer> stateId, List<Long> atendeeId, List<Long> levelId, List<Long> role_id, Optional<Integer> page, Optional<Integer> size, List<String> sort) throws Exception {
        int page1 = 0;
        int size1 = 10;
        Pageable pageable;
        List<Sort.Order> order = new ArrayList<>();
        if (page.isPresent()) page1 = page.get() - 1;
        if (size.isPresent()) size1 = size.get();
        if (sort != null && !sort.isEmpty()) {
            for (String sortItem : sort) {
                String[] subSort = sortItem.split("-");
                order.add(new Sort.Order(getSortDirection(subSort[1]), subSort[0]));
            }
            pageable = PageRequest.of(page1, size1, Sort.by(order));
        } else {
            pageable = PageRequest.of(page1, size1);
        }
        Page<User> pages;
        try {
            pages = userRepo.searchByFilter(id, dob, email, fullName, gender, phone, stateId, atendeeId, levelId, role_id, pageable);

            List<UserDTOCustom> result = new ArrayList<>();
            for (User u : pages) {
                UserDTOCustom userDTOCustom = new UserDTOCustom(u.getId(), u.getEmail(), u.getFullName(), u.getImage(), getState(u.getState()), u.getDob(), u.getPhone(), u.isGender(), u.isStatus(),
                        RoleMapper.INSTANCE.toDTO(u.getRole()), LevelMapper.INSTANCE.toDTO(u.getLevel()), AttendeeMapper.INSTANCE.toDTO(u.getAttendee()));
                result.add(userDTOCustom);
            }

        } catch (Exception e) {
            throw e;
        }
        return new PageImpl<>(
                pages.stream().map(UserMapper.INSTANCE::toDTO).collect(Collectors.toList()),
                pages.getPageable(),
                pages.getTotalElements());
    }

    public void encodePassword() {
        for (User user : userRepo.findAllBy()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);
        }
    }

    public Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

    @Override
    public boolean updateStatus(Long id) {
        boolean status = false;
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            User u = user.get();
            u.setStatus(false);
            userRepo.save(u);
            status = true;
        }
        return status;
    }

    @Override
    public Integer updateStateToFalse(Long id) {
        Optional<User> user = userRepo.findById(id);
        int state = -1;
        if (user.isPresent()) {
            User u = user.get();
            u.setState(0);
            userRepo.save(u);
            state = 0;
        }
        return state;
    }

    @Override
    public Integer updateStateToTrue(Long id) {
        Optional<User> user = userRepo.findById(id);
        int state = -1;
        if (user.isPresent()) {
            User u = user.get();
            u.setState(1);
            userRepo.save(u);
            state = 1;
        }
        return state;
    }

    @Override
    public boolean changeRole(Long id, Long roleId) {
        Optional<User> user = userRepo.findById(id);
        Optional<Role> role = roleRepository.getRoleById(roleId);
        if (user.isPresent() && role.isPresent()) {
            User user1 = user.get();
            Role role1 = role.get();
            user1.setRole(role1);
            userRepo.save(user1);
            return true;
        }
        return false;
    }

    @Override
    public boolean editName(Long id, String name) {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            User u = user.get();
            u.setFullName(name);
            userRepo.save(u);
            return true;
        }
        return false;
    }

    @Override
    public boolean editDoB(Long id, LocalDate date) {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            User u = user.get();
            u.setDob(date);
            userRepo.save(u);
            return true;
        }
        return false;
    }

    @Override
    public boolean editGender(Long id, boolean gender) {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            User u = user.get();
            u.setGender(gender);
            userRepo.save(u);
            return true;
        }
        return false;
    }

    @Override
    public boolean editLevel(Long id, String levelCode) {
        Optional<User> user = userRepo.findById(id);
        Optional<Level> level = levelRepository.getLevelByLevelCode(levelCode);
        if (user.isPresent() && level.isPresent()) {
            User user1 = user.get();
            Level level1 = level.get();
            user1.setLevel(level1);
            userRepo.save(user1);
            return true;
        }
        return false;
    }

    @Override
    public boolean editUser(UserDTO user) {
        Optional<User> user1 = userRepo.findById(user.getId());
        Optional<Level> level = levelRepository.getLevelById(user.getLevelId());
        if (user1.isPresent()) {
            User u = user1.get();
            Level level1 = level.get();
            u.setFullName(user.getFullName());
            u.setDob(user.getDob());
            u.setGender(user.isGender());
            u.setLevel(level1);
            userRepo.save(u);
            return true;
        }
        return false;
    }

    @Override
    public String readCSVFile(File file) {
        List<User> userList = new ArrayList<>();
        try {
            if (!file.exists()) {
                return "File not Found!";
            } else {
                // create a reader for the CSV file
                CSVReader reader = new CSVReader(new FileReader(file));

                // read the header row
                String[] headerRow = reader.readNext();

                // read the data rows and map them to Product objects
                String[] rowData;
                while ((rowData = reader.readNext()) != null) {
                    System.out.println(rowData[0]);
                    System.out.println(rowData[1]);
                    System.out.println(rowData[2]);
                    System.out.println(rowData[3]);
                    System.out.println(rowData[4]);
                    System.out.println(rowData[5]);
                    User user = new User();
                    user.setEmail(rowData[0]);
                    user.setPassword(passwordEncoder.encode("123456"));
                    user.setFullName(rowData[1]);
                    if (rowData[2].equals("Nam")) {
                        user.setGender(true);
                    } else {
                        user.setGender(false);
                    }
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    user.setDob(LocalDate.parse(rowData[3], formatter));
                    user.setPhone(rowData[4]);
                    user.setStatus(true);
                    userList.add(user);
                    userRepo.save(user);
                }
                reader.close();
            }

        } catch (Exception e) {
            return String.valueOf(e);
        }
        return userList.toString();
    }

    private String getState(int id) {
        switch (id) {
            case 0:
                return "De-active";
            case 1:
                return "Active";
            case 2:
                return "In class";
            case 3:
                return "Off class";
            case 4:
                return "On boaring";
            default:
                return "";
        }
    }

    @Override
    public ByteArrayInputStream getCSVUserFileExample() {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out, true, StandardCharsets.UTF_8), format);) {
            List<String> header = Arrays.asList("Email", "Full name", "Gender", "Date of birth", "Image link", "Password", "Phone", "State", "Attendee", "Level", "Role");
            List<String> data1 = Arrays.asList("example1@gmail.com", "Nguyen Bao Long", "Male", "28/10/2002", "imagelink1", "password1", "01123456789", "In class", "Fresher", "AA", "Trainer");
            List<String> data2 = Arrays.asList("example2@gmail.com", "Nguyễn Thị Minh Tâm", "Female", "28/11/2002", "imagelink2", "password2", "01123456789", "In class", "Fresher", "AA", "Trainer");
            csvPrinter.printRecord(header);
            csvPrinter.printRecord(data1);
            csvPrinter.printRecord(data2);
            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }

    @Override
    public void storeListUser(List<User> list) {
        userRepo.saveAll(list);
    }

    @Override
    public List<User> csvToUsers(InputStream is) {
        boolean checkUserEmail = true;
        boolean replace = true;
        boolean skip = false;
        String[] headers = {"Email", "Full name", "Gender", "Date of birth", "Image link", "Password", "Phone", "State", "Attendee", "Level", "Role"};

        Long count = null;
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<User> result = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            count = 0L;

            List<String> listEmail = new ArrayList<>();

            for (CSVRecord csvRecord : csvRecords) {
                String email = csvRecord.get("Email");
                if (checkUserEmail && replace) {
                    Long idUser = null;
                    if (userRepo.findByEmail(email).isPresent()) {
                        idUser = userRepo.findByEmail(email).get().getId();
                    }
                    String fullName = csvRecord.get("Full name");
                    String imgLink = csvRecord.get("Image link");
                    //check state
                    int state = getStateIdByStateName(csvRecord.get("State"));
                    if (state == -1)
                        throw new NotFoundException("Import successfull " + count + " user\n" + "Record " + (count + 1) +
                                " (" + email + ")" + " is invalid state");
                    //check date of birth
                    LocalDate dob;
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                        dob = LocalDate.parse(csvRecord.get("Date of birth"), formatter);
                    } catch (DateTimeParseException e) {
                        throw new NotFoundException("Import successfull " + count + " user\n" + "Record " + (count + 1) +
                                " (" + email + ")" + " is invalid date");
                    }
                    //check phone number
                    String phone = csvRecord.get("Phone");
                    if (userRepo.findByPhone(phone).isPresent() && (idUser != userRepo.findByPhone(phone).get().getId()))
                        throw new NotFoundException("Import successfull " + count + " user\n" + "Record " + (count + 1) +
                                " (" + email + ")" + " is invalid phone number (phone number is exits!!!)");
                    //check gender
                    boolean gender = false;
                    if (csvRecord.get("Gender").equals("Female")) {
                        gender = false;
                    } else if (csvRecord.get("Gender").equals("Male")) {
                        gender = true;
                    }
                    boolean status = true;

                    //check role
                    Long roleId = null;
                    if (roleRepository.getRoleByRoleName(csvRecord.get("Role")).isPresent()) {
                        roleId = roleRepository.getRoleByRoleName(csvRecord.get("Role")).get().getId();
                    }
                    //check level
                    Long levelId = null;
                    if (levelRepository.getLevelByLevelCode(csvRecord.get("Level")).isPresent()) {
                        levelId = levelRepository.getLevelByLevelCode(csvRecord.get("Level")).get().getId();
                    }
                    //check attendee
                    Long attendeeId = null;
                    if (attendeeRepository.findByAttendeeNameAndStatus(csvRecord.get("Attendee"), true).isPresent()) {
                        attendeeId = attendeeRepository.findByAttendeeNameAndStatus(csvRecord.get("Attendee"), true).get().getId();
                    }

                    UserDTO userDTO = new UserDTO(email, fullName, imgLink,
                            state, dob, phone, gender, status, roleId, levelId,
                            attendeeId);


                    User user = UserMapper.INSTANCE.toEntity(userDTO);
                    user.setPassword(passwordEncoder.encode(csvRecord.get("Password")));
                    if (idUser != null) user.setId(idUser);

                    result.add(user);
                    count++;
                }
            }

            return result;

        } catch (IOException e) {
            throw new NotFoundException("Import " + count + " user successfull\n" + "Record " + count + 1 + " is invalid!!!");
        }
    }


}

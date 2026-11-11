package com.mcmanuel.domain.staff.non_teaching;

import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import com.mcmanuel.client.CourseClient;
import com.mcmanuel.domain.staff.AwsService;
import com.mcmanuel.domain.staff.JwtService;
import com.mcmanuel.domain.staff.Staff;
import com.mcmanuel.domain.staff.StaffServiceImpl;
import com.mcmanuel.domain.staff.lecturer.LecturerRepository;
import com.mcmanuel.domain.token.TokenService;
import com.mcmanuel.email.EmailService;
import com.mcmanuel.enums.Department;
import com.mcmanuel.enums.Role;
import com.mcmanuel.enums.Status;
import com.mcmanuel.exception.DepartmentNotFoundException;
import com.mcmanuel.exception.StaffNotFoundException;
import com.mcmanuel.pojo.RegisterRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(rollbackFor = Exception.class)
public class NonTeachingServiceImpl extends StaffServiceImpl<NonTeachingDto> implements NonTeachingService{
    private final NonTeachingRepository nonTeachingRepo;
    private final CourseClient courseClient;
    private final PasswordEncoder passwordEncoder;
    private final AwsService awsService;
    private final LecturerRepository lecturerRepo;
    private final Client client;


    public NonTeachingServiceImpl(JwtService jwtService, AuthenticationManager manager, EmailService emailService, TokenService tokenService,
                                  LecturerRepository lecturerRepo, NonTeachingRepository nonTeachingRepo, CourseClient courseClient, PasswordEncoder passwordEncoder, AwsService awsService, LecturerRepository lecturerRepo1, Client client) {
        super(jwtService, manager, emailService, tokenService, lecturerRepo, nonTeachingRepo);
        this.nonTeachingRepo = nonTeachingRepo;
        this.courseClient = courseClient;
        this.passwordEncoder = passwordEncoder;
        this.awsService = awsService;
        this.lecturerRepo = lecturerRepo1;
        this.client = client;
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public NonTeachingDto registerStaff(String email, RegisterRequest request) {
        try{
            if (!request.getCreatePassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Password mismatch");
            }

            if (request.getStatus() ==Status.NON_TEACHING){
            String photoName = request.getLastname()+"_"+request.getFirstname()+"_passport";

            NonTeaching nonTeachingStaff = NonTeaching.builder()
                .staffId(generateStaffId(request.getDepartment().name()))
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .department(request.getDepartment())
                .dateCreated(LocalDateTime.now())
                .build();


            ArrayList<Role> roles = new ArrayList<>();
            roles.add(Role.NON_TEACHING);
//            try(FileInputStream stream = new FileInputStream(request.getFile())) {
           
//                if (assessPhoto(request.getFile())) {
//                todo fix this
//                nonTeachingStaff.setImageUrl(awsService.uploadPhoto(photoName,request.getFile()));
//                    nonTeachingStaff.setImageUrl(photoName);
//                } else throw new RuntimeException("Invalid photo");
//            }

            nonTeachingStaff.setImageUrl(photoName);
            nonTeachingStaff.setRoles(roles);
            nonTeachingStaff.setFirstname(request.getFirstname());
            nonTeachingStaff.setLastname(request.getLastname());
            nonTeachingStaff.setEnabled(true);

            return Mapper.toDto(nonTeachingRepo.save(nonTeachingStaff));
        }
            else throw new RuntimeException("Invalid Staff Status");
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }


    private String generateStaffId(String department) {
//        CSC0805008
        if ( Arrays.stream(Department.values()).noneMatch(dept ->
                dept.name().equalsIgnoreCase(department))) {
            throw new DepartmentNotFoundException("Department "+department+" Not Found");
        }

        Department verifiedDepartment = Department.valueOf(department);
        String end ;

        int last_index = Integer.parseInt(getStaffList(department).isEmpty()?"00": getStaffList(department).getLast().substring(7));
        System.out.println("The last index is "+last_index);
        if(last_index <=9) {
            end = "00"+(last_index+1);
        }
        else if (last_index <=99) {
            end = "0"+(last_index+1);
        }
        else end = String.valueOf(last_index+1);

        return verifiedDepartment.getCode()+verifiedDepartment.getFaculty().getCode()+verifiedDepartment.getNumber()+end;
    }


    private boolean assessPhoto(MultipartFile photo){
        try {
            byte [] bytes = photo.getBytes();
            GenerateContentResponse response;

            Content content = Content.fromParts(
                    Part.fromBytes(bytes,"image/jpeg"),
                    Part.fromText("Does this photo fit for a passport, on a scale of 1-10, give the integer value only")
            );

            response= client.models.generateContent("gemini-3.8-flash", content, null);
            return Integer.parseInt(Objects.requireNonNull(response.text()).trim()) > 5;
        }
        catch (NumberFormatException ex){
            throw new NumberFormatException("Invalid pictorial response");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public NonTeachingDto findStaffByStaffId(String staffId) {
        return Mapper.toDto(
                (NonTeaching) nonTeachingRepo.findByStaffId(staffId).orElseThrow(()-> new StaffNotFoundException("Lecturer Not Found"))
        );
    }

    @Override
    public NonTeachingDto findStaffByEmail(String email) {
        return Mapper.toDto(
                (NonTeaching) nonTeachingRepo.findByStaffId(email).orElseThrow(()-> new StaffNotFoundException("Lecturer Not Found"))
        );
    }

    @Override
    public List<NonTeachingDto> getAllStaffs(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by("staffId"));
        return nonTeachingRepo.findAll(pageable).stream()
                .map(Mapper::toDto)
                .toList();
    }

    @Override
    public NonTeachingDto updateBio(String staffId, String email, String phoneNumber) {
        NonTeaching nonTeaching= (NonTeaching) nonTeachingRepo.findByStaffId(staffId).orElseThrow(()-> new StaffNotFoundException("Lecturer Not Found"));
        nonTeaching.setEmail(email);
        nonTeaching.setPhoneNumber(phoneNumber);
        return Mapper.toDto(nonTeachingRepo.save(nonTeaching));
    }

    @Override
    public boolean deleteStaff(String staffId) {
        NonTeaching nonTeaching = (NonTeaching) nonTeachingRepo.findByStaffId(staffId).orElseThrow(()-> new StaffNotFoundException("Lecturer Not Found"));
        nonTeachingRepo.delete(nonTeaching);
        return true;
    }

    @Override
    public List<String> getStaffList(String department) {
        if ( Arrays.stream(Department.values()).noneMatch(dept ->
                dept.name().equalsIgnoreCase(department))) {
            throw new DepartmentNotFoundException("Department "+department+" Not Found");
        }

        return nonTeachingRepo.findAll().stream()
                .filter(nonTeaching -> ((NonTeaching)nonTeachingRepo).getDepartment().equals(Department.valueOf(department)))
                .map(Staff::getStaffId)
                .toList();
    }

    @Override
    public String makeAdmin(String staffId) {
        NonTeaching nonTeaching = (NonTeaching) nonTeachingRepo.findByStaffId(staffId).orElseThrow(() -> new StaffNotFoundException("Lecturer with Id Not Found "+staffId));

        if (nonTeaching.getRoles().contains(Role.ADMIN)){
            return "Already an Admin";
        }
        nonTeaching.getRoles().add(Role.ADMIN);

        nonTeachingRepo.save(nonTeaching);
        return staffId+"'s role Updated to Admin";
    }

    @Override
    public String removeAdmin(String staffId) {
        NonTeaching nonTeaching = (NonTeaching) nonTeachingRepo.findByStaffId(staffId).orElseThrow(() -> new StaffNotFoundException("Lecturer with Id Not Found "+staffId));

        if (nonTeaching.getRoles().contains(Role.ADMIN)){
            return "Not an Admin";
        }
        nonTeaching.getRoles().add(Role.ADMIN);

        nonTeachingRepo.save(nonTeaching);
        return staffId+"'s role Updated to Admin";
    }
}

package com.mcmanuel.domain.lecturer;

import com.mcmanuel.pojo.RegisterRequest;
import com.mcmanuel.domain.token.TokenService;
import com.mcmanuel.email.EmailService;
import com.mcmanuel.enums.Role;
import com.mcmanuel.exception.LecturerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class LecturerServiceImpl implements LecturerService{
    private final LecturerRepository lecturerRepo;
    private final EmailService emailService;
    private final TokenService tokenService;

    @Override
    public LecturerDto registerLecturer(String email, RegisterRequest request) {
        Lecturer lecturer = Lecturer.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .staffNumber(generateStaffId())
                .email(email)
                .phoneNumber("09081199688")
                .department(Department.COMPUTER_SCIENCE)
                .dateCreated(LocalDateTime.now())
                .role(Role.LECURER)
                .department(request.getDepartment())
                .imageUrl(request.getFile().getOriginalFilename())
                .build();
        return Mapper.toDto(lecturerRepo.save(lecturer));
    }

    private String generateStaffId() {
        return "170805008";
    }


    @Override
    public LecturerDto findLecturerByStaffNumber(String staffNumber) {
        return Mapper.toDto(
                lecturerRepo.findByStaffNumber(staffNumber).orElseThrow(()-> new LecturerNotFoundException("Lecturer Not Found"))
        );
    }

    @Override
    public List<LecturerDto> getAllLecturers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by("staffId"));
        return lecturerRepo.findAll(pageable).stream()
                .map(Mapper::toDto)
                .toList();
    }

    @Override
    public LecturerDto updateBio(String staffNumber, LecturerDto updatedDto) {
        Lecturer lecturer= lecturerRepo.findByStaffNumber(staffNumber).orElseThrow(()-> new LecturerNotFoundException("Lecturer Not Found"));
        Lecturer updatedLecturer= Mapper.toLecturer(updatedDto);
        updatedLecturer.setLecturerId(lecturer.getLecturerId());
        return Mapper.toDto(updatedLecturer);
    }

    @Override
    public boolean deleteLecturer(String staffNumber) {
        Lecturer lecturer =lecturerRepo.findByStaffNumber(staffNumber).orElseThrow(()-> new LecturerNotFoundException("Lecturer Not Found"));
        lecturerRepo.delete(lecturer);
        return true;
    }

    @Override
    public void sendUserEmail(String email) throws MessagingException, jakarta.mail.MessagingException {
        emailService.sendEmail(email,tokenService.generateToken(email).getToken());
    }

    @Override
    public boolean verifyOtp(String email,String otp) {
        return tokenService.verifyOtp(email,otp);
    }

}

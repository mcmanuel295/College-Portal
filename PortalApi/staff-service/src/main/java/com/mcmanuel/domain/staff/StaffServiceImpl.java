package com.mcmanuel.domain.staff;

import com.mcmanuel.domain.staff.lecturer.*;
import com.mcmanuel.domain.staff.non_teaching.NonTeachingDto;
import com.mcmanuel.domain.staff.non_teaching.NonTeachingRepository;
import com.mcmanuel.domain.token.TokenService;
import com.mcmanuel.email.EmailService;
import com.mcmanuel.exception.StaffNotFoundException;
import com.mcmanuel.pojo.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.MessagingException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public abstract class StaffServiceImpl<T> implements StaffService<T>{
    private final JwtService jwtService;
    private final AuthenticationManager manager;
    private final EmailService emailService;
    private final TokenService tokenService;
    private final LecturerRepository lecturerRepo;
    private final NonTeachingRepository nonTeachingRepo;


    @Override
    public String login(LoginRequest loginRequest){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getStaffId(),loginRequest.getPassword());
        Authentication authentication =manager.authenticate(token);
        if(authentication.isAuthenticated()) {
            List<String> roles;
            if (loginRequest.getStaffId().startsWith("NTS")){
                roles = ((NonTeachingDto)findStaffByStaffId(loginRequest.getStaffId())).roles().stream().map(Enum::name).toList();
            }
            else
               roles = ((LecturerDto)findStaffByStaffId(loginRequest.getStaffId())).roles().stream().map(Enum::name).toList();

            String jwtToken = jwtService.generateToken(loginRequest.getStaffId(),roles);
            System.out.println("JWT Token :"+jwtToken);
            return jwtToken;
        }
        else throw new RuntimeException("Invalid Login request");
    }

//    protected abstract T findStaff(String staffId);
//
//    @Override
//    public T findStaffByStaffId(String staffId) {
//        Optional<Staff> optionalStaff = staffId.substring(0,3).equalsIgnoreCase("NTS")? nonTeachingRepo.findByStaffId(staffId): lecturerRepo.findByStaffId(staffId);
//        if (optionalStaff.isEmpty()) {
//            throw new StaffNotFoundException("Lecturer Not Found");
//        }
//        return  findStaff(optionalStaff.get().getStaffId());
//    }


    public Staff findStaff(String staffId){
        Optional<Staff> optionalStaff = staffId.startsWith("NTS")? lecturerRepo.findByStaffId(staffId): nonTeachingRepo.findByStaffId(staffId);

        if ( optionalStaff.isEmpty()) {
            throw new StaffNotFoundException("Staff with Id "+staffId+ "Not found");
        }
        return optionalStaff.get();
    }

    @Override
    public void sendUserEmail(String email) throws MessagingException, jakarta.mail.MessagingException{
        emailService.sendEmail(email,tokenService.generateToken(email).getToken());
    }

    @Override
    public boolean verifyOtp(String email,String otp){
        return tokenService.verifyOtp(email,otp);
    }
}


package com.mcmanuel.domain.staff.non_teaching;

import com.mcmanuel.domain.staff.StaffService;
import com.mcmanuel.pojo.NonTeachingRegisterRequest;
import com.mcmanuel.pojo.RegisterRequest;
import org.springframework.messaging.MessagingException;

import java.util.List;

public interface NonTeachingService extends StaffService<NonTeachingDto,NonTeachingRegisterRequest> {

    @Override
    NonTeachingDto registerStaff(String email, NonTeachingRegisterRequest request);

    @Override
    NonTeachingDto findStaffByStaffNumber(String staffNumber);

    @Override
    NonTeachingDto findStaffByEmail(String email);

    @Override
    List<NonTeachingDto> getAllStaffs(int pageNo, int pageSize);

    @Override
    NonTeachingDto updateBio(String staffNumber, String email, String phoneNumber);

    @Override
    boolean deleteStaff(String staffNumber);

    @Override
    void sendUserEmail(String email) throws MessagingException, jakarta.mail.MessagingException;

    @Override
    boolean verifyOtp(String email,String otp);

    @Override
    List<String> getStaffList(String department);


}


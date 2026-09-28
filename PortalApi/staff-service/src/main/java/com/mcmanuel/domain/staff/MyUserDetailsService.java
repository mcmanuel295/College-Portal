package com.mcmanuel.domain.staff;

import com.mcmanuel.exception.LecturerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService{
    private final StaffRepository staffRepo;


    @Override
    public UserDetails loadUserByUsername(String staffNumber) throws UsernameNotFoundException {
        Staff staff = staffRepo.findByStaffNumber(staffNumber).orElseThrow(()-> new LecturerNotFoundException("Lecturer Not Found "+staffNumber));

        return new MyUserDetails(staff);
    }
}

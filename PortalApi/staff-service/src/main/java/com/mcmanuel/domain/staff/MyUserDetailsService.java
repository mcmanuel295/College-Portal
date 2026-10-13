package com.mcmanuel.domain.staff;

import com.mcmanuel.domain.staff.lecturer.LecturerRepository;
import com.mcmanuel.domain.staff.non_teaching.NonTeachingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService{
    private final LecturerRepository lecturerRepo;
    private final NonTeachingRepository nonTeachingRepo;

    @Override
    public UserDetails loadUserByUsername(String staffNumber) throws UsernameNotFoundException {
        Staff staff = lecturerRepo.findByStaffNumber(staffNumber)
                .or(() -> nonTeachingRepo.findByStaffNumber(staffNumber))
                .orElseThrow(()-> new UsernameNotFoundException("Staff Number Not Found "+staffNumber));

        return new MyUserDetails(staff);
    }
}

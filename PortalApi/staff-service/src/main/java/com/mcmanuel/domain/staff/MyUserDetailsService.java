package com.mcmanuel.domain.staff;

import com.mcmanuel.domain.staff.lecturer.LecturerRepository;
import com.mcmanuel.domain.staff.non_teaching.NonTeachingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService{
    private final LecturerRepository lecturerRepo;
    private final NonTeachingRepository nonTeachingRepo;

    @Override
    public UserDetails loadUserByUsername(String staffNumber) throws UsernameNotFoundException {
        Optional<Staff> staff = staffNumber.substring(0,3).equalsIgnoreCase("NTS")? nonTeachingRepo.findByStaffNumber(staffNumber): lecturerRepo.findByStaffNumber(staffNumber);

        if (staff.isEmpty()) {
            throw new UsernameNotFoundException("Staff Number Not Found "+staffNumber);
        }

        return new MyUserDetails(staff.get());
    }
}

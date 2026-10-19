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
    public UserDetails loadUserByUsername(String staffId) throws UsernameNotFoundException {
        Optional<Staff> staff = staffId.substring(0,3).equalsIgnoreCase("NTS")? nonTeachingRepo.findByStaffId(staffId): lecturerRepo.findByStaffId(staffId);

        if (staff.isEmpty()) {
            throw new UsernameNotFoundException("Staff with Id Not Found "+staffId);
        }

        return new MyUserDetails(staff.get());
    }
}

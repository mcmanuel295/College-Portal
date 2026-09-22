package com.mcmanuel.domain.lecturer;

import com.mcmanuel.exception.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService{
    private final LecturerRepository lecturerRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Lecturer lecturer = lecturerRrepo.findByEmail(username).orElseThrow(()-> new StudentNotFoundException("Lecturer Not FOund "+username));

        return new MyUserDetails(lecturer);
    }
}

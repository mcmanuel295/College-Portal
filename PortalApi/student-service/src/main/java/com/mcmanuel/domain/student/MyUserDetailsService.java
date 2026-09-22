package com.mcmanuel.domain.student;

import com.mcmanuel.exception.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService{
    private final StudentRepository studentRepo;

    @Override
    public UserDetails loadUserByUsername(String matriculationNumber) throws UsernameNotFoundException {
        Student student = studentRepo.findByMatriculationNumber(matriculationNumber).orElseThrow(()-> new StudentNotFoundException("Student Not Found "+matriculationNumber));

        return new MyUserDetails(student);
    }
}

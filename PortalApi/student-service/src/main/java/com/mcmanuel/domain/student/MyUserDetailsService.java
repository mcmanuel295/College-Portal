package com.mcmanuel.domain.student;

import com.mcmanuel.client.StaffClient;
import com.mcmanuel.exception.StudentNotFoundException;
import com.mcmanuel.pojo.Staff;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService{
    private final StudentRepository studentRepo;
    private final StaffClient staffClient;

    @Override
    public UserDetails loadUserByUsername(String matriculationNumber) throws UsernameNotFoundException {

        if(Integer.parseInt(matriculationNumber.substring(3)) ){
            Student student = studentRepo.findByMatriculationNumber(matriculationNumber).orElseThrow(()-> new StudentNotFoundException("Student Not Found "+matriculationNumber));
            return new MyUserDetails(student);
        }
        Staff staff =staffClient.findStaffByStaffId(matriculationNumber);
        if (staff !=null){
            return new MyUserDetails(staff);
        }

    }
}

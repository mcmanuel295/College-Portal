package com.mcmanuel.domain.student;

import com.mcmanuel.client.StaffClient;
import com.mcmanuel.exception.StudentNotFoundException;
import com.mcmanuel.pojo.Staff;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService{
    private final StudentRepository studentRepo;
    private final StaffClient staffClient;

    @NotNull
    @Override
    public UserDetails loadUserByUsername(String matriculationNumber) throws UsernameNotFoundException {
        try{
            Integer.parseInt(matriculationNumber.substring(3));
            Student student = studentRepo.findByMatriculationNumber(matriculationNumber).orElseThrow(()-> new StudentNotFoundException("Student Not Found "+matriculationNumber));
            return new MyUserDetails(student);
        }
        catch (NumberFormatException ex){

        Staff staff =  staffClient.findStaffClient(matriculationNumber);
        if (staff !=null){
            return new UserDetails() {
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    return staff.getRoles().stream()
                            .map(role ->  new SimpleGrantedAuthority("ROLE_"+role))
                            .toList();
                }

                @Override
                public @Nullable String getPassword() {
                    return staff.getStaffId();
                }

                @Override
                public String getUsername() {
                    return staff.getStaffId();
                }
            };
        }
        throw UsernameNotFoundException.fromUsername("Staff Not Found");
        }
    }
}


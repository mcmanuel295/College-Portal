package com.mcmanuel.web;

import com.mcmanuel.domain.staff.Staff;
import com.mcmanuel.domain.staff.StaffServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/staffs")
@RequiredArgsConstructor
public abstract class StaffControllerImpl<T> {
    private final StaffServiceImpl<T> service;


    @PreAuthorize("hasAnyRole('ADMIN','LECTURER')")
    @Operation(description = "Find Staff By StaffId Endpoint", summary = "Find Staff By StaffId")
    @GetMapping("/{staffId}")
    public ResponseEntity<Staff> findStaffClient(@PathVariable String staffId) {
        Staff staff = service.findStaff(staffId);

        if (staff != null) {
            return new ResponseEntity<>(staff, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}



//    @PreAuthorize("hasRole('ADMIN')")
//    @Operation(description = "Find  All Staff Endpoint",summary = "Find all staff")
//    @Override
//    @GetMapping("/")
//    public ResponseEntity<List<T>> findAllStaffs(@RequestParam(required = false,defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10",required = false) int pageSize){
//        return new ResponseEntity<>( service.getAllStaffs(pageNo,pageSize), HttpStatus.OK);
//    }



//
//    @PreAuthorize("hasAnyRole('ADMIN')")
//    @Operation(description = "Make an Admin",summary ="Make an Admin" )
//    @Override
//    @PostMapping("/{staffId}/make-admin")
//    public ResponseEntity<String> makeAdmin(@PathVariable String staffId){
//        String admin =service.makeAdmin(staffId);
//
//        if(admin!=null){
//            return new ResponseEntity<>(admin, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @PreAuthorize("hasAnyRole('ADMIN','LECTURER')")
//    @Operation(description = "Find Staff By StaffId Endpoint",summary ="Find Staff By StaffId" )
//    @Override
//    @PostMapping("/{staffId}/remove-admin")
//    public ResponseEntity<String> removeAdmin(@PathVariable String staffId){
//        String admin =service.removeAdmin(staffId);
//
//        if(admin!=null){
//            return new ResponseEntity<>(admin, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }



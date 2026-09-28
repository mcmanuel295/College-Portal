package com.mcmanuel.domain.admin;

import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Override
    public boolean openPortal() {
        return false;
    }

    @Override
    public boolean setDepartmentalCourses() {
        return false;
    }
}

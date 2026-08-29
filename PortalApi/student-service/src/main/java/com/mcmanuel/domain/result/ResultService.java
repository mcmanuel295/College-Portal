package com.mcmanuel.domain.result;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultService {
    private final ResultRepository resultRepo;

    public List<Result> getAllResults(){
        return resultRepo.findAll();
    }
}

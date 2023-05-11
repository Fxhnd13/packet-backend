package com.example.routeservice.service;

import com.example.routeservice.repository.PathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PathService {

    @Autowired
    private PathRepository pathRepository;

}

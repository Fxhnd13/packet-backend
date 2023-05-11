package com.example.routeservice.service;

import com.example.routeservice.repository.EdgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EdgeService {

    @Autowired
    private EdgeRepository edgeRepository;

}

package com.example.notificationservice.service;

import com.example.notificationservice.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediaService {

    @Autowired
    private MediaRepository mediaRepository;
}

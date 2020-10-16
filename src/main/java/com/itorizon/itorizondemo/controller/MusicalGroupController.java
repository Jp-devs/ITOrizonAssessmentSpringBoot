package com.itorizon.itorizondemo.controller;

import com.itorizon.itorizondemo.dto.ResponseDto;
import com.itorizon.itorizondemo.dto.SearchRequestDto;
import com.itorizon.itorizondemo.model.MusicalGroup;
import com.itorizon.itorizondemo.service.MusicalGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MusicalGroupController {

    @Autowired
    private MusicalGroupService musicalGroupService;

    @GetMapping({"/hello"})
    private String printHelloWorld() {
        return "Hello world!";
    }

    @PostMapping({"/upload-file"})
    private ResponseEntity<ResponseDto<String>> uploadFile(@RequestParam MultipartFile file) {
        return musicalGroupService.uploadFileAndStoreData(file);
    }

    @PostMapping({"/search"})
    private ResponseEntity<ResponseDto<String[]>> searchMusicalGroup(@RequestBody SearchRequestDto searchRequest) {
        return musicalGroupService.searchAndFetchMusicalGroup(searchRequest);
    }

}

package com.itorizon.itorizondemo.service;

import com.itorizon.itorizondemo.dto.ResponseDto;
import com.itorizon.itorizondemo.dto.SearchRequestDto;
import com.itorizon.itorizondemo.model.MusicalGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface MusicalGroupService {

    ResponseEntity<ResponseDto<String>> uploadFileAndStoreData(MultipartFile file);

    ResponseEntity<ResponseDto<String[]>> searchAndFetchMusicalGroup(SearchRequestDto searchRequest);
}

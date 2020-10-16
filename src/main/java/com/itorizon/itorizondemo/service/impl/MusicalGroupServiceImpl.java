package com.itorizon.itorizondemo.service.impl;

import com.itorizon.itorizondemo.dao.MusicalGroupRepository;
import com.itorizon.itorizondemo.dto.ResponseDto;
import com.itorizon.itorizondemo.dto.SearchRequestDto;
import com.itorizon.itorizondemo.model.MusicalGroup;
import com.itorizon.itorizondemo.service.MusicalGroupService;
import com.itorizon.itorizondemo.util.TsvFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MusicalGroupServiceImpl implements MusicalGroupService {

    @Autowired
    private TsvFileUtil tsvFileUtil;

    @Autowired
    private MusicalGroupRepository musicalGroupRepository;

    @Override
    public ResponseEntity<ResponseDto<String>> uploadFileAndStoreData(MultipartFile file) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        String fileName = null;
        String fileExtension = null;

        if (file == null || file.isEmpty()) {
            responseDto.setStatus(HttpStatus.BAD_REQUEST.value());
            responseDto.setMessage("File is mandatory");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        }

        fileName = file.getOriginalFilename();
        if (fileName == null || StringUtils.isEmpty(fileName)) {
            responseDto.setStatus(HttpStatus.BAD_REQUEST.value());
            responseDto.setMessage("Unable to process file");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        }

        String splittedFileName[] = fileName.split("\\.");
        System.out.println(fileName);
        if (splittedFileName.length < 2) {
            responseDto.setStatus(HttpStatus.BAD_REQUEST.value());
            responseDto.setMessage("Invalid File Name");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        }

        fileExtension = splittedFileName[splittedFileName.length - 1];

        if (fileExtension == null || StringUtils.isEmpty(fileExtension) || !fileExtension.equalsIgnoreCase("tsv")) {
            responseDto.setStatus(HttpStatus.BAD_REQUEST.value());
            responseDto.setMessage("Invalid File Name");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        }
        try {

            List<MusicalGroup> musicalGroups = tsvFileUtil.readTsvFile(file);
            System.out.println("Size of all records :: " + musicalGroups.size());
            musicalGroupRepository.saveAll(musicalGroups);

            responseDto.setMessage("Successfully saved all data!");
            responseDto.setStatus(HttpStatus.OK.value());

            return ResponseEntity.status(HttpStatus.OK.value()).body(responseDto);

        } catch (Exception e) {

            responseDto.setMessage("Something went wrong while saving data!");
            responseDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);

        }

    }

    @Override
    public ResponseEntity<ResponseDto<String[]>> searchAndFetchMusicalGroup(SearchRequestDto searchRequest) {
        ResponseDto<String[]> responseDto = new ResponseDto<>();

        if (searchRequest == null) {
            responseDto.setMessage("Request cannot be empty");
            responseDto.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        }

        if (searchRequest.getSearchStr() == null || StringUtils.isEmpty(searchRequest.getSearchStr())) {
            responseDto.setMessage("Request cannot be empty");
            responseDto.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        }

        try {
            TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(searchRequest.getSearchStr());
            MusicalGroup[] musicalGroups = musicalGroupRepository.findAllBy(criteria);
            if (musicalGroups.length > 0) {
                List<String> valuesList = Arrays.asList(musicalGroups).stream().map(x -> x.getValue()).collect(Collectors.toList());
                responseDto.setBody(valuesList.toArray(new String[valuesList.size()]));
            } else {
                String values[] = {};
                responseDto.setBody(values);
            }
            responseDto.setStatus(HttpStatus.OK.value());
            responseDto.setMessage("Successfully fetched data");
            return ResponseEntity.ok(responseDto);

        } catch (Exception e) {
            e.printStackTrace();
            responseDto.setMessage("Something went wrong while fetching data!");
            responseDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);

        }
    }

}

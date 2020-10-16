package com.itorizon.itorizondemo.util;

import com.itorizon.itorizondemo.model.MusicalGroup;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class TsvFileUtil {

    public List<MusicalGroup> readTsvFile(MultipartFile file) {
        List<MusicalGroup> musicalGroups = new ArrayList<>();
        if (file != null) {
            try {
                InputStream is = file.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String headerRow = br.readLine();
                String line = null;
                while((line = br.readLine()) != null) {
                    String splittedValues[] = line.split("\\t");
                    if (splittedValues.length > 1) {
                        String key = splittedValues[0].trim();
                        String value = splittedValues[1].replaceAll("/m/", "").trim();
                        if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value)) {
                            MusicalGroup musicalGroup = new MusicalGroup();
                            musicalGroup.setKey(key);
                            musicalGroup.setValue(value);
                            musicalGroups.add(musicalGroup);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return musicalGroups;
    }

}

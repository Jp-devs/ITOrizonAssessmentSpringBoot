package com.itorizon.itorizondemo.dao;

import com.itorizon.itorizondemo.model.MusicalGroup;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicalGroupRepository extends MongoRepository<MusicalGroup, String> {

    MusicalGroup[] findAllByKey(String key);

    MusicalGroup[] findAllByKeyLike(String searchStr);

    MusicalGroup[] findAllBy(TextCriteria criteria);
}

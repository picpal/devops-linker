package com.picpal.framework.sample.repository;

import com.picpal.framework.sample.repository.model.SampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
    List<SampleEntity> findFirst2ByUsernameLikeOrderByIdDesc(String name);
}

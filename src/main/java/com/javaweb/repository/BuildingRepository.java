package com.javaweb.repository;

import com.javaweb.entity.BuildingEntity;

import java.util.List;

public interface BuildingRepository {
    void deleteByIdIn(List<Long> ids);

    int countByIdIn(List<Long> ids);

    BuildingEntity findById(Long id);

    void save(BuildingEntity building);
}
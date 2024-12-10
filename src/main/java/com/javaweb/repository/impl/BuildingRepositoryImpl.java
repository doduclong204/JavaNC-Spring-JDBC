package com.javaweb.repository.impl;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BuildingRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void deleteByIdIn(List<Long> ids) {
        String sql = "DELETE FROM building WHERE id IN (" +
                ids.stream().map(id -> "?").collect(Collectors.joining(", ")) + ")";
        jdbcTemplate.update(sql, ids.toArray());
    }

    @Override
    public int countByIdIn(List<Long> ids) {
        String sql = "SELECT COUNT(*) FROM building WHERE id IN (" +
                ids.stream().map(id -> "?").collect(Collectors.joining(", ")) + ")";
        return jdbcTemplate.queryForObject(sql, ids.toArray(), Integer.class);
    }

    @Override
    public BuildingEntity findById(Long id) {
        String sql = "SELECT * FROM building WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(BuildingEntity.class));
    }

    @Override
    public void save(BuildingEntity building) {
        String sql = "INSERT INTO building (id, name, ward, street, district, numberofbasement, managername, managerphone, floorarea, rentprice, " +
                "brokeragefee, servicefee, level, overtimefee, waterfee, electricityfee, deposit, payment, renttime, decorationtime, " +
                "rentpricedescription, carfee, motofee, structure, direction, note, type, avatar) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" +
                " ON DUPLICATE KEY UPDATE name = ?, ward = ?, street = ?, district = ?, numberofbasement = ?, managername = ?, managerphone = ?, " +
                "floorarea = ?, rentprice = ?, brokeragefee = ?, servicefee = ?, level = ?, overtimefee = ?, waterfee = ?, electricityfee = ?, " +
                "deposit = ?, payment = ?, renttime = ?, decorationtime = ?, rentpricedescription = ?, carfee = ?, motofee = ?, structure = ?, " +
                "direction = ?, note = ?, type = ?, avatar = ?";
        jdbcTemplate.update(sql,
                building.getId(),
                building.getName(),
                building.getWard(),
                building.getStreet(),
                building.getDistrict(),
                building.getNumberOfBasement(),
                building.getManagerName(),
                building.getManagerPhone(),
                building.getFloorArea(),
                building.getRentPrice(),
                building.getBrokerageFee(),
                building.getServiceFee(),
                building.getLevel(),
                building.getOvertimeFee(),
                building.getWaterFee(),
                building.getElectricityFee(),
                building.getDeposit(),
                building.getPayment(),
                building.getRentTime(),
                building.getDecorationTime(),
                building.getRentPriceDescription(),
                building.getCarFee(),
                building.getMotoFee(),
                building.getStructure(),
                building.getDirection(),
                building.getNote(),
                building.getTypeCode(),
                building.getAvatar(),
                building.getName(),
                building.getWard(),
                building.getStreet(),
                building.getDistrict(),
                building.getNumberOfBasement(),
                building.getManagerName(),
                building.getManagerPhone(),
                building.getFloorArea(),
                building.getRentPrice(),
                building.getBrokerageFee(),
                building.getServiceFee(),
                building.getLevel(),
                building.getOvertimeFee(),
                building.getWaterFee(),
                building.getElectricityFee(),
                building.getDeposit(),
                building.getPayment(),
                building.getRentTime(),
                building.getDecorationTime(),
                building.getRentPriceDescription(),
                building.getCarFee(),
                building.getMotoFee(),
                building.getStructure(),
                building.getDirection(),
                building.getNote(),
                building.getTypeCode(),
                building.getAvatar());
    }
}



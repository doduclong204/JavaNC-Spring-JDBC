package com.javaweb.converter;

import com.javaweb.entity.RoleEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.model.dto.UserDTO;
import com.javaweb.model.request.UserRequest;
import com.javaweb.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserConverter {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleRepository roleRepository;

    public UserDTO convertToDto(UserEntity entity) {
        UserDTO result = modelMapper.map(entity, UserDTO.class);
        return result;
    }

    public UserEntity convertToEntity(UserDTO dto) {
        UserEntity result = modelMapper.map(dto, UserEntity.class);
        return result;
    }

    public UserEntity convertToEntity(UserRequest request) {
        UserEntity user = modelMapper.map(request, UserEntity.class);

        RoleEntity role = roleRepository.findOneByCode(request.getRole());
        user.setRoles(Collections.singletonList(role));

        return user;
    }
}

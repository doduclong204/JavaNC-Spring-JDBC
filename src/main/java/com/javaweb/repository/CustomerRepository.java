package com.javaweb.repository;

import java.util.List;

public interface CustomerRepository {
    int countByIdIn(List<Long> ids);
}

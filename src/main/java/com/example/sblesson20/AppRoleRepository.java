package com.example.sblesson20;

import org.springframework.data.repository.CrudRepository;

public interface AppRoleRepository extends CrudRepository<AppRole,Long> {
    AppRole findByAppRole(String approle);
}

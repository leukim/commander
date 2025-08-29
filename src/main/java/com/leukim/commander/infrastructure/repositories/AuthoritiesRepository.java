package com.leukim.commander.infrastructure.repositories;

import com.leukim.commander.infrastructure.repositories.model.Authority;
import com.leukim.commander.infrastructure.repositories.model.AuthorityId;
import org.springframework.data.repository.CrudRepository;

public interface AuthoritiesRepository extends CrudRepository<Authority, AuthorityId> {
    boolean existsByAuthority(String authority);
}

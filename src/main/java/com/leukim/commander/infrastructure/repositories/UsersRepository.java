package com.leukim.commander.infrastructure.repositories;

import com.leukim.commander.infrastructure.repositories.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, String> {
}

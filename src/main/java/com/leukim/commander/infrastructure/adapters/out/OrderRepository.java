package com.leukim.commander.infrastructure.adapters.out;

import com.leukim.commander.infrastructure.adapters.out.model.DbOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OrderRepository extends CrudRepository<DbOrder, UUID> {
}

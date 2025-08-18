package com.leukim.commander.infrastructure.adapters.out;

import com.leukim.commander.infrastructure.adapters.out.model.DbOrder;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<DbOrder, UUID> {
}

package com.leukim.commander.infrastructure.adapters.out;

import com.leukim.commander.application.model.Order;
import com.leukim.commander.application.model.Product;
import com.leukim.commander.application.ports.in.model.CreateOrderDto;
import com.leukim.commander.application.ports.out.OrderPersistencePort;
import com.leukim.commander.infrastructure.adapters.out.model.DbOrder;
import com.leukim.commander.infrastructure.adapters.out.model.DbProduct;
import com.leukim.commander.infrastructure.adapters.out.model.DbProductQuantity;
import com.leukim.commander.infrastructure.mappers.OrderMapper;
import com.leukim.commander.infrastructure.mappers.ProductMapper;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;

@Service
public final class OrderPersistenceService implements OrderPersistencePort {
    private final OrderMapper orderMapper;
    private final ProductMapper productMapper;
    private final OrderRepository repository;
    private final ProductRepository productRepository;
    private final DbProductQuantityRepository productQuantityRepository;

    public OrderPersistenceService(OrderMapper orderMapper,
                                   ProductMapper productMapper,
                                   OrderRepository repository,
                                   ProductRepository productRepository,
                                   DbProductQuantityRepository productQuantityRepository) {
        this.orderMapper = orderMapper;
        this.productMapper = productMapper;
        this.repository = repository;
        this.productRepository = productRepository;
        this.productQuantityRepository = productQuantityRepository;
    }

    private List<Product> getAllProducts() {
        return StreamSupport.stream(productRepository.findAll().spliterator(),
                false)
            .map(productMapper::fromDbModel)
            .toList();
    }

    @Override
    public List<Order> getAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
            .map((DbOrder dbOrder) -> orderMapper.fromDbModel(dbOrder,
                getAllProducts()))
            .toList();
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return repository.findById(id).map(
            dbOrder -> orderMapper.fromDbModel(dbOrder, getAllProducts())
        );
    }

    private Order save(DbOrder dbOrder) {
        DbOrder savedOrder = repository.save(dbOrder);
        return orderMapper.fromDbModel(savedOrder, getAllProducts());
    }

    @Override
    public Order create(CreateOrderDto createOrderDto) {
        return save(orderMapper.create(createOrderDto));
    }

    @Override
    public List<Order> getByDate(java.time.LocalDate date) {
        return repository.findByDate(date).stream()
            .map(dbOrder -> orderMapper.fromDbModel(dbOrder, getAllProducts()))
            .toList();
    }

    @Override
    public Order addItem(UUID orderId, Product product, double quantity) {
        DbOrder dbOrder = repository.findById(orderId).orElseThrow();

        DbProduct dbProduct = productMapper.toDbModel(product);
        DbProductQuantity savedItem = productQuantityRepository.save(
            new DbProductQuantity(null, dbOrder, dbProduct, quantity));

        dbOrder.addItem(savedItem);

        return save(dbOrder);
    }

    @Override
    public Order removeItem(Order order, UUID productId) {
        productQuantityRepository.deleteByProductId(productId);
        return new Order(
            order.id(),
            order.name(),
            order.items().entrySet().stream()
                .filter(it -> !it.getKey().id().equals(productId))
                .collect(java.util.stream.Collectors.toMap(
                    Entry::getKey,
                    Entry::getValue
                )),
            order.picked(),
            order.date()
        );
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}

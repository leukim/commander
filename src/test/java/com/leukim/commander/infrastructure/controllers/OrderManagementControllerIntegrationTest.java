package com.leukim.commander.infrastructure.controllers;

import com.leukim.commander.application.model.Order;
import com.leukim.commander.application.model.Product;
import com.leukim.commander.application.ports.in.model.AddOrderItemDto;
import com.leukim.commander.application.ports.in.model.CreateOrderDto;
import com.leukim.commander.application.ports.out.OrderPersistencePort;
import com.leukim.commander.application.ports.out.ProductPersistencePort;
import com.leukim.commander.clients.OrderClient;
import com.leukim.commander.infrastructure.controllers.model.OrderDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.leukim.commander.assertions.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderManagementControllerIntegrationTest {
    private static final LocalDate TEST_DATE = LocalDate.of(2025, 8, 19);
    private static final Order ORDER_1 =
        new Order(null, "TestOrder", Map.of(), false, TEST_DATE);
    private static final Product PRODUCT_1 =
        new Product(null, "TestProduct", "TestDescription");

    private UUID orderId;

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private OrderPersistencePort persistencePort;

    @Autowired
    private ProductPersistencePort productPersistencePort;

    private UUID productId;

    @BeforeEach
    void setUp() {
        persistencePort.deleteAll();
        CreateOrderDto createOrderDto = new CreateOrderDto(ORDER_1.name(), TEST_DATE);
        Order save = persistencePort.create(createOrderDto);
        orderId = save.id();

        Product product = productPersistencePort.save(PRODUCT_1);
        productId = product.id();
    }

    @Test
    void getAllOrders_returnsOrderList() {
        List<OrderDto> orders = orderClient.getAll();

        assertThat(orders).hasSize(1);

        assertThat(orders.getFirst())
                .hasId(orderId)
                .hasName(ORDER_1.name())
                .hasNoItems()
                .isNotPicked();
    }

    @Test
    void createOrder_returnsCreatedOrder() {
        CreateOrderDto createOrderDto = new CreateOrderDto("OrderName", TEST_DATE);

        OrderDto response = orderClient.create(createOrderDto);

        assertThat(response)
            .isNotNull()
            .hasName("OrderName")
            .hasNoItems()
            .isNotPicked()
            .hasDate(TEST_DATE);
    }

    @Test
    void getOrderById_returnsOrder() {
        OrderDto response = orderClient.getById(orderId);

        assertThat(response)
            .isNotNull()
            .hasId(orderId)
            .hasName(ORDER_1.name())
            .hasNoItems()
            .isNotPicked();
    }

    @Test
    void getOrderById_notFound_returns404() {
        UUID randomId = UUID.randomUUID();
        try {
            orderClient.getById(randomId);
            throw new AssertionError("Expected 404 exception");
        } catch (feign.FeignException.NotFound ex) {
            assertThat(ex.getMessage()).contains(randomId.toString());
        }
    }

    @Test
    void deleteOrder_removesOrder() {
        orderClient.delete(orderId);
        try {
            orderClient.getById(orderId);
            throw new AssertionError("Expected 404 exception");
        } catch (feign.FeignException.NotFound ex) {
            assertThat(ex.getMessage()).contains(orderId.toString());
        }
    }

    @Test
    void deleteOrder_notFound_returns404() {
        UUID randomId = UUID.randomUUID();
        try {
            orderClient.delete(randomId);
            throw new AssertionError("Expected 404 exception");
        } catch (feign.FeignException.NotFound ex) {
            assertThat(ex.getMessage()).contains(randomId.toString());
        }
    }

    @Test
    void addItemToOrder_addsItemSuccessfully() {
        AddOrderItemDto itemDto = new AddOrderItemDto(productId, 2);
        OrderDto response = orderClient.addItem(orderId, itemDto);

        assertThat(response)
            .isNotNull()
            .hasItems(Map.of(productId, 2.0));
    }

    @Test
    void removeItemFromOrder_removesItemSuccessfully() {
        // First add an item
        AddOrderItemDto itemDto = new AddOrderItemDto(productId, 2);
        orderClient.addItem(orderId, itemDto);

        // Now remove it
        OrderDto response = orderClient.removeItem(orderId, productId);
        assertThat(response)
            .isNotNull()
            .hasNoItems();
    }

    @Test
    void getOrderByDate_returnsOrdersForDate() {
        List<OrderDto> orders = orderClient.getByDate(TEST_DATE.format(DateTimeFormatter.ISO_DATE));

        assertThat(orders)
            .isNotNull()
            .hasSize(1)
            .allSatisfy(order -> {
                assertThat(order).hasId(orderId)
                    .hasName(ORDER_1.name())
                    .hasNoItems()
                    .isNotPicked()
                    .hasDate(TEST_DATE);
            });
    }

    @Test
    void getOrderForADifferentDay_returnsNoOrders() {
        LocalDate testPlus1 = TEST_DATE.plusDays(1);

        List<OrderDto> orders = orderClient.getByDate(testPlus1.format(DateTimeFormatter.ISO_DATE));
        assertThat(orders)
            .isNotNull()
            .isEmpty();
    }
}

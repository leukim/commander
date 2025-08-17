package com.leukim.commander.infrastructure.controllers;

import com.leukim.commander.application.model.Order;
import com.leukim.commander.application.model.Product;
import com.leukim.commander.application.ports.in.model.CreateOrderDto;
import com.leukim.commander.application.ports.in.model.AddOrderItemDto;
import com.leukim.commander.application.ports.out.OrderPersistencePort;
import com.leukim.commander.application.ports.out.ProductPersistencePort;
import com.leukim.commander.infrastructure.controllers.model.OrderDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.UUID;

import static com.leukim.commander.assertions.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderManagementControllerIntegrationTest {
    private static final Order ORDER_1 = new Order(null, "TestOrder", Map.of(), false);
    private static final Product PRODUCT_1 = new Product(null, "TestProduct", "TestDescription");

    private UUID orderId;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private OrderPersistencePort persistencePort;

    @Autowired
    private ProductPersistencePort productPersistencePort;

    private UUID productId;

    @BeforeEach
    void setUp() {
        persistencePort.deleteAll();
        CreateOrderDto createOrderDto = new CreateOrderDto(ORDER_1.name());
        Order save = persistencePort.create(createOrderDto);
        orderId = save.id();

        Product product = productPersistencePort.save(PRODUCT_1);
        productId = product.id();
    }

    @Test
    void createOrder_returnsCreatedOrder() {
        CreateOrderDto createOrderDto = new CreateOrderDto("OrderName");
        ResponseEntity<OrderDto> response = restTemplate.postForEntity("/api/orders", createOrderDto, OrderDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(response.getBody())
            .isNotNull()
            .hasName("OrderName")
            .hasNoItems()
            .isNotPicked();
    }

    @Test
    void getAllOrders_returnsOrderList() {
        OrderDto[] orders = restTemplate.getForEntity("/api/orders", OrderDto[].class).getBody();
        assertThat(orders).isNotEmpty();

        assertThat(orders[0])
            .hasId(orderId)
            .hasName(ORDER_1.name())
            .hasNoItems()
            .isNotPicked();
    }

    @Test
    void getOrderById_returnsOrder() {
        ResponseEntity<OrderDto> response = restTemplate.getForEntity("/api/orders/" + orderId, OrderDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(response.getBody())
            .isNotNull()
            .hasId(orderId)
            .hasName(ORDER_1.name())
            .hasNoItems()
            .isNotPicked();
    }

    @Test
    void getOrderById_notFound_returns404() {
        UUID randomId = UUID.randomUUID();
        ResponseEntity<String> response = restTemplate.getForEntity("/api/orders/" + randomId, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains(randomId.toString());
    }

    @Test
    void deleteOrder_removesOrder() {
        restTemplate.delete("/api/orders/" + orderId);

        ResponseEntity<String> response = restTemplate.getForEntity("/api/orders/" + orderId, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains(orderId.toString());
    }

    @Test
    void deleteOrder_notFound_returns404() {
        UUID randomId = UUID.randomUUID();
        ResponseEntity<String> response = restTemplate.exchange("/api/orders/" + randomId, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains(randomId.toString());
    }

    @Test
    void addItemToOrder_addsItemSuccessfully() {
        AddOrderItemDto itemDto = new AddOrderItemDto(productId, 2);
        ResponseEntity<OrderDto> response = restTemplate.postForEntity("/api/orders/" + orderId + "/items", itemDto, OrderDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
            .isNotNull()
            .hasItems(Map.of(productId, 2.0));
    }

    @Test
    void removeItemFromOrder_removesItemSuccessfully() {
        // First add an item
        AddOrderItemDto itemDto = new AddOrderItemDto(productId, 2);
        restTemplate.postForEntity("/api/orders/" + orderId + "/items", itemDto, OrderDto.class);
        // Now remove it
        ResponseEntity<OrderDto> response = restTemplate.exchange("/api/orders/" + orderId + "/items/" + productId, HttpMethod.DELETE, HttpEntity.EMPTY, OrderDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
            .isNotNull()
            .hasNoItems();
    }
}

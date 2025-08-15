package com.leukim.commander.infrastructure.controllers;

import com.leukim.commander.application.model.Product;
import com.leukim.commander.application.ports.in.model.CreateProductDto;
import com.leukim.commander.application.ports.out.ProductsPersistencePort;
import com.leukim.commander.infrastructure.controllers.model.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductManagementControllerIntegrationTest {

    private static final Product PRODUCT_1 = new Product(null, "TestName", "TestDescription");
    private UUID productId;

    @Autowired
    private ProductsPersistencePort productsPersistencePort;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        productsPersistencePort.deleteAll();
        productsPersistencePort.save(PRODUCT_1);

        this.productId = productsPersistencePort.getAll().getFirst().id();
    }

    @Test
    void getAllProducts_returnsProductList_fromPersistenceLayer() {
        ProductDto[] products = restTemplate.getForEntity("/api/products", ProductDto[].class).getBody();

        assertThat(products).hasSize(1);
        assertThat(products[0].id()).isEqualTo(productId);
        assertThat(products[0].name()).isEqualTo(PRODUCT_1.name());
        assertThat(products[0].description()).isEqualTo(PRODUCT_1.description());
    }

    @Test
    void getProductById_returnsProduct() {
        ResponseEntity<ProductDto> response = restTemplate.getForEntity("/api/products/" + productId, ProductDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        ProductDto product = response.getBody();
        assertThat(product).isNotNull();
        assertThat(product.id()).isEqualTo(productId);
        assertThat(product.name()).isEqualTo(PRODUCT_1.name());
        assertThat(product.description()).isEqualTo(PRODUCT_1.description());
    }

    @Test
    void createProduct_returnsCreatedProduct() {
        CreateProductDto createProductDto = new CreateProductDto("NewProduct", "NewDescription");
        ResponseEntity<ProductDto> response = restTemplate.postForEntity("/api/products", createProductDto, ProductDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        ProductDto product = response.getBody();
        assertThat(product).isNotNull();
        assertThat(product.id()).isNotNull();
        assertThat(product.name()).isEqualTo("NewProduct");
        assertThat(product.description()).isEqualTo("NewDescription");
    }

    @Test
    void getProductById_notFound_returns404() {
        UUID randomId = UUID.randomUUID();
        ResponseEntity<String> response = restTemplate.getForEntity("/api/products/" + randomId, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains(randomId.toString());
    }

    @Test
    void deleteProduct_removesProduct() {
        restTemplate.delete("/api/products/" + productId);

        ResponseEntity<String> response = restTemplate.getForEntity("/api/products/" + productId, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}

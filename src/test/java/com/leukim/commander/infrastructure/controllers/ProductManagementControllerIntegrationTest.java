package com.leukim.commander.infrastructure.controllers;

import com.leukim.commander.application.model.Product;
import com.leukim.commander.application.ports.in.model.CreateProductDto;
import com.leukim.commander.application.ports.out.ProductPersistencePort;
import com.leukim.commander.clients.ProductClient;
import com.leukim.commander.infrastructure.controllers.model.ProductDto;
import feign.FeignException;
import java.io.IOException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

import static com.leukim.commander.assertions.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductManagementControllerIntegrationTest {

    private static final Product PRODUCT_1 =
        new Product(null, "TestName", "TestDescription");
    private UUID productId;

    @Autowired
    private ProductPersistencePort productPersistencePort;

    @Autowired
    private ProductClient productClient;

    @BeforeEach
    void setUp() {
        productPersistencePort.deleteAll();
        Product save = productPersistencePort.save(PRODUCT_1);

        this.productId = save.id();
    }

    @Test
    void getAllProducts_returnsProductList_fromPersistenceLayer() {
        List<ProductDto> products = productClient.getAll();

        assertThat(products).hasSize(1);

        assertThat(products.getFirst())
            .hasName(PRODUCT_1.name())
            .hasDescription(PRODUCT_1.description());
    }

    @Test
    void createProduct_returnsCreatedProduct() {
        CreateProductDto createProductDto =
            new CreateProductDto("NewProduct", "NewDescription");
        ProductDto response = productClient.create(createProductDto);

        assertThat(response)
            .hasName("NewProduct")
            .hasDescription("NewDescription");
    }

    @Test
    void getProductById_returnsProduct() {
        ProductDto response = productClient.getById(productId);

        assertThat(response)
            .hasId(productId)
            .hasName(PRODUCT_1.name())
            .hasDescription(PRODUCT_1.description());
    }

    @Test
    void getProductById_notFound_returns404() {
        UUID randomId = UUID.randomUUID();
        try {
            productClient.getById(randomId);
            throw new AssertionError("Expecting NotFoundException");
        } catch (FeignException.NotFound e) {
            assertThat(e.status()).isEqualTo(HttpStatus.NOT_FOUND.value());
            assertThat(e.getMessage()).contains(randomId.toString());
        }
    }

    @Test
    void deleteProduct_removesProduct() {
        productClient.delete(productId);

        try {
            productClient.getById(productId);
            throw new AssertionError("Expecting NotFoundException");
        } catch (FeignException.NotFound e) {
            assertThat(e.status()).isEqualTo(HttpStatus.NOT_FOUND.value());
            assertThat(e.getMessage()).contains(productId.toString());
        }
    }

    @Test
    void deleteProduct_notFound_returns404() {
        UUID randomId = UUID.randomUUID();
        try {
            productClient.delete(randomId);
            throw new AssertionError("Expecting NotFoundException");
        } catch (FeignException.NotFound e) {
            assertThat(e.status()).isEqualTo(HttpStatus.NOT_FOUND.value());
            assertThat(e.getMessage()).contains(randomId.toString());
        }
    }

    @Test
    void exportProducts_generatesCSVFile() throws IOException {
        String export = productClient.export();

        CSVParser parser = CSVParser.parse(export, CSVFormat.Builder.create().setHeader("name", "description").get());

        List<CSVRecord> records = parser.getRecords();
        assertThat(records).hasSize(2);
        assertThat(records.get(1).get("name")).isEqualTo(PRODUCT_1.name());
        assertThat(records.get(1).get("description")).isEqualTo(PRODUCT_1.description());
    }
}

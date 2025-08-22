package com.leukim.commander.infrastructure.export;

import com.leukim.commander.application.model.Product;
import com.leukim.commander.application.ports.in.model.CreateProductDto;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

public final class CSVUtils {
    private CSVUtils() {
    }

    public static InputStreamResource createProductsCSVFile(List<Product> products) {
        String[] csvHeader = {"name", "description"};

        List<List<String>> csvBody = products.stream()
            .map(product -> List.of(product.name(), product.description()))
            .toList();

        ByteArrayInputStream byteArrayInputStream;

        try (
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            CSVPrinter csvPrinter = new CSVPrinter(
                new PrintWriter(out),
                CSVFormat.Builder.create().setHeader(csvHeader).get()
            )
        ) {
            for (List<String> strings : csvBody) {
                csvPrinter.printRecord(strings);
            }
            csvPrinter.flush();

            byteArrayInputStream = new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return new InputStreamResource(byteArrayInputStream);
    }

    public static List<CreateProductDto> parseCsvToCreateProductDto(Resource resource) {
        List<CreateProductDto> productsToCreate;
        try (
            InputStreamReader fileReader = new InputStreamReader(resource.getInputStream())
        ) {
            CSVFormat csvFormat = CSVFormat.Builder.create().setHeader("name", "description").get();

            productsToCreate = CSVParser.parse(fileReader, csvFormat).stream()
                .skip(1)
                .map(record -> new CreateProductDto(record.get("name"), record.get("description")))
                .toList();


        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return productsToCreate;
    }
}

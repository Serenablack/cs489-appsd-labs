package com.example.lab1;

import com.example.lab1.edu.miu.cs.cs489appsd.lab1a.productmgmtapp.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class Lab1Application {

    public static void main(String[] args) throws IOException {
        // Create an array of Product objects
        Product[] products = {
                new Product(3128874119L, "Banana", LocalDate.parse("2023-01-24"), 124, 0.55),
                new Product(2927458265L, "Apple", LocalDate.parse("2022-12-09"), 18, 1.09),
                new Product(9189927460L, "Carrot", LocalDate.parse("2023-03-31"), 89, 2.99)
        };

        List<Product> sortedProducts = Arrays.stream(products)
                .sorted(Comparator.comparing(Product::getUnitPrice).reversed())
                .collect(Collectors.toList());

        printJSON(sortedProducts);

        printXML(sortedProducts);

        printCSV(sortedProducts);
    }

    private static void printJSON(List<Product> products) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String jsonOutput = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(products);
        System.out.println("JSON Output:\n" + jsonOutput);
    }

    private static void printXML(List<Product> products) throws IOException {
        JacksonXmlModule xmlModule = new JacksonXmlModule();
        xmlModule.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(xmlModule);

        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String xmlOutput = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(products);
        System.out.println("-------XML Output:---------\n" + xmlOutput);
    }

    private static void printCSV(List<Product> products) {
        System.out.println("CSV Output:");
        System.out.println("ProductId, Name, DateSupplied, QuantityInStock, UnitPrice");
        for (Product p : products) {
            System.out.printf("%d, %s, %s, %d, %.2f%n",
                    p.getProductId(), p.getName(), p.getDateSupplied(), p.getQuantityInStock(), p.getUnitPrice());
        }
    }

}

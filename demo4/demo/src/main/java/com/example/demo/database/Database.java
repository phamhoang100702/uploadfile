//package com.example.demo.database;
//
//import com.example.demo.model.Product;
//import com.example.demo.repositories.ProductRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//public class Database {
//    private static final Logger logger = LoggerFactory.getLogger(Database.class);
//    @Bean
//    CommandLineRunner initDatabase(ProductRepository productRepositories) {
//        return new CommandLineRunner() {
//            @Override
//            public void run(String... args) throws Exception {
//                Product product = new Product("IPHONE 12", 2020, 2700.0, "");
//                Product product1 = new Product("IPHONE 13", 2020, 3000.0, "");
//                Product product2 = new Product("IPHONE 14", 2022, 3300.0, "");
//                logger.info("insert data : " + productRepositories.save(product));
//                logger.info("insert data : " + productRepositories.save(product1));
//                logger.info("insert data : " + productRepositories.save(product2));
//            }
//        };
//    }
//}

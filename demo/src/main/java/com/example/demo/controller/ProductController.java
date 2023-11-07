package com.example.demo.controller;


import com.example.demo.model.Product;
import com.example.demo.model.ResponseObject;
import com.example.demo.service.ProductService;
import com.example.demo.serviceI.IStorageService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/product")
public class ProductController {

    @Autowired
    private ProductService service;


    @GetMapping("")
        // this request is : http://localhost:8000/api/v1/Products
    List<Product> getAllProducts() {
        return service.findAllProduct();//where is data?;
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable int id){
        List<Product> foundProduct = service.findByManufacturedYear(id);
        if(!foundProduct.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok","Query product successfully",foundProduct)
            );
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Not found","Can not find product with id = " +id,"")
            );
        }
    }
    @PostMapping("")
    public Product addProduct(@RequestBody Product product) {
        service.save(product);
        return product;
    }


}

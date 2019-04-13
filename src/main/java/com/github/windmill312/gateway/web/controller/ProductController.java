package com.github.windmill312.gateway.web.controller;

import com.github.windmill312.gateway.service.ProductService;
import com.github.windmill312.gateway.web.to.common.PagedResult;
import com.github.windmill312.gateway.web.to.in.AddProductRequest;
import com.github.windmill312.gateway.web.to.in.LinkProductAndCafeRequest;
import com.github.windmill312.gateway.web.to.in.UnlinkProductAndCafeRequest;
import com.github.windmill312.gateway.web.to.in.UpdateProductRequest;
import com.github.windmill312.gateway.web.to.out.AddProductInfo;
import com.github.windmill312.gateway.web.to.out.ProductInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/product", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<PagedResult<ProductInfo>> getProducts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        logger.debug("Getting all products for page: {} and page size: {}", page, size);

        return ResponseEntity.ok(productService.getAllProducts(page, size));

    }

    @GetMapping(path = "/cafe/{uuid}")
    public ResponseEntity<List<ProductInfo>> getProductsByCafe(
            @PathVariable UUID uuid) {
        logger.debug("Getting all products by cafe: {}", uuid);

        return ResponseEntity.ok(productService.getProductsByCafe(uuid));
    }

    @GetMapping(path = "/{uuid}")
    public ResponseEntity<ProductInfo> getProductInfo(@PathVariable UUID uuid) {
        logger.debug("Getting product info by uid: {}", uuid);

        return ResponseEntity.ok(productService.getProductByUid(uuid));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AddProductInfo> addProduct(@RequestBody @Valid AddProductRequest request) {
        logger.debug("Adding product with name: {}", request.getName());

        return ResponseEntity.ok(new AddProductInfo(productService.addProduct(request)));
    }

    @PostMapping(path = "/link", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> linkProductAndCafe(@RequestBody @Valid LinkProductAndCafeRequest request) {
        logger.debug("Linking product with uid: {} and cafe with uid: {}", request.getProductUid(), request.getCafeUid());

        productService.linkCafeAndProduct(request.getCafeUid(), request.getProductUid());
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/unlink", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> unlinkProductAndCafe(@RequestBody @Valid UnlinkProductAndCafeRequest request) {
        logger.debug("Unlinking product with uid: {} and cafe with uid: {}", request.getProductUid(), request.getCafeUid());

        productService.unlinkCafeAndProduct(request.getCafeUid(), request.getProductUid());
        return ResponseEntity.noContent().build();
    }


    @PatchMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> updateProduct(@RequestBody @Valid UpdateProductRequest request) {
        logger.debug("Updating product with name: {}", request.getName());

        productService.updateProduct(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{uuid}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID uuid) {
        logger.debug("Deleting product with uid: {}", uuid);

        productService.removeProduct(uuid);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/cafe/{uuid}")
    public ResponseEntity<Void> deleteProductsByCafe(@PathVariable UUID uuid) {
        logger.debug("Deleting products by cafe with uid: {}", uuid);

        productService.removeAllProductsByCafe(uuid);
        return ResponseEntity.noContent().build();
    }

}

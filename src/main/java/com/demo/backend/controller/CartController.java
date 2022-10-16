package com.demo.backend.controller;

import com.demo.backend.dto.AddToCartDto;
import com.demo.backend.dto.ApiResponseDto;
import com.demo.backend.dto.CartDto;
import com.demo.backend.exception.CustomValidationException;
import com.demo.backend.model.Product;
import com.demo.backend.model.User;
import com.demo.backend.service.CartService;
import com.demo.backend.service.ProductService;
import com.demo.backend.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.xml.bind.ValidationException;


@Api(value="APIs for Cart Service")
@RestController
@RequestMapping(value="/cart",produces ={"application/json"})
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;


    @ApiOperation(value = "Add Items to Cart",response = ApiResponseDto.class)
    @PostMapping("/")
    public ResponseEntity<ApiResponseDto> addToCart(@Valid @RequestBody AddToCartDto addToCartDto, @NotBlank  @RequestParam Integer userId) throws ValidationException {
        User user = userService.getUserById(userId);
        Product product = productService.getProductById(addToCartDto.getProductId());
        String error = cartService.validateRequest(addToCartDto,product,user);
        if(!error.isBlank()){
            throw new CustomValidationException(error);
        }
        cartService.addToCart(addToCartDto, product, user);
        int stock=product.getStock()-addToCartDto.getQuantity();
        product.setStock(stock);
        productService.updateProduct(product);
        return new ResponseEntity<ApiResponseDto>(new ApiResponseDto(true, "Added to cart"), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("userId") int userId) {
        User user = userService.getUserById(userId);
        CartDto cartDto = cartService.listCartItems(user);
        return new ResponseEntity<CartDto>(cartDto,HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<ApiResponseDto> updateCartItem(@Valid @RequestBody AddToCartDto cartDto,
                                                     @NotBlank @RequestParam("userId") int userId) {
        User user = userService.getUserById(userId);
        Product product = productService.getProductById(cartDto.getProductId());
        String error = cartService.validateRequest(cartDto,product,user);
        if(!error.isBlank()){
            throw new CustomValidationException(error);
        }
        int currentStock= cartService.getCartItemQuantityById(cartDto.getId());
        int stock = product.getStock();
        stock = stock+currentStock-cartDto.getQuantity();
        cartService.updateCartItem(cartDto, user,product);
        product.setStock(stock);
        productService.saveProduct(product);
        return new ResponseEntity<ApiResponseDto>(new ApiResponseDto(true, "Product has been updated"), HttpStatus.OK);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponseDto> deleteCartItem(@PathVariable("cartItemId") int cartItemId,@RequestParam("userId") int userId)  {
        User user = userService.getUserById(userId);
        cartService.deleteCartItem(cartItemId,user);
        return new ResponseEntity<ApiResponseDto>(new ApiResponseDto(true, "Item has been removed"), HttpStatus.OK);
    }


}

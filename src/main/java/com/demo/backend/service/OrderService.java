package com.demo.backend.service;

import com.demo.backend.dto.CartDto;
import com.demo.backend.dto.CartItemDto;
import com.demo.backend.model.Order;
import com.demo.backend.model.OrderItem;
import com.demo.backend.model.User;
import com.demo.backend.repository.OrderItemRepository;
import com.demo.backend.repository.OrderRepository;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderService {
    @Autowired
    private CartService cartService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    public void placeOrder(User user) {
        // first let get cart items for the user
        CartDto cartDto = cartService.listCartItems(user);

        List<CartItemDto> cartItemDtoList = cartDto.getCartItems();

        // create the order and save it
        Order newOrder = new Order();
        newOrder.setCreatedDate(new Date());
        newOrder.setUser(user);
        newOrder.setTotalPrice(cartDto.getTotalCost());
        orderRepository.save(newOrder);

        for (CartItemDto cartItemDto : cartItemDtoList) {
            // create orderItem and save each one
            OrderItem orderItem = new OrderItem();
            orderItem.setCreatedDate(new Date());
            orderItem.setPrice(cartItemDto.getProduct().getPrice());
            orderItem.setProduct(cartItemDto.getProduct());
            orderItem.setQuantity(cartItemDto.getQuantity());
            orderItem.setOrder(newOrder);
            // add to order item list
            orderItemRepository.save(orderItem);
        }

        //
        cartService.deleteUserCartItems(user);
    }

    public List<Order> getOrdersByUser(User user){
        return orderRepository.findAllByUserOrderByCreatedDateDesc(user);

    }
}

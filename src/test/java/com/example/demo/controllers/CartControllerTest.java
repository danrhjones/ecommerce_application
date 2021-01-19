package com.example.demo.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

public class CartControllerTest {

    private UserController userController;
    private UserRepository userRepository = mock(UserRepository.class);

    private CartController cartController;
    private CartRepository cartRepository = mock(CartRepository.class);

    private ItemController itemController;
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void before() {
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);
    }

    @Test
    public void test_adding_to_a_cart() {
        when(userRepository.findByUsername("addToCart")).thenReturn(createUser());
        when(itemRepository.findById(2L)).thenReturn(java.util.Optional.of(createItem(2L)));

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(2L);
        modifyCartRequest.setQuantity(1);
        modifyCartRequest.setUsername("addToCart");
        ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Cart cart = response.getBody();
        assertEquals(BigDecimal.valueOf(2.99), cart.getTotal());
    }

    @Test
    public void test_adding_to_a_cart_but_item_not_present() {
        when(userRepository.findByUsername("addToCart")).thenReturn(createUser());
        when(itemRepository.findById(2L)).thenReturn(java.util.Optional.of(createItem(2L)));

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("addToCart");
        ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void test_adding_to_a_cart_but_user_not_present() {
        when(userRepository.findByUsername("addToCart")).thenReturn(createUser());
        when(itemRepository.findById(2L)).thenReturn(java.util.Optional.of(createItem(2L)));

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(2L);
        modifyCartRequest.setQuantity(1);
        ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void test_remove_from_cart() {
        when(userRepository.findByUsername("addToCart")).thenReturn(createUser());
        when(itemRepository.findById(1L)).thenReturn(java.util.Optional.of(createItem(1L)));

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setQuantity(1);
        modifyCartRequest.setUsername("addToCart");
        cartController.addTocart(modifyCartRequest);
        ResponseEntity<Cart> response = cartController.removeFromcart(modifyCartRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Cart cart = response.getBody();
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.CEILING), cart.getTotal());
    }

    @Test
    public void test_remove_from_cart_but_user_not_present() {
        when(userRepository.findByUsername("addToCart")).thenReturn(createUser());
        when(itemRepository.findById(1L)).thenReturn(java.util.Optional.of(createItem(1L)));

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setQuantity(1);
        cartController.addTocart(modifyCartRequest);
        ResponseEntity<Cart> response = cartController.removeFromcart(modifyCartRequest);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void test_remove_from_cart_but_item_not_present() {
        when(userRepository.findByUsername("addToCart")).thenReturn(createUser());
        when(itemRepository.findById(1L)).thenReturn(java.util.Optional.of(createItem(1L)));

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("addToCart");
        cartController.addTocart(modifyCartRequest);
        ResponseEntity<Cart> response = cartController.removeFromcart(modifyCartRequest);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    private Item createItem(Long id) {
        Item item = new Item();
        item.setId(id);
        item.setName("blue book");
        BigDecimal price = BigDecimal.valueOf(2.99);
        item.setPrice(price);
        item.setDescription("It's a blue book");
        return item;
    }

    private User createUser() {
        User user = new User();
        Cart cart = new Cart();
        user.setId(0);
        user.setUsername("addToCart");
        user.setPassword("password");
        user.setCart(cart);
        return user;
    }


}

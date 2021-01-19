package com.example.demo.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

public class ItemControllerTest {

    private ItemController itemController;
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void before() {
        itemController = new ItemController();
        TestUtils.injectObjects(itemController,  "itemRepository", itemRepository);
    }

    @Test
    public void test_get_all_items() {
        Item item = new Item();
        item.setId(2L);
        item.setDescription("book");
        item.setName("Green book");
        item.setPrice(new BigDecimal(98.0));
        List<Item> items = new ArrayList<>();
        items.add(item);
        when(itemRepository.findAll()).thenReturn(Collections.singletonList(item));

        ResponseEntity<List<Item>> response = itemController.getItems();
        assertEquals(200, response.getStatusCodeValue());
        List<Item> itemsResponse = response.getBody();
        assertEquals("Green book", itemsResponse.get(0).getName());
        assertEquals("book", itemsResponse.get(0).getDescription());
        assertEquals(new BigDecimal(98.0), itemsResponse.get(0).getPrice());
    }

    @Test
    public void test_get_item_by_id() {
        Item item = new Item();
        item.setId(1L);
        item.setDescription("kettle");
        item.setName("Green Kettle");
        item.setPrice(new BigDecimal(99.0));
        when(itemRepository.findById(1L)).thenReturn(java.util.Optional.of(item));
        final ResponseEntity<Item> response = itemController.getItemById(1L);
        assertEquals(200, response.getStatusCodeValue());
        Item itemsResponse = response.getBody();
        assertEquals("Green Kettle", itemsResponse.getName());
        assertEquals("kettle", itemsResponse.getDescription());
        assertEquals(new BigDecimal(99.0), itemsResponse.getPrice());
    }

    @Test
    public void test_get_item_by_name() {
        Item item = new Item();
        item.setId(1L);
        item.setDescription("Green chair");
        item.setName("chair");
        item.setPrice(new BigDecimal(199.0));
        when(itemRepository.findByName("chair")).thenReturn(Collections.singletonList(item));
        ResponseEntity<List<Item>> response = itemController.getItemsByName("chair");
        assertEquals(200, response.getStatusCodeValue());
        List<Item> itemsResponse = response.getBody();
        assertEquals("chair", itemsResponse.get(0).getName());
        assertEquals("Green chair", itemsResponse.get(0).getDescription());
        assertEquals(new BigDecimal(199.0), itemsResponse.get(0).getPrice());
    }

}

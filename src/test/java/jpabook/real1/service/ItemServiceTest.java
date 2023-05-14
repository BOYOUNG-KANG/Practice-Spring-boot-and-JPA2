package jpabook.real1.service;

import jpabook.real1.domain.OrderItem;
import jpabook.real1.domain.item.Book;
import jpabook.real1.domain.item.Item;
import jpabook.real1.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class ItemServiceTest {
    @Autowired ItemService itemService;
    @Autowired ItemRepository itemRepository;

    @Test
    public void 상품등록() throws Exception {
        //given
        Item item = new Book();
        item.setName("jPA");
        item.setPrice(100);
        item.setStockQuantity(1);

        //when
        itemService.saveItem(item);

        //then

    }

}
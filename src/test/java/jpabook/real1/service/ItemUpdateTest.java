package jpabook.real1.service;

import jpabook.real1.domain.item.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@ExtendWith(Extension.class)
@Transactional
public class ItemUpdateTest {
    @Autowired EntityManager em;

    @Test
    public void itemUpdateTest() throws Exception {
        //given
        Book book = em.find(Book.class, 1L);

        //when
        book.setName("sdjigl");

        //dirtychecking

        //

        //then

    }

}

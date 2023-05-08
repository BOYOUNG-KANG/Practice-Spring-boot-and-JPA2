package jpabook.real1.service;

import jpabook.real1.domain.Member;
import jpabook.real1.domain.Order;
import jpabook.real1.domain.OrderStatus;
import jpabook.real1.domain.item.Book;
import jpabook.real1.domain.item.Item;
import jpabook.real1.repository.OrderRepository;
import org.apache.tomcat.jni.Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;
    @Test
    public void 상품주문() throws Exception{
        //given
        Member member = new Member();
        member.setUsername("회원1");
        //member.setAddress(new Address("sfjf", "djskl", "dsjkl"));
        em.persist(member);

        Book book = createBook();

        int orderCount = 2;
        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.ORDER, getOrder.getStatus());//상품 주문시 상태는 order
        assertEquals(1, getOrder.getOrderItems().size()); //주문한 상품 종류의 수가 정화해야한다
        assertEquals(10000 * orderCount, getOrder.getTotalPrice()); //주문 가격은 가격 * 수량이다
        assertEquals(8, book.getStockQuantity()); //주문 수량만큼 재고가 줄어야 한다

    }


    @Test
    public void 주문취소() throws Exception{
        //given
        Member member = new Member();
        member.setUsername("회원1");
        //member.setAddress(new Address("sfjf", "djskl", "dsjkl"));
        em.persist(member);

        Book item = createBook();
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, getOrder.getStatus()); //주문 취소시 상태는 cancel이어야 한다.
        assertEquals(10, item.getStockQuantity()); //주문이 취소된 상품은 그만큰 재고가 증가해야 한다.
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception{
        //given
        Member member = new Member();
        member.setUsername("회원1");
        member.setAddress(new Address("sfjf", "djskl", "dsjkl"));
        em.persist(member);

        Item item = createBook();

        int orderCount = 11;

        //when

        //then
        Assertions.assertThrows(IllegalStateException.class, () -> orderService.order(member.getId(), item.getId(), orderCount));

    }

    //command+option+M
    private Book createBook() {
        Book book = new Book();
        book.setName("시골 jpa");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);
        return book;
    }

}
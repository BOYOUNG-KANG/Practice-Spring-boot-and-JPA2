package jpabook.real1.service;

import jpabook.real1.domain.item.Item;
import jpabook.real1.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){

        itemRepository.save(item);
    }
    @Transactional
    public void updateItem(Long itemId, Item itemParam) {
        Item findItem = itemRepository.findOne(itemId);
        findItem.setPrice(itemParam.getPrice());
        findItem.setName(itemParam.getName());
        findItem.setStockQuantity(itemParam.getStockQuantity());

    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }
    public Item findOne(Long itemId){
        return  itemRepository.findOne(itemId);
    }
}
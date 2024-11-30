package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional // 저장 -> 쓰기도 가능해야 됨
    public void saveItem(Item item) {
        /* 방법2. 병합 사용 (em.merge()로 데이터를 저장하고 있음) - 값 전체 변경 됨(전달되지 않은 값은 null.. 위험 쓰지말기) */
        itemRepository.save(item);
    }

    /* 방법1. 변경 감지에 의해 준영속성 엔터티를 변경하는 방법 - 일부 값만 변경 가능 */
    // em.merge()는 아래 코드가 작동되는 것과 같음
    @Transactional
    public Item updateItem(Long itemId, UpdateItemDto updateItemDto) {
        Item findItem = itemRepository.findOne(itemId); // id를 기반으로 실제 영속성 엔티티를 가져옴
        findItem.setName(updateItemDto.getName()); // 엔터티의 값 세팅
        findItem.setPrice(updateItemDto.getPrice());
        findItem.setStockQuantity(updateItemDto.getStockQuantity());

        // @Transactional 에 의해, 트랜잭션이 커밋됨 -> JPA는 flush 날림 (영속성 entity중 변경된 것 찾음) -> DB에 변경 상태 반영

        return findItem;
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

}

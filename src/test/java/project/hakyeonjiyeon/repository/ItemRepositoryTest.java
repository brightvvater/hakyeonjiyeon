package project.hakyeonjiyeon.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import project.hakyeonjiyeon.contstant.ItemSellStatus;
import project.hakyeonjiyeon.entity.Category;
import project.hakyeonjiyeon.entity.Item;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @AfterEach
    public void cleanup() {
        itemRepository.deleteAll();
    }

    @Test
    @DisplayName("상품 저장 불러오기")
    public void createItemTest() {
        //given
        String content = "상품 내용";
        String teacher = "강사이름";
        //Category category = new Category(1L,"카테고리");

        itemRepository.save(Item.builder()
                .title("상품제목")
                .teacher(teacher)
                .content(content)
                .price(1000)
                .stockQuantity(1)
                .itemSellStatus(ItemSellStatus.SELL)
                .build());
        
        //when
        List<Item> itemList = itemRepository.findAll();

        //then
        Item item = itemList.get(0);
        assertThat(item.getContent()).isEqualTo(content);
        assertThat(item.getTeacher()).isEqualTo(teacher);
    }




}
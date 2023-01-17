package project.hakyeonjiyeon.entity;

import lombok.*;
import project.hakyeonjiyeon.contstant.ItemSellStatus;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Item extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String teacher;

    private int price;

    @ManyToOne
    private Category category;

    private String content;

    private int stockQuantity;

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    @Builder
    public Item(Long id, String title, String teacher, int price, Category category, String content, int stockQuantity, ItemSellStatus itemSellStatus) {
        this.id = id;
        this.title = title;
        this.teacher = teacher;
        this.price = price;
        this.category = category;
        this.content = content;
        this.stockQuantity = stockQuantity;
        this.itemSellStatus = itemSellStatus;
    }
}

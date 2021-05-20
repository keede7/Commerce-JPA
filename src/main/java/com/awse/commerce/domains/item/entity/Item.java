package com.awse.commerce.domains.item.entity;

import com.awse.commerce.domains.util.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@ToString
@Getter
@Entity
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(nullable = false)
    private String imgPath; // 이미지

    @Column(nullable = false)
    private String name; // 상품명

    @Column(nullable = false)
    private int money; // 가격

    @Column(nullable = false)
    private int stockQuantity; // 재고 수량

    // 재고 빼기
    public void removeStockQuantity(int orderQuantity) {
        int restStockQuantity = this.stockQuantity - orderQuantity;
        // 주문수량보다 재고가 없으면
        if (restStockQuantity < 0) {
            throw new IllegalStateException("재고가 부족합니다.");
        }

        this.stockQuantity = restStockQuantity;
    }
    // 재고 추가
    public void addStockQuantity(int orderQuantity) {
        this.stockQuantity += orderQuantity;
    }
}

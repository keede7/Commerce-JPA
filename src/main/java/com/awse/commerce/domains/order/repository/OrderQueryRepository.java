package com.awse.commerce.domains.order.repository;

import com.awse.commerce.domains.delivery.entity.QDelivery;
import com.awse.commerce.domains.member.entity.QMember;
import com.awse.commerce.domains.order.entity.Order;
import com.awse.commerce.domains.order.entity.QOrder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderQueryRepository extends QuerydslRepositorySupport {

    private JPAQueryFactory queryFactory;

    private QOrder order;
    private QMember member;
    private QDelivery delivery;

    public OrderQueryRepository(JPAQueryFactory jpaQueryFactory) {
        super(Order.class);
        this.queryFactory = jpaQueryFactory;
        this.order = QOrder.order;
        this.member = QMember.member;
        this.delivery = QDelivery.delivery;
    }

    public Page<Order> getMyOrders(Long memberId, Pageable pageable) {

        QueryResults<Order> queryResults = queryFactory.select(order)
                .from(order)
                .leftJoin(order.orderer, member).fetchJoin()
                .leftJoin(order.deliveryInfo, delivery).fetchJoin()
                .where(order.orderer.id.eq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(order.orderId.desc())
                .fetchResults();

        List<Order> orderList = queryResults.getResults();

        long total = queryResults.getTotal();

        return new PageImpl<>(orderList, pageable, total);
    }
}
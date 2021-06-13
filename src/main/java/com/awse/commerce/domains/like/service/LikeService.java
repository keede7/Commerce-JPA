package com.awse.commerce.domains.like.service;

import com.awse.commerce.domains.item.entity.Item;
import com.awse.commerce.domains.item.repository.ItemRepository;
import com.awse.commerce.domains.like.dto.LikedItemDetails;
import com.awse.commerce.domains.like.dto.PageResultLikedItemDto;
import com.awse.commerce.domains.like.entity.Like;
import com.awse.commerce.domains.like.repository.LikeQueryRepository;
import com.awse.commerce.domains.like.repository.LikeRepository;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.util.pagination.PageRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class LikeService {

    private final LikeRepository likeRepository;
    private final ItemRepository itemRepository;

    private final LikeQueryRepository likeQueryRepository;

    // 좋아요 추가하기
    public boolean addLike(Member member, Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품 입니다."));

        if (isAlreadyLike(member, item)) {
            return false;
        }

        Like like = new Like(member, item);

        likeRepository.save(like);

        return true;
    }

    // 좋아요 취소하기
    public void cancelLike(Member member, Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품 입니다."));

        Like like = likeRepository.findByMemberAndItem(member, item).get();

        likeRepository.delete(like);
    }

    // 찜목록 전체 조회
    public PageResultLikedItemDto<LikedItemDetails> getMyLikeList(Long memberId, PageRequestDto requestDto) {

        Page<LikedItemDetails> pageList = likeQueryRepository.getLikeList(memberId, requestDto.getPageable("id"));

        return new PageResultLikedItemDto<>(pageList);
    }
    // NOTE : use other Controller
    public boolean isEnabledLike(Member member, Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품 입니다."));

        return likeRepository.findByMemberAndItem(member, item).isPresent();
    }

    // 이미 추가했는지 확인
    private boolean isAlreadyLike(Member member, Item item) {
        return likeRepository.findByMemberAndItem(member, item).isPresent();
    }
}

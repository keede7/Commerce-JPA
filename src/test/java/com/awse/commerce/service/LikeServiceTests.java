package com.awse.commerce.service;

import com.awse.commerce.domains.like.service.LikeService;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.member.repository.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Log4j2
public class LikeServiceTests {

    @Autowired
    private LikeService likeService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("좋아요 추가성공")
    @Test
    @Transactional
    @Commit
    public void addLikeTests() {
        Member member = memberRepository.findById(1L).get();

        likeService.addLike(member, 3L);

    }

    @DisplayName("좋아요 취소하기")
    @Test
    @Transactional
    @Commit
    public void cancelLikeTests() {
        Member member = memberRepository.findById(1L).get();

        likeService.cancelLike(member, 3L);
    }
}

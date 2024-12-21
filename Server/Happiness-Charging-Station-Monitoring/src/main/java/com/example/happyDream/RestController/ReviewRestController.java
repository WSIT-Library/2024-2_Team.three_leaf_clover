package com.example.happyDream.RestController;

import com.example.happyDream.DTO.ResponseDTO;
import com.example.happyDream.DTO.ReviewDTO;
import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Repository.ChargerRepository;
import com.example.happyDream.Service.ReviewService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ReviewRestController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewRestController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // TODO - Review 전체 예외 처리
    // 전체 리뷰 조회
    @GetMapping("/reviews")
    public ResponseDTO reviewSelectAll(Model model) {
        List<ReviewDTO> reviews = this.reviewService.reviewSelectAll();
        return ResponseDTO.success("v1", HttpServletResponse.SC_OK, reviews);
    }

    // 리뷰 추가
    @PostMapping("/reviews")
    public ResponseDTO reviewInsert(@RequestBody ReviewDTO review) {
        System.out.println("charger_id : " + review.getChargerId());
        System.out.println("userId : " + review.getUserId());
        System.out.println("content : " + review.getReviewContent());
        System.out.println("rating : " + review.getRating());

        this.reviewService.reviewInsert(review);
        return ResponseDTO.success("v1", HttpServletResponse.SC_OK);
    }

    // 특정 리뷰 수정
    @PostMapping("/reviews/{id}")
    public ResponseDTO reviewUpdate(@RequestBody ReviewDTO review) {
        this.reviewService.reviewUpdate(review);
        return ResponseDTO.success("v1", HttpServletResponse.SC_OK);
    }

    // 전체 리뷰 삭제
    @DeleteMapping("/reviews")
    public ResponseDTO reviewDeleteAll() {
        this.reviewService.reviewDeleteAll();
        return ResponseDTO.success("v1", HttpServletResponse.SC_OK);
    }

    // 특정 리뷰 조회
    @GetMapping("/reviews/{id}")
    public ResponseDTO reviewSelect(@PathVariable("id") Integer id) {
        ReviewDTO review = this.reviewService.reviewSelect(id);
        return ResponseDTO.success("v1", HttpServletResponse.SC_OK, Collections.singletonList(review));
    }

    // 특정 충전소 id 리뷰 조회
    @GetMapping("/reviews/charger/{id}")
    public ResponseDTO reviewSelectByChargerId(@PathVariable("id") Integer id) {
        List<ReviewDTO> reviews = this.reviewService.reviewSelectByChargerId(id);
        return ResponseDTO.success("v1", HttpServletResponse.SC_OK, reviews);
    }


    // 리뷰 검색
    @GetMapping("/reviews/search")
    public ResponseDTO reviewSelectAsSearch(
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "district", required = false) String district,
            @RequestParam(value = "keyword", required = false) String keyword) {

        // 주소 및 검색어로 필터링된 리뷰 목록을 가져오는 서비스 메서드 호출
        List<ReviewDTO> reviews = reviewService.reviewSelectAsSearch(city, district, keyword);

        for(ReviewDTO review : reviews){
            System.out.println("review : " + review);
        }

        // 성공적으로 가져온 리뷰를 클라이언트에 반환
        return ResponseDTO.success("v1", HttpServletResponse.SC_OK, reviews);
    }

    // 특정 리뷰 삭제
    @DeleteMapping("/reviews/{id}")
    public ResponseDTO reviewDelete(@PathVariable("id") Integer id) {
        this.reviewService.reviewDelete(id);
        return ResponseDTO.success("v1", HttpServletResponse.SC_OK);
    }
}

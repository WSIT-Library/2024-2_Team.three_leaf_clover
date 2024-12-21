package com.example.happyDream.Controller;

import com.example.happyDream.DTO.ChargerDTO;
import com.example.happyDream.DTO.ReportDTO;
import com.example.happyDream.DTO.ReviewDTO;
import com.example.happyDream.DTO.UserDTO;
import com.example.happyDream.Service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/reviews")
    public String reviewSelectAll(Model model, HttpServletRequest request) {
        Map<String, List<String>> uniqueCities = reviewService.reviewAddress();
        List<ReviewDTO> reviews = reviewService.reviewSelectAll();
        List<ReviewDTO> ratingFive = reviewService.reviewSelectByRatingFive();
        List<ReviewDTO> ratingFour = reviewService.reviewSelectByRatingFour();
        List<ReviewDTO> ratingThree = reviewService.reviewSelectByRatingThree();
        List<ReviewDTO> ratingTwo = reviewService.reviewSelectByRatingTwo();
        List<ReviewDTO> ratingOne = reviewService.reviewSelectByRatingOne();
        float ratingAverage = (float) ((ratingFive.size() * 5) +
                (ratingFour.size() * 4) +
                (ratingThree.size() * 3) +
                (ratingTwo.size() * 2) +
                (ratingOne.size() * 1)) / reviews.size();
        ratingAverage = Math.round(ratingAverage * 10) / 10.0f;
        model.addAttribute("cities", uniqueCities);
        model.addAttribute("reviews", reviews.size());
        model.addAttribute("ratingFiveCount", ratingFive.size());
        model.addAttribute("ratingFourCount", ratingFour.size());
        model.addAttribute("ratingThreeCount", ratingThree.size());
        model.addAttribute("ratingTwoCount", ratingTwo.size());
        model.addAttribute("ratingOneCount", ratingOne.size());
        model.addAttribute("ratingAverage", ratingAverage);
        model.addAttribute("currentUri", request.getRequestURI());
        return "reviews";
    }
}

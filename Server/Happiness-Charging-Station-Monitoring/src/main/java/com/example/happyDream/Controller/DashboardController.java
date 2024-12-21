package com.example.happyDream.Controller;

import com.example.happyDream.DTO.ChargerDTO;
import com.example.happyDream.DTO.ReportDTO;
import com.example.happyDream.DTO.ReviewDTO;
import com.example.happyDream.Service.ChargerServiceFacade;
import com.example.happyDream.Service.ReportService;
import com.example.happyDream.Service.ReviewService;
import groovy.util.logging.Slf4j;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
public class DashboardController {
    private final ChargerServiceFacade chargerServiceFacade;
    private final ReviewService reviewService;
    private final ReportService reportService;

    @Autowired
    public DashboardController(ChargerServiceFacade chargerServiceFacade, ReviewService reviewService, ReportService reportService) {
        this.chargerServiceFacade = chargerServiceFacade;
        this.reviewService = reviewService;
        this.reportService = reportService;
    }
    
    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        //충전기
        List<ChargerDTO> chargers = this.chargerServiceFacade.chargerSelectAll();
        List<ChargerDTO> usingChargers = this.chargerServiceFacade.chargerSelectByUsingYn(true);
        List<ChargerDTO> idleChargers = this.chargerServiceFacade.chargerSelectByUsingYn(false);
        List<ChargerDTO> brokenChargers = this.chargerServiceFacade.chargerSelectByBrokenYn(true);
        List<ChargerDTO> notRequestRecentlyCharger = this.chargerServiceFacade.chargerSelectWithoutLogInPeriod(30);

        //리뷰
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
                (ratingOne.size())) / reviews.size();
        ratingAverage = Math.round(ratingAverage * 10) / 10.0f;

        //고장 신고
        List<ReportDTO> reports = reportService.reportSelectAll();
        List<ReportDTO> totalRepairRequired = reportService.reportSelectByCheckedReport();
        List<ReportDTO> totalRepaired = reportService.reportSelectByCheckedRepair();

        //충전기
        model.addAttribute("totalChargerCount", chargers.size());
        model.addAttribute("usingChargerCount", usingChargers.size());
        model.addAttribute("idleChargerCount", idleChargers.size() - brokenChargers.size());
        model.addAttribute("brokenChargerCount", brokenChargers.size());
        model.addAttribute("notRequestRecentlyChargerCount", notRequestRecentlyCharger.size());
        model.addAttribute("notUsingTodayCharger", 0);
        model.addAttribute("chargerList", chargers);

        //리뷰
        model.addAttribute("totalReviewCount", reviews.size());
        model.addAttribute("ratingFiveCount", ratingFive.size());
        model.addAttribute("ratingFourCount", ratingFour.size());
        model.addAttribute("ratingThreeCount", ratingThree.size());
        model.addAttribute("ratingTwoCount", ratingTwo.size());
        model.addAttribute("ratingOneCount", ratingOne.size());
        model.addAttribute("averageReviewRating", ratingAverage);

        //고장 신고
        model.addAttribute("totalReportCount", reports.size());
        model.addAttribute("totalRepairRequiredCount", totalRepairRequired.size());
        model.addAttribute("totalRepairedCount", totalRepaired.size());

        //페이지 주소
        model.addAttribute("currentUri", request.getRequestURI());

        return "dashboard_V2";
    }
}

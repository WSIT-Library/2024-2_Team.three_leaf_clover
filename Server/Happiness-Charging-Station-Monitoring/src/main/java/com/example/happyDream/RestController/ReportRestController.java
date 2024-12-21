package com.example.happyDream.RestController;

import com.example.happyDream.DTO.ReportDTO;
import com.example.happyDream.DTO.ResponseDTO;
import com.example.happyDream.DTO.ReviewDTO;
import com.example.happyDream.Service.ReportService;
import com.example.happyDream.Service.ReviewService;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ReportRestController {

    private final ReportService reportService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    public ReportRestController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/reports")
    public ResponseDTO reportSelectAll(Model model) {
        List<ReportDTO> reports = this.reportService.reportSelectAll();
        return ResponseDTO.success("v1", HttpServletResponse.SC_OK, reports);
    }

    @PostMapping("/reports/check/{reportId}")
    public ResponseDTO reportCheck(@PathVariable("reportId") Integer reportId) {
        this.reportService.reportCheck(reportId);
        return ResponseDTO.success("v1", HttpServletResponse.SC_OK);
    }

    @PostMapping("/reports/repair/{reportId}")
    public ResponseDTO reportRepair(@PathVariable("reportId") Integer reportId) {
        this.reportService.reportRepair(reportId);
        return ResponseDTO.success("v1", HttpServletResponse.SC_OK);
    }

    @PostMapping("/reports")
    public ResponseDTO reportInsert(@RequestBody ReportDTO reportRequest) {
        ReportDTO reportDTO = ReportDTO.builder()
                .chargerId(reportRequest.getChargerId())
                .reportContent(reportRequest.getReportContent())
                .createdAt(LocalDateTime.now())
                .checkedReport(false)
                .checkedRepair(false)
                .build();

        this.reportService.insertReport(reportDTO);
        return ResponseDTO.success("v1", HttpServletResponse.SC_OK);
    }

    // 신고 검색
    @GetMapping("/reports/search")
    public ResponseDTO reportSelectAsSearch(
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "district", required = false) String district,
            @RequestParam(value = "chargerId", required = false) Integer chargerId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {

        LocalDateTime parsedStartDate = null;
        LocalDateTime parsedEndDate = null;

        parsedStartDate = LocalDateTime.parse(startDate + "T00:00");
        parsedEndDate = LocalDateTime.parse(endDate + "T00:00");

        // 주소 및 검색어로 필터링된 리뷰 목록을 가져오는 서비스 메서드 호출
        List<ReportDTO> reports = reportService.reportSelectAsSearch(city, district, chargerId, parsedStartDate, parsedEndDate);
        return ResponseDTO.success("v1", HttpServletResponse.SC_OK, reports);
    }
}

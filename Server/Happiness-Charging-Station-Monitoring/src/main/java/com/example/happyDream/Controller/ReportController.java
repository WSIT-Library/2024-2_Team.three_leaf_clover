package com.example.happyDream.Controller;

import com.example.happyDream.DTO.ReportDTO;
import com.example.happyDream.Service.ReportService;
import com.example.happyDream.Service.ReviewService;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/reports")
    public String reviewSelectAll(Model model, HttpServletRequest request) {
        Map<String, List<String>> uniqueCities = reportService.reportAddress();
        List<ReportDTO> reports = reportService.reportSelectAll();
        List<ReportDTO> notReportChargers = reportService.reportSelectByNotCheckedReport();
        List<ReportDTO> reportChargers = reportService.reportSelectByCheckedReport();
        List<ReportDTO> repairChargers = reportService.reportSelectByCheckedRepair();
        model.addAttribute("cities", uniqueCities);
        model.addAttribute("reports", reports.size());
        model.addAttribute("notReportChargersCount", notReportChargers.size());
        model.addAttribute("reportChargersCount", reportChargers.size());
        model.addAttribute("repairChargersCount", repairChargers.size());
        model.addAttribute("currentUri", request.getRequestURI());
        return "reports";
    }
}

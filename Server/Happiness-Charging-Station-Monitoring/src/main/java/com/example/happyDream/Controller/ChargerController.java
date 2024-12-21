package com.example.happyDream.Controller;

import com.example.happyDream.DTO.ChargerDTO;
import com.example.happyDream.DTO.ReportDTO;
import com.example.happyDream.Service.ChargerServiceFacade;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
public class ChargerController {
    private final ChargerServiceFacade chargerServiceFacade;

    @Autowired
    public ChargerController(ChargerServiceFacade chargerServiceFacade) {
        this.chargerServiceFacade = chargerServiceFacade;
    }

    //전체 충전기 조회
    @GetMapping("/chargers")
    public String chargerSelectAll(Model model, HttpServletRequest request,
                                   @RequestParam(value = "address", required = false) String address,
                                   @RequestParam(value = "latitude", required = false) Double latitude,
                                   @RequestParam(value = "longitude", required = false) Double longitude) {
        List<ChargerDTO> chargers;
        try {
            if (address != null) {
                log.info(address);
                chargers = this.chargerServiceFacade.chargerSelectByAddress(address);
            }
            else if (latitude != null && longitude != null) {
                log.info("{}, {}", latitude, longitude);
                chargers = this.chargerServiceFacade.chagerSelectNear(latitude, longitude);
            }
            else {
                chargers = this.chargerServiceFacade.chargerSelectAll();
            }
        } catch (EntityNotFoundException ignored) {
            chargers = null;
        }
        model.addAttribute("chargers", chargers);
        model.addAttribute("currentUri", request.getRequestURI());
        return "chargers_V2";
    }

    //전체 충전기 삭제
    @DeleteMapping("/chargers")
    public String chargerDeleteAll() {
        this.chargerServiceFacade.chargerDeleteAll();
        return "chargers";
    }

    //충전기 추가
    /*
    @PostMapping("/chargers")
    public String chargerInsert(@RequestParam(value="")) {

        return " ";
    }
    */

    //특정 충전기 조회
    @GetMapping("/chargers/{id}")
    public String chargerSelect(@PathVariable("id") Integer id) {
        ChargerDTO charger = this.chargerServiceFacade.chargerSelect(id);
        return " ";
    }

    //특정 충전기 삭제
    @DeleteMapping("/chargers/{id}")
    public String chargerDelete(@PathVariable("id") Integer id) {
        this.chargerServiceFacade.chargerDelete(id);
        return " ";
    }

    @GetMapping("/chargers/lists")
    public String chargerList(Model model,
                              @RequestParam(value = "usingYn", required = false) Boolean usingYn,
                              @RequestParam(value = "brokenYn", required = false) Boolean brokenYn) {
        List<ChargerDTO> chargers = this.chargerServiceFacade.chargerSelectAll();

        model.addAttribute("chargers", chargers);
        return  "";
    }
}

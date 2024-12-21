package com.example.happyDream.Controller;

import com.example.happyDream.Service.ChargerServiceFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Controller
public class ChargerStateController {
    private final ChargerServiceFacade chargerServiceFacade;

    @Autowired
    public ChargerStateController(ChargerServiceFacade chargerServiceFacade) {
        this.chargerServiceFacade = chargerServiceFacade;
    }
}
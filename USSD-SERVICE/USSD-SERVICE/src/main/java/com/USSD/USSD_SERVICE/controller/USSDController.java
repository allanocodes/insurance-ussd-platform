package com.USSD.USSD_SERVICE.controller;

import com.USSD.USSD_SERVICE.dto.UssdSession;
import com.USSD.USSD_SERVICE.service.SessionService;
import com.USSD.USSD_SERVICE.service.SessionStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("USSD")
public class USSDController {

    @Autowired
    SessionStateService stateService;
    @Autowired
    SessionStateService sessionStateService;
    @Autowired
    SessionService sessionService;


    @PostMapping(value = "/generate",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,produces = MediaType.TEXT_PLAIN_VALUE)
    public String generateUssdMenu(@RequestParam String sessionId,@RequestParam String serviceCode,
                                   @RequestParam  String phoneNumber,@RequestParam(required = false) String text){

      String trimmedText = text == null ? "" : text.trim();
      String input[] = trimmedText.isEmpty() ? new String[0]: trimmedText.split("\\*");
      String lastText = input.length != 0 ? input[input.length -1]: "";


        UssdSession ussdSession = sessionService.getOrSet(sessionId,phoneNumber,serviceCode);


        return sessionStateService.produceReturnMessage(lastText,ussdSession);
    }

}

package com.example.cih.controller.auction;

import com.example.cih.dto.auction.AuctionRegDTO;
import com.example.cih.dto.auction.AuctionViewDTO;
import com.example.cih.service.auction.AuctionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/auction")
@RequiredArgsConstructor
@Log4j2
public class AuctionRestController {

    private final AuctionService auctionService;

    @PostMapping("/register")
    public Map<String,String> registerAuction(@Valid @RequestBody AuctionRegDTO auctionRegDTO,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                Principal principal ) throws BindException {
        log.error("auctionRegister post...." + auctionRegDTO);

        if(bindingResult.hasErrors()){
            log.error("has errors.....");
            throw new BindException(bindingResult);
        }

        auctionService.registerAuction(principal.getName(), auctionRegDTO);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");

        return resultMap;
    }

    @GetMapping("/get")
    public AuctionViewDTO getAuction(Long auctionId, Principal principal){

        AuctionViewDTO auctionRegDTO = auctionService.getAuction(auctionId);

        return auctionRegDTO;
    }
}

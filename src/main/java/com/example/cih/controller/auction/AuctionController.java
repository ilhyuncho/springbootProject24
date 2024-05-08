package com.example.cih.controller.auction;



import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.auction.AuctionViewDTO;
import com.example.cih.dto.car.CarViewDTO;
import com.example.cih.dto.user.UserDTO;
import com.example.cih.service.auction.AuctionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/auction")
@RequiredArgsConstructor
@Log4j2
@PreAuthorize("hasRole('USER')")
public class AuctionController {

    private final AuctionService auctionService;

    @GetMapping("/list")
    public String userCarList(PageRequestDTO pageRequestDTO, String userName,
                              Model model){

        List<AuctionViewDTO> listAuction = auctionService.getListAuction();

        model.addAttribute("list", listAuction);

        return "/auction/auctionList";
    }


}

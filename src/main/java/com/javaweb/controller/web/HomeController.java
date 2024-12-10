package com.javaweb.controller.web;

import com.javaweb.constant.SystemConstant;
import com.javaweb.enums.districtCode;
import com.javaweb.enums.typeCode;
import com.javaweb.model.dto.CustomerDTO;
import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.model.response.BuildingSearchResponse;
import com.javaweb.service.IBuildingService;
import com.javaweb.utils.DisplayTagUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller(value = "homeControllerOfWeb")
@RequiredArgsConstructor
public class HomeController {
    private final IBuildingService buildingService;

    @RequestMapping(value = "/trang-chu", method = RequestMethod.GET)
    public ModelAndView homePage(@ModelAttribute(SystemConstant.MODEL) BuildingSearchRequest model,
                                 HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("web/home");
        DisplayTagUtils.of(request, model);

        List<BuildingSearchResponse> buildings = buildingService.findAll(model);
        model.setListResult(buildings);
        model.setTotalItems(buildingService.countTotalItem(model));

        mav.addObject("district", districtCode.district());
        mav.addObject("typeCodes", typeCode.getTypeCode());
        mav.addObject(SystemConstant.MODEL, model);

        return mav;
    }

    @GetMapping(value = "/gioi-thieu")
    public ModelAndView introducceBuiding() {
        return new ModelAndView("web/introduce");
    }

    @GetMapping(value = "/san-pham")
    public ModelAndView buildingList(@ModelAttribute(SystemConstant.MODEL) BuildingSearchRequest model,
                                     HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/web/list");
        DisplayTagUtils.of(request, model);

        List<BuildingSearchResponse> buildings = buildingService.findAll(model);
        model.setListResult(buildings);
        model.setTotalItems(buildingService.countTotalItem(model));

        mav.addObject("district", districtCode.district());
        mav.addObject("typeCodes", typeCode.getTypeCode());
        mav.addObject(SystemConstant.MODEL, model);

        return mav;
    }

    @GetMapping(value = "/tin-tuc")
    public ModelAndView news() {
        return new ModelAndView("/web/news");
    }

    @GetMapping(value = "/lien-he")
    public ModelAndView contact(@ModelAttribute(SystemConstant.MODEL) CustomerDTO model) {
        return new ModelAndView("/web/contact");
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        return new ModelAndView("/login");
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register() {
        return new ModelAndView("/register");
    }

    @RequestMapping(value = "/access-denied", method = RequestMethod.GET)
    public ModelAndView accessDenied() {
        return new ModelAndView("redirect:/login?accessDenied");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return new ModelAndView("redirect:/trang-chu");
    }
}

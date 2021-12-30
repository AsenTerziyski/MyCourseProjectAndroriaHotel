package com.example.myproject.web;

import com.example.myproject.model.binding.OfferAddBindingModel;
import com.example.myproject.model.view.OfferSummaryView;
import com.example.myproject.service.OffersService;
import com.example.myproject.web.exceptions.UserNotSupportedOperation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class OffersController {
    private final OffersService offersService;

    public OffersController(OffersService offersService) {
        this.offersService = offersService;
    }

    @GetMapping("/add-offer")
    public String getOfferAddPage(Model model) {
        if (!model.containsAttribute("standardDiscountBiggerThanVip")) {
            model.addAttribute("standardDiscountBiggerThanVip", false);
        }
        return "offer-add";
    }

    @ModelAttribute
    public OfferAddBindingModel offerAddBindingModel() {
        return new OfferAddBindingModel();
    }

    @PostMapping("/add-offer")
    private String booking(@Valid OfferAddBindingModel offerAddBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           Principal principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("offerAddBindingModel", offerAddBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.offerAddBindingModel",
                            bindingResult);
            return "redirect:/add-offer";
        }

        if (offerAddBindingModel.getDiscount() >= offerAddBindingModel.getVipDiscount()) {
            redirectAttributes
                    .addFlashAttribute("offerAddBindingModel", offerAddBindingModel)
                    .addFlashAttribute("standardDiscountBiggerThanVip", true);
            return "redirect:/add-offer";
        }
        this.offersService.addOffer(offerAddBindingModel, principal);
        return "index_androria";
    }

    @GetMapping("/offers")
    public String allOffers(Model model) {
        List<OfferSummaryView> allOffers = this.offersService.getAllOffers();
        model.addAttribute("offers", allOffers);
        if (allOffers.size() != 0) {
            model.addAttribute("noOffers", false);
        } else {
            model.addAttribute("noOffers", true);
        }
        return "offers-all";
    }

    @GetMapping("/remove-offer")
    private String getRemoveOfferPage(Model model, Principal principal) {
        List<OfferSummaryView> allOffers = this.offersService.getAllOffers();
        model.addAttribute("offers", allOffers);
        for (OfferSummaryView offer : allOffers) {
                boolean offerOwnerOrAdmin = this.offersService.isOfferOwner(principal, offer.getId());
                if (offerOwnerOrAdmin) {
                    offer.setCanDelete(true);
                }
        }

        return "offer-remove";
    }

    @DeleteMapping("/offers/remove/{id}")
    public String removeOffer(@PathVariable Long id, Principal principal, Model model) {
        boolean offerOwnerOrAdmin = this.offersService.isOfferOwner(principal, id);
        if (!offerOwnerOrAdmin) {
            throw new UserNotSupportedOperation(principal.getName());
        }

        this.offersService.removeOffer(id);
        return "redirect:/remove-offer";
    }

    @ExceptionHandler(UserNotSupportedOperation.class)
    public ModelAndView handleDbExceptions(UserNotSupportedOperation e) {
        ModelAndView modelAndView = new ModelAndView("userNotSupportedOperation");
        modelAndView.addObject("userName", e.getUsername());
        modelAndView.setStatus(HttpStatus.UNAUTHORIZED);
        return modelAndView;
    }
}

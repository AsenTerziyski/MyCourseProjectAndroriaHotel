package com.example.myproject.web;

import com.example.myproject.model.binding.PricesEditBindingModel;
import com.example.myproject.model.entities.enums.RoomEnum;
import com.example.myproject.model.view.RoomPricesView;
import com.example.myproject.service.RoomService;
import com.example.myproject.web.exceptions.UserNotSupportedOperation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/prices")
public class PricesController {

    private final RoomService roomService;

    public PricesController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping()
    public String getPricesPage(Model model) {
        List<RoomPricesView> allPrices = this.roomService.getAllPrices();
        model.addAttribute("allRoomsPrices", allPrices);
        return "prices-all";
    }


    @GetMapping("/edit")
    public String getEditPricesPage() {
        return "prices-edit";
    }

    @ModelAttribute
    public PricesEditBindingModel pricesEditBindingModel() {
        return new PricesEditBindingModel();
    }

    @PostMapping("/edit/post-price")
    public String editPrices(@Valid PricesEditBindingModel pricesEditBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes, Authentication authentication) {
        Optional<? extends GrantedAuthority> role_admin = authentication
                .getAuthorities()
                .stream()
                .filter(a -> a.getAuthority().equalsIgnoreCase("role_admin"))
                .findAny();
        if (role_admin.isEmpty()) {
            String name = "not admin user";
            throw new UserNotSupportedOperation(name);
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("pricesEditBindingModel", pricesEditBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.pricesEditBindingModel",
                            pricesEditBindingModel);
            return "prices-edit";
        }
        this.roomService.editPrice(pricesEditBindingModel);
        return "priceEditConfirmation";
    }

    @ExceptionHandler(UserNotSupportedOperation.class)
    public ModelAndView handlePricesExceptions(UserNotSupportedOperation e) {
        ModelAndView modelAndView = new ModelAndView("userNotSupportedOperation");
        modelAndView.addObject("userName", e.getUsername());
        return modelAndView;
    }
}

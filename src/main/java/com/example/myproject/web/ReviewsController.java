package com.example.myproject.web;

import com.example.myproject.model.binding.ReviewSendBindingModel;
import com.example.myproject.model.view.ReviewSummeryView;
import com.example.myproject.service.GuestService;
import com.example.myproject.service.ReviewService;
import com.example.myproject.web.exceptions.UserNotSupportedOperation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class ReviewsController {
    private final GuestService guestService;
    private final ReviewService reviewService;

    public ReviewsController(GuestService guestService, ReviewService reviewService) {
        this.guestService = guestService;
        this.reviewService = reviewService;
    }

    @GetMapping("/reviews")
    public String getReviewsPage(Model model) {
        List<ReviewSummeryView> allReviews = this.reviewService.getAllReviews();
        model.addAttribute("allReviews", allReviews);
        return "reviews";
    }

    @ModelAttribute
    public ReviewSendBindingModel reviewSendBindingModel() {
        return new ReviewSendBindingModel();
    }

    @PostMapping("/reviews/send")
    public String sendReview(@Valid ReviewSendBindingModel reviewSendBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        System.out.println();

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("reviewSendBindingModel", reviewSendBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.reviewSendBindingModel", bindingResult);
            return "redirect:/reviews";
        }

        this.guestService.addReview(reviewSendBindingModel);
        return "redirect:/reviews";
    }

    @GetMapping("/reviews/remove")
    public String getReviewsEdit(Model model) {
        List<ReviewSummeryView> allReviews = this.reviewService.getAllReviews();
        model.addAttribute("allReviews", allReviews);
        return "reviews-remove";
    }

    @DeleteMapping("/reviews/remove/{id}")
    @Transactional
    public String removeReview(@PathVariable Long id, Principal principal) {

        if (principal == null) {
            String name = "not registered user";
            throw new UserNotSupportedOperation(name);
        }

        this.reviewService.deleteReviewById(id);
        return "redirect:/reviews/remove";
    }

    @ExceptionHandler(UserNotSupportedOperation.class)
    public ModelAndView handleNotRegisteredUserCantRemoveReview(UserNotSupportedOperation e) {
        ModelAndView modelAndView = new ModelAndView("userNotSupportedOperation");
        modelAndView.addObject("userName", e.getUsername());
        modelAndView.setStatus(HttpStatus.UNAUTHORIZED);
        return modelAndView;
    }

}

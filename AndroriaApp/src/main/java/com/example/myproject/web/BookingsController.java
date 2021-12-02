package com.example.myproject.web;

import com.example.myproject.model.binding.BookingBindingModel;
import com.example.myproject.model.entities.enums.RoomEnum;
import com.example.myproject.model.view.BookingSummaryView;
import com.example.myproject.service.BookingService;
import com.example.myproject.web.exceptions.UserNotSupportedOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Controller
public class BookingsController {
    private final BookingService bookingService;

    public BookingsController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/book")
    public String getBooking(Model model) {
        if (!model.containsAttribute("stayIsNegative")) {
            model.addAttribute("stayIsNegative", false);
        }
        if(!model.containsAttribute("roomNotSelected")) {
            model.addAttribute("roomNotSelected", false);
        }
        return "booking_create";
    }

    @ModelAttribute
    public BookingBindingModel bookingBindingModel() {
        return new BookingBindingModel();
    }

    @PostMapping("/book/create")
    private String booking(@Valid BookingBindingModel bookingBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("bookingBindingModel", bookingBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.bookingBindingModel",
                            bookingBindingModel);
            return "booking_create";
        }
        LocalDate checkIn = bookingBindingModel.getCheckIn();
        LocalDate checkOut = bookingBindingModel.getCheckOut();
        long stay = DAYS.between(checkIn, checkOut);
        if (stay <= 0) {
            redirectAttributes
                    .addFlashAttribute("bookingBindingModel", bookingBindingModel)
                    .addFlashAttribute("stayIsNegative", true);
            return "redirect:/book";
        }
        RoomEnum room = bookingBindingModel.getRoom();
        if (room == null) {
            redirectAttributes
                    .addFlashAttribute("bookingBindingModel", bookingBindingModel)
                    .addFlashAttribute("roomNotSelected", true);
            return "redirect:/book";
        }
        this.bookingService.saveNewBooking(bookingBindingModel);
        return "index_androria";
    }

    @GetMapping("/remove-booking")
    private String getRemoveBookingPage(Model model) {
        LocalDate now = LocalDate.now();
        List<BookingSummaryView> allExpiredBookings = this.bookingService.getAllExpiredBookings(now);
        model.addAttribute("expiredBookings", allExpiredBookings);
        List<BookingSummaryView> allNotExpiredBookings = this.bookingService.getAllNotExpiredBookings(now);
        model.addAttribute("notExpiredBookings", allNotExpiredBookings);
        return "booking-remove";
    }

    @PostMapping("/bookings/remove/{id}")
    public String removeBooking(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            String name = "not registered users";
            throw new UserNotSupportedOperation(name);
        }
        this.bookingService.removeBooking(id);
        return "redirect:/remove-booking";
    }

    @ExceptionHandler(UserNotSupportedOperation.class)
    public ModelAndView handleBookingsExceptions(UserNotSupportedOperation e) {
        ModelAndView modelAndView = new ModelAndView("userNotSupportedOperation");
        modelAndView.addObject("userName", e.getUsername());
        return modelAndView;
    }

}

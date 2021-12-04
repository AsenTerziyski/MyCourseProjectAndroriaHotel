package com.example.myproject.web;

import com.example.myproject.model.binding.MessageSendBindingModel;
import com.example.myproject.model.entities.MessageEntity;
import com.example.myproject.model.view.ReviewSummeryView;
import com.example.myproject.service.GuestService;
import com.example.myproject.service.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ContactsController {
    private final GuestService guestService;
    private final MessageService messageService;

    public ContactsController(GuestService guestService, MessageService messageService) {
        this.guestService = guestService;
        this.messageService = messageService;
    }

    @GetMapping("/contacts")
    public String getContacts() {
        return "contacts";
    }

    @ModelAttribute
    public MessageSendBindingModel messageSendBindingModel () {
        return new MessageSendBindingModel();
    }

    @PostMapping("/contacts/send")
    public String sendEmail (@Valid MessageSendBindingModel messageSendBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("messageSendBindingModel", messageSendBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.messageSendBindingModel",
                            messageSendBindingModel);
            return "contacts";
        }
        sendMessage(messageSendBindingModel);
        return "index_androria";
    }

    private void sendMessage(MessageSendBindingModel messageSendBindingModel) {
        String email = messageSendBindingModel.getEmail();
        String text = messageSendBindingModel.getText();
        this.guestService
                .receiveEmailAndMessageAndCreatesNewGuestByEmailIfNotExistsOrAddsMessageToExistingGuest(email, text);
    }

    @GetMapping("/messages")
    public String getContactFormMessagesPage(Model model) {
        List<MessageEntity> allMessagesFromContactForm = this.messageService.getAllMessagesFromContactForm();
        model.addAttribute("allMessages", allMessagesFromContactForm);
        return "messages";
    }

}

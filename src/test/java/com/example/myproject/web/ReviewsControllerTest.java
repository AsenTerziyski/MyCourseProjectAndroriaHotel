package com.example.myproject.web;

import com.example.myproject.model.binding.ReviewSendBindingModel;
import com.example.myproject.model.entities.ReviewEntity;
import com.example.myproject.model.view.ReviewSummeryView;
import com.example.myproject.repository.ReviewRepository;
import com.example.myproject.service.GuestService;
import com.example.myproject.service.ReviewService;
import com.example.myproject.web.exceptions.UserNotSupportedOperation;
import net.bytebuddy.implementation.bytecode.Throw;
import org.aspectj.lang.annotation.AfterThrowing;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class ReviewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GuestService guestService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void testGetReviewsPage() throws Exception {
        mockMvc.perform(get("/reviews"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allReviews"))
                .andExpect(view().name("reviews"));
    }

    @Test
    void testSendReview() throws Exception {
        List<ReviewSummeryView> allReviews = this.reviewService.getAllReviews();
        int beforeTestingPostReview = allReviews.size();

        mockMvc
                .perform(post("/reviews/send")
                        .param("name", "testName")
                        .param("email", "test@test.com")
                        .param("text", "testTestTestTestTestTest")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection());
        int afterTestingPostReview = this.reviewService.getAllReviews().size();
        assertEquals((beforeTestingPostReview + 1), afterTestingPostReview);
        deleteTestReview();

        mockMvc
                .perform(post("/reviews/send")
                        .param("name", "testName")
                        .param("email", "test@test.com")
                        .param("text", "t")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(view().name("reviews"));
    }

    private void deleteTestReview() {
        List<ReviewEntity> reviews = this.reviewRepository.findAllReviews();
        Long idToDelete = 0L;
        for (int i = reviews.size()-1; i >=0; i--) {
            ReviewEntity reviewEntity = reviews.get(i);
            if (i == reviews.size()-1) {
                idToDelete = reviewEntity.getId();
                break;
            }
        }
        this.reviewRepository.deleteById(idToDelete);
    }

    @Test
    @WithMockUser(username = "test")
    void getReviewsEdit() throws Exception {
        mockMvc.perform(get("/reviews/remove"))
                .andExpect(status().isOk())
                .andExpect(view().name("reviews-remove"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void removeReview() throws Exception {

        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setReviewText("Test-Test-Test-Test-Test-Test-Test-Test-Test-Test")
                .setReviewerName("Axl Rose")
                .setGuest(this.guestService.createNewGuestByEmailIfNotExistsAndReturnsHimOrReturnsExistingGuestByEmail("test@test.com"));

        Long id = this.reviewRepository.save(reviewEntity).getId();
        int beforeTest = this.reviewRepository.findAllReviews().size();

        mockMvc
                .perform(MockMvcRequestBuilders.delete("/reviews/remove/" + id)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection());

        int afterTest = this.reviewRepository.findAllReviews().size();

        Assertions.assertEquals(beforeTest - 1, afterTest);
    }

    @Test
    void TestTryToRemoveReviewFromNotRegisteredUser() throws Exception {

        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setReviewText("Test-Test-Test-Test-Test-Test-Test-Test-Test-Test")
                .setReviewerName("Axl Rose")
                .setGuest(this.guestService.createNewGuestByEmailIfNotExistsAndReturnsHimOrReturnsExistingGuestByEmail("test@test.com"));

        Long id = this.reviewRepository.save(reviewEntity).getId();
        int beforeTest = this.reviewRepository.findAllReviews().size();

        mockMvc
                .perform(MockMvcRequestBuilders.delete("/reviews/remove/" + id)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection());

        int afterTest = this.reviewRepository.findAllReviews().size();
        Assertions.assertEquals(beforeTest, afterTest);
        this.reviewRepository.deleteById(id);
    }

}
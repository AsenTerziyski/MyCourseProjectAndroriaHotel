package com.example.myproject.web;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.example.myproject.model.entities.GuestEntity;
import com.example.myproject.model.entities.ReviewEntity;
import com.example.myproject.repository.GuestRepository;
import com.example.myproject.repository.ReviewRepository;
import com.example.myproject.service.GuestService;
import com.example.myproject.service.ReviewService;
import org.junit.jupiter.api.Test;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser("malmsuite")
@SpringBootTest
@AutoConfigureMockMvc
class ReviewsRestControllerTest {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private GuestService guestService;
    @Autowired
    private GuestRepository guestRepository;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllReviews() throws Exception {
        ReviewEntity testReview = createTestReview();
        this.reviewRepository.save(testReview);
        int beforeTest = this.reviewService.getAllReviews().size();
        List<ReviewEntity> reviews = this.reviewRepository.findAllReviews();
        mockMvc
                .perform(get("/androriapi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(beforeTest)));
        deleteTestReview();
    }

    private ReviewEntity createTestReview() {
        GuestEntity testGuest = this.guestService
                .createNewGuestByEmailIfNotExistsAndReturnsHimOrReturnsExistingGuestByEmail("test@test.com");

        ReviewEntity testReview = new ReviewEntity()
                .setReviewText("TestTestTestTest..........TestTestTestTest")
                .setReviewerName("Test Name")
                .setGuest(testGuest);
        return testReview;
    }

    private void deleteTestReview() {
        int size = this.reviewRepository.findAllReviews().size();
        for (int i = size - 1; i >= 0; i--) {
            if (i == size - 1) {
                ReviewEntity reviewEntity = this.reviewRepository.findAll().get(i);
                this.reviewRepository.delete(reviewEntity);
                break;
            }
        }

        //!!!!!!!!!! TRY
        GuestEntity guestToDelete = this.guestRepository.findByEmail("test@test.com").orElse(null);
        if (guestToDelete != null) {
            this.guestRepository.delete(guestToDelete);
        }
    }

}
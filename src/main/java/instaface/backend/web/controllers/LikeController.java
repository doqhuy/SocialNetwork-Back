package instaface.backend.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import instaface.backend.services.LikeService;
import instaface.backend.utils.responseHandler.exceptions.CustomException;
import instaface.backend.utils.responseHandler.successResponse.SuccessResponse;
import instaface.backend.utils.constants.ResponseMessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

import static instaface.backend.utils.constants.ResponseMessageConstants.SUCCESSFUL_LIKE_POST_MESSAGE;

@RestController()
@RequestMapping(value = "/like")
public class LikeController {

    private final LikeService likeService;
    private final ObjectMapper objectMapper;

    @Autowired
    public LikeController(LikeService likeService, ObjectMapper objectMapper) {
        this.likeService = likeService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(value = "/add")
    public ResponseEntity addLike(@RequestBody Map<String, Object> body) throws Exception {
        String postId = (String) body.get("postId");
        String loggedInUserId = (String) body.get("loggedInUserId");

        boolean result = this.likeService.addLike(postId, loggedInUserId);

        if (result) {
            SuccessResponse successResponse = new SuccessResponse(LocalDateTime.now(), SUCCESSFUL_LIKE_POST_MESSAGE, "", true);

            return new ResponseEntity<>(this.objectMapper.writeValueAsString(successResponse), HttpStatus.OK);
        }

        throw new CustomException(ResponseMessageConstants.SERVER_ERROR_MESSAGE);
    }
}

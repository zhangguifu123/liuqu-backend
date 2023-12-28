package com.omate.liuqu.controller;

import com.omate.liuqu.dto.UserDTO;
import com.omate.liuqu.model.Comment;
import com.omate.liuqu.model.Result;
import com.omate.liuqu.service.CommentService;
import com.omate.liuqu.service.PostService;
import com.omate.liuqu.utils.JWTManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/api") // This means URL's start with /demo (after Application path)
public class CommentController {
    private final CommentService commentService;
    private final PostService postService;

    @Autowired
    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;this.postService = postService;
    }

    private boolean checkToken(String token){
        if (token == null || token.isEmpty() || !token.contains("Bearer ")) {
            return false;
        }
        return true;
    }

    @PostMapping(value = "/posts/{pid}/comments", consumes = { "multipart/form-data" })
    public ResponseEntity<Result> createComment(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                 @PathVariable Integer pid,
                                                  Comment commentRequest) {
        token = token.replace("Bearer ", "");
        UserDTO userDTO = JWTManager.getDataFromToken(token, "user", UserDTO.class);
        Result result = new Result();
        if (userDTO == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (!postService.existsById(pid)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Comment comment = commentService.createComment(pid, userDTO.getUid(), commentRequest.getContext(), commentRequest.getParentCommentId());

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("comment", comment);
        result.setResultSuccess(0, resultMap);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("comments/{cid}")
    public ResponseEntity<Void> softDeleteComment(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
//                                                  @PathVariable Integer pid,
                                                  @PathVariable Integer cid) {

        token = token.replace("Bearer ", "");
        UserDTO userDTO = JWTManager.getDataFromToken(token, "user", UserDTO.class);
        Result result = new Result();
        if (userDTO == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }


        boolean isDeleted = commentService.softDeleteComment(cid, userDTO.getUid());
        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


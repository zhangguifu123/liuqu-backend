package com.omate.liuqu.service;

import com.omate.liuqu.model.Comment;
import com.omate.liuqu.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment createComment(Integer pid, Integer uid, String context, Integer parentCommentId) {
        Comment comment = new Comment();
        comment.setPid(pid);
        comment.setUid(uid);
        comment.setContext(context);
        comment.setParentCommentId(parentCommentId);
        return commentRepository.save(comment);
    }


    public boolean softDeleteComment(Integer commentId, Integer userId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            if (comment.getUid().equals(userId)) {
                comment.setIfDeleted(true);
                commentRepository.save(comment);
                return true;
            }
        }
        return false;
    }
}

package com.omate.liuqu.repository;

import com.omate.liuqu.model.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {

    Optional<Comment> findById(Integer id);

    List<Comment> findByParentCommentIdAndIfDeletedFalse(Integer parentCommentId);
    List<Comment> findByPidAndParentCommentIdIsNullAndIfDeletedFalse(Integer pid);
}

package com.omate.liuqu.repository;

import com.omate.liuqu.model.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {

    Optional<Post> findById(Integer id);

    List<Post> findAll();
}

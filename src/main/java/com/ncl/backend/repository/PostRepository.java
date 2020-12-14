package com.ncl.backend.repository;

import com.ncl.backend.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByType(String type);
    List<Post> findAllByTypeContains(String type);

}

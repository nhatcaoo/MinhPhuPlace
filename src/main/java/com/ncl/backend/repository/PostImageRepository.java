package com.ncl.backend.repository;

import com.ncl.backend.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    PostImage findByPostIdAndType(Long postId, String type);

    List<PostImage> findAllByPostId(Long postId);

    Integer deleteAllByPostId(Long postId);
}

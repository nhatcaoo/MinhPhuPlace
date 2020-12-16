package com.ncl.backend.repository;

import com.ncl.backend.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository  extends JpaRepository<Image, Long> {
    Image findByPostIdAndType(Long postId, String type);
    List<Image> findAllByPostId(Long postId);

    Integer deleteAllByPostId(Long postId);
}

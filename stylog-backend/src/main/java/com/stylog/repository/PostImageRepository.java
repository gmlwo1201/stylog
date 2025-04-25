package com.stylog.repository;

import com.stylog.entity.Post;
import com.stylog.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {

	PostImage findByPost(Post post);
}

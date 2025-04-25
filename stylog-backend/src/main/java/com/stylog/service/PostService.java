package com.stylog.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.stylog.dto.PostFeedResponse;
import com.stylog.entity.Post;
import com.stylog.entity.PostImage;
import com.stylog.repository.PostImageRepository;
import com.stylog.repository.PostRepository;

@Service
public class PostService {

	private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;

    public PostService(PostRepository postRepository, PostImageRepository postImageRepository) {
        this.postRepository = postRepository;
        this.postImageRepository = postImageRepository;
    }
    // 게시글 저장 메서드
	public Post savePost( String content, String productInfo, Integer userId) {
        Post post = new Post();
        post.setContent(content);
        post.setProductInfo(productInfo);
        post.setUserId(userId);
        post.setIsDeleted(0); // 기본 0(정상)
        post.setIsPublic(1); // 기본 1(공개)
        post.setCreatedAt(LocalDateTime.now());

        return postRepository.save(post); // 저장 후 Post 객체 리턴
    }

    // 이미지 저장 메서드
    public void savePostImage(Post post, String imageUrl) {
        PostImage postImage = new PostImage();
        postImage.setPost(post);
        postImage.setImageUrl(imageUrl);

        postImageRepository.save(postImage);
    }
    public List<PostFeedResponse> getFeed() {
        List<Post> posts = postRepository.findAll(); // 모든 게시물 조회
        List<PostFeedResponse> responses = new ArrayList<>();

        for (Post post : posts) {
            PostImage postImage = postImageRepository.findByPost(post);

            PostFeedResponse response = new PostFeedResponse();
            response.setId(post.getId());
            response.setUsername("testuser"); // 지금은 임시로 고정 (나중에 users 테이블에서 가져올 수 있어)
            response.setImageUrl(postImage.getImageUrl());
            response.setContent(post.getContent());
            responses.add(response);
            response.setProductInfo(post.getProductInfo());
        }

        return responses;
    }
}

package com.stylog.controller;

import com.stylog.dto.PostFeedResponse;
import com.stylog.entity.Post;
import com.stylog.entity.PostImage;
import com.stylog.repository.PostRepository;
import com.stylog.service.PostService;
import com.stylog.repository.PostImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/stylog")
public class UploadController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostImageRepository postImageRepository;
    
    @Autowired
    private PostService postService;

    @GetMapping("/upload")
    public String uploadPage() {
        return "upload"; // /upload.jsp 보여줌
    }

    @PostMapping("/upload")
    public String handleUpload(
            @RequestParam("content") String content,
            @RequestParam("products-info") String productInfo,
            @RequestParam("image") MultipartFile imageFile
            
    ) {
        // 1. 파일 저장
    	String uploadDir = "C:/growith/uploads/";
    	File uploadPath = new File(uploadDir);

        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        String savedFileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        File destFile = new File(uploadDir + savedFileName);

        try {
            imageFile.transferTo(destFile);
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패!", e);
        }

        // 2. Post 저장
        Post post = new Post();
        post.setContent(content);
        post.setProductInfo(productInfo);
        post.setUserId(1); // 지금은 테스트용으로 userId를 1로 고정
        post.setIsDeleted(0);
        post.setIsPublic(1);
        post.setCreatedAt(LocalDateTime.now());

        Post savedPost = postRepository.save(post);

        // 3. PostImage 저장
        PostImage postImage = new PostImage();
        postImage.setPost(savedPost);
        postImage.setImageUrl("/uploads/" + savedFileName);

        postImageRepository.save(postImage);

        // 4. 업로드 완료 후 피드로 이동
        return "redirect:/stylog/feed";
    }

    @GetMapping("/feed")
    public String feedPage(Model model) {
        List<PostFeedResponse> posts = postService.getFeed(); 
        model.addAttribute("posts", posts);
        return "view_feed_one"; 
    }
}

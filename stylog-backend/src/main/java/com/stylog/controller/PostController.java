	package com.stylog.controller;
	
	import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.stylog.dto.PostFeedResponse;
import com.stylog.entity.Post;
import com.stylog.service.PostService;
	
	@RestController
	@RequestMapping("/api/posts")
	public class PostController {
	
		private final PostService postService;
	
		public PostController(PostService postService) {
			this.postService = postService;
		}
	
		@PostMapping
		public ResponseEntity<String> uploadPost(
				@RequestParam("content") String content, 
				@RequestParam("products-info") String productInfo,
				@RequestParam("userId") Integer userId, 
				@RequestParam("image") MultipartFile image) {
	
			// 1. 파일 저장
			String uploadDir = System.getProperty("user.dir") + "/uploads/";
			File uploadPath = new File(uploadDir);
	
			if (!uploadPath.exists()) {
				uploadPath.mkdirs();
			}
	
			String originalFilename = image.getOriginalFilename();
	
			// 시간 기반 파일명 생성 (예: 20250409_221200_img3.jpg)
			String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
			String savedFilename = timestamp + "_" + originalFilename;
	
			File savedFile = new File(uploadDir + savedFilename);
	
			try {
				image.transferTo(savedFile);
			} catch (IOException e) {
				throw new RuntimeException("파일 저장 실패", e);
			}
	
			// 2. 게시글 저장
			Post post = postService.savePost(content, productInfo, userId );
	
			// 3. 이미지 저장 (DB에 저장할 때는 경로만 저장)
			postService.savePostImage(post, "/uploads/" + savedFilename);
	
			return ResponseEntity.ok("업로드 및 저장 성공!");
		}
	
		@GetMapping("/feed")
		public List<PostFeedResponse> getFeed() {
			return postService.getFeed();
		}
	
	}

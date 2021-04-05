package com.acme.blog.boundary;

import java.util.List;
import java.util.Optional;

import com.acme.blog.domain.comment.CommentDto;
import com.acme.blog.domain.comment.CommentService;
import com.acme.blog.domain.comment.NewCommentDto;
import com.acme.blog.domain.post.PostDto;
import com.acme.blog.domain.post.PostService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {

	private final PostService postService;
	private final CommentService commentService;

	public PostController(PostService postService, CommentService commentService) {
		this.postService = postService;
		this.commentService = commentService;
	}

	@GetMapping("/{id}")
	public Optional<PostDto> getPost(@PathVariable Long id) {
		return postService.getPost(id);
	}

	@GetMapping("/{id}/comments")
	@ExceptionHandler(ResourceNotFoundException.class)
	public List<CommentDto> getComments(@PathVariable Long id) {
		if (commentService.checkPost(id)){
			return commentService.getCommentsForPost(id);
		}
		else {
			throw new ResourceNotFoundException();
		}
	}

	@PostMapping("/{id}/comment")
	@ResponseStatus(value = HttpStatus.CREATED)
	public void addComment(@PathVariable Long id, @RequestBody NewCommentDto newCommentDto) {
		commentService.addComment(id, newCommentDto);
	}

	@PostMapping("/")
	public void addPost(@RequestBody PostDto post) {
		postService.setPost(post);
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	class ResourceNotFoundException extends RuntimeException {
	}
}

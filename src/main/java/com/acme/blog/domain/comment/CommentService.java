package com.acme.blog.domain.comment;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import com.acme.blog.domain.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;

	/**
	 * Returns a list of all comments for a blog post with passed id.
	 *
	 * @param postId id of the post
	 * @return list of comments sorted by creation date descending - most recent first
	 * @throws IllegalArgumentException if there is no blog post for passed postId
	 */
	public List<CommentDto> getCommentsForPost(Long postId) {
		if (checkPost(postId)){
			List<CommentDto> commentList = commentRepository.findAllByPostId(postId);
			commentList.sort(Comparator.comparing(CommentDto::getCreationDate).reversed());
			return commentList;
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Creates a new comment
	 *
	 * @param newCommentDto data of new comment
	 * @return id of created comment
	 * @throws IllegalArgumentException if there is no blog post for passed newCommentDto.postId
	 */
	public Long addComment(Long id, NewCommentDto newCommentDto) {

		if (checkPost(id)){
			Comment comment = Comment.builder()
					.postId(id)
					.author(newCommentDto.getAuthor())
					.comment(newCommentDto.getContent())
					.creationDate(LocalDate.now())
					.build();
			comment = commentRepository.save(comment);
			return comment.getId();
		} else {
			throw new IllegalArgumentException();
		}
	}

	public boolean checkPost(Long id){
		return postRepository.existsById(id);
	}
}

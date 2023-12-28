package com.bbcommunity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bbcommunity.entity.Comment;
import com.bbcommunity.entity.Posts;
import com.bbcommunity.entity.User;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	// 특정 게시물의 모든 댓글을 조회하는 메서드
    List<Comment> findByPost(Posts post);

    // 특정 사용자가 작성한 모든 댓글을 조회하는 메서드
    List<Comment> findByUser(User user);

    // 댓글 ID로 댓글을 조회하는 메서드
    // Optional을 사용하는 이유는 ID로 조회했을 때 해당하는 댓글이 없을 수 있기 때문입니다.
    Optional<Comment> findByCommentId(Long commentId);

    // 댓글 삭제 메서드
    void deleteByCommentId(Long commentId);
}

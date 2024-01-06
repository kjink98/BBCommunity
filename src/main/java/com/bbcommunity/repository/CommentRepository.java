package com.bbcommunity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bbcommunity.entity.Comment;
import com.bbcommunity.entity.Posts;
import com.bbcommunity.entity.User;

import jakarta.transaction.Transactional;
/*
* 댓글 정보를 처리하는 레포지토리 인터페이스입니다.
* 특정 게시글의 댓글, 특정 사용자의 댓글, 댓글 ID를 이용한 댓글 조회, 댓글 삭제 등의 메소드를 제공합니다.
* JpaRepository 인터페이스를 상속받아 기본적인 CRUD 연산을 지원합니다.
*/

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	// 특정 게시글의 모든 댓글을 조회하는 메소드입니다.
    List<Comment> findByPost(Posts post);
    // 특정 사용자가 작성한 모든 댓글을 조회하는 메소드입니다.
    List<Comment> findByUser(User user);
    // 댓글 ID를 이용하여 댓글 정보를 조회하는 메소드입니다.
    Optional<Comment> findByCommentId(Long commentId);

    // 댓글 ID와 게시글 ID를 이용하여 댓글을 삭제하는 메소드입니다. 
    // @Modifying 어노테이션은 쿼리가 데이터를 변경하는 작업임을 나타냅니다.
    // @Transactional 어노테이션은 이 메소드가 하나의 트랜잭션으로 처리되어야 함을 나타냅니다.
    @Modifying
    @Transactional
    @Query("DELETE FROM Comment c WHERE c.commentId = :commentId AND c.post.postId = :postId")
    void deleteByCommentId(@Param("commentId") Long commentId, @Param("postId") Long postId);

}

package com.bbcommunity.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bbcommunity.entity.Posts;

/**
 * 게시글 정보를 다루는 Repository 인터페이스입니다.
 */
public interface PostsRepository extends JpaRepository<Posts, Long> {
    
	/**
     * 모든 글을 내림차순으로 조회하는 쿼리입니다.
     */
//	List<Posts> findAllByOrderByIdDesc();
	
	/**
     * 제목에 특정 단어를 포함하는 글을 대소문자 구분 없이 조회하는 쿼리입니다.
     */
	Page<Posts> findByTitleIgnoreCaseContaining(String title, Pageable pageable);
	
	/**
     * 조회수 올려주는 쿼리
     
	@Modifying
	@Query("")
	int updateView(@Param("id") Long id);
	
	List<Posts> findByAuthorOrderByIdDesc(String userId);
	*/
}
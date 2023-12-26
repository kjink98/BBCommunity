package com.bbcommunity.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bbcommunity.entity.Posts;
import com.bbcommunity.repository.PostsRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {
	private final PostsRepository postsRepository;

    public List<Posts> findAll() {
        return postsRepository.findAll();
    }
}

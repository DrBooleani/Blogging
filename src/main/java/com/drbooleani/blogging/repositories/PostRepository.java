package com.drbooleani.blogging.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.drbooleani.blogging.models.Post;

public interface PostRepository extends PagingAndSortingRepository<Post, Integer>, CrudRepository<Post, Integer> {
}

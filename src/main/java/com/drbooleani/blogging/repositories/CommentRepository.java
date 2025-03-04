package com.drbooleani.blogging.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.drbooleani.blogging.models.Comment;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Integer>, CrudRepository<Comment, Integer> {

}

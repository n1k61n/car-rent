package com.example.carrent.repositories;

import com.example.carrent.models.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository  extends JpaRepository<Blog, Long> {
}

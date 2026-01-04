package com.example.carrent.services;

import com.example.carrent.dtos.testimonial.TestimonialDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TestimonialService {

    List<TestimonialDto> getLastTestimonials(int size);

    Page<TestimonialDto> getTestimonils(Pageable pageable);

    boolean delete(Long id);
}

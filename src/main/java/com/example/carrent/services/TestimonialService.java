package com.example.carrent.services;

import com.example.carrent.dtos.testimonial.TestimonialDto;

import java.util.List;

public interface TestimonialService {

    List<TestimonialDto> getLastTestimonials(int size);
}

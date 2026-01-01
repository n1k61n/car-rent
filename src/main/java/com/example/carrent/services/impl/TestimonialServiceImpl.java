package com.example.carrent.services.impl;

import com.example.carrent.dtos.testimonial.TestimonialDto;
import com.example.carrent.models.Testimonial;
import com.example.carrent.repositories.TestimonialRepository;
import com.example.carrent.services.TestimonialService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestimonialServiceImpl  implements TestimonialService {

    private final TestimonialRepository testimonialRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<TestimonialDto> getLastTestimonials(int size) {
        Pageable limit = PageRequest.of(0, size);
        List<Testimonial> list = testimonialRepository.findByOrderByCreatedAtDesc(limit);
        if(!list.isEmpty()){
            return list.stream().map(testimonial -> modelMapper.map(testimonial, TestimonialDto.class)).toList();
        }
        return List.of();
    }
}

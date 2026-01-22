package com.example.carrent.services.impl;

import com.example.carrent.dtos.testimonial.TestimonialCreateDto;
import com.example.carrent.dtos.testimonial.TestimonialDto;
import com.example.carrent.dtos.testimonial.TestimonialUpdateDto;
import com.example.carrent.models.Testimonial;
import com.example.carrent.repositories.TestimonialRepository;
import com.example.carrent.services.TestimonialService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    @Override
    public Page<TestimonialDto> getTestimonils(Pageable pageable) {
        Page<Testimonial> pages = testimonialRepository.findAll(pageable);
        if(pages.hasContent()){
            return pages.map(testimonial -> modelMapper.map(testimonial, TestimonialDto.class));
        }
        return Page.empty();
    }

    @Override
    public boolean delete(Long id) {
        if(testimonialRepository.existsById(id)){
            testimonialRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean create(TestimonialCreateDto testimonialCreateDto) {
        if(testimonialCreateDto == null) return false;
        Testimonial testimonial = modelMapper.map(testimonialCreateDto, Testimonial.class);
        testimonial.setCreatedAt(LocalDate.now());
        testimonialRepository.save(testimonial);
        return true;
    }

    @Override
    public TestimonialUpdateDto getTestimonial(Long id) {
        if(testimonialRepository.existsById(id)){
            Testimonial testimonial = testimonialRepository.findById(id).get();
            return modelMapper.map(testimonial, TestimonialUpdateDto.class);
        }
        return new TestimonialUpdateDto();
    }

    @Override
    public boolean updateTestimonial(Long id, TestimonialUpdateDto testimonialUpdateDto) {
        if(testimonialRepository.existsById(id)){
            Testimonial testimonial = modelMapper.map(testimonialUpdateDto, Testimonial.class);
            testimonial.setId(id);
            testimonialRepository.save(testimonial);
            return true;
        }
        return false;
    }
}

package com.example.carrent.controllers.dashboard;

import com.example.carrent.dtos.car.CarCreateDto;
import com.example.carrent.dtos.car.CarDto;
import com.example.carrent.dtos.car.CarUpdateDto;
import com.example.carrent.dtos.category.CategoryDto;
import com.example.carrent.services.CarService;
import com.example.carrent.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dashboard/car")
@RequiredArgsConstructor
public class DashboardCarController {

    private final CarService carService;
    private final CategoryService categoryService;

    @GetMapping("/index")
    public String cars(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort", defaultValue = "id") String sortField,
            @RequestParam(name = "dir", defaultValue = "asc") String sortDir,
            @RequestParam(name = "keyword", required = false) String keyword,
            Model model) {

        // Sıralama və səhifələmə məlumatlarını birləşdiririk
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        // Axtarış metodu çağırılır
        Page<CarDto> carPage = carService.searchCars(keyword, pageable);

        // Modelə lazım olan bütün dataları yükləyirik
        model.addAttribute("carList", carPage.getContent());
        model.addAttribute("totalPages", carPage.getTotalPages());
        model.addAttribute("totalItems", carPage.getTotalElements());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("keyword", keyword);
        return "dashboard/car/index";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        List<CategoryDto> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "dashboard/car/create";
    }

    @PostMapping("/create")
    public String createCar(@Valid @ModelAttribute CarCreateDto carCreateDto, BindingResult result) {
        if (result.hasErrors()) {
            return "dashboard/car/create";
        }
        boolean hasCreateCar = carService.createCar(carCreateDto);
        return "redirect:/dashboard/car/index";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        CarUpdateDto carUpdateDto = carService.getUpdateCar(id);
        model.addAttribute("updateCar", carUpdateDto);
        List<CategoryDto> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "dashboard/car/update";
    }

    @PostMapping("/edit/{id}")
    public String updateCar(@PathVariable Long id, CarUpdateDto carUpdateDto){
        boolean result = carService.updateCar(id, carUpdateDto);
        return "redirect:/dashboard/car/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteCar(@PathVariable Long id){
        boolean result = carService.deleteCar(id);
        return "redirect:/dashboard/car/index";
    }

}

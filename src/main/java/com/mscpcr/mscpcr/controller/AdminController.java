package com.mscpcr.mscpcr.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mscpcr.mscpcr.entity.AppUser;
import com.mscpcr.mscpcr.entity.District;
import com.mscpcr.mscpcr.repository.DistrictRepository;
import com.mscpcr.mscpcr.service.AppUserService;

@Controller
public class AdminController {

    private final AppUserService appUserService;
    private final DistrictRepository districtRepository;

    public AdminController(AppUserService appUserService, DistrictRepository districtRepository) {
        this.appUserService = appUserService;
        this.districtRepository = districtRepository;
    }

    @GetMapping("/admin-dashboard")
    public String dashboard() {
        return "admin-dashboard";
    }

    @GetMapping("/admin/create-user")
    public String showCreateUserForm(Model model) {
        // Fetch the list of districts from the database
        List<District> districts = districtRepository.findAll();

        // Add the list of districts to the model
        model.addAttribute("districts", districts);

        // Return the name of the Thymeleaf template
        return "admin-create-user"; 
    }

    @PostMapping("/admin/create-user")
    public String registerUser(
        @RequestParam Long district,
        @RequestParam String userType,
        @RequestParam String username,
        @RequestParam String password,
        RedirectAttributes redirectAttributes) {

        try {
            // Find the district entity
            District selectedDistrict = districtRepository.findById(district)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid district ID"));

            // Parse usertype
            AppUser.Usertype parsedUserType = AppUser.Usertype.valueOf(userType.toUpperCase());

            // Create AppUser instance
            AppUser newUser = new AppUser();
            newUser.setUsername(username);
            newUser.setPasswordhash(password); // will be encoded in service
            newUser.setUsertype(parsedUserType);
            newUser.setDistrict(selectedDistrict);

            // Call service to validate and save
            appUserService.createUser(newUser);

            redirectAttributes.addFlashAttribute("message", "User registered successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Unexpected error occurred: " + e.getMessage());
        }

        return "redirect:/admin-dashboard";
    }
}

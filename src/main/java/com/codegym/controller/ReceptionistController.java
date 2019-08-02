package com.codegym.controller;

import com.codegym.model.Receptionist;
import com.codegym.model.ReceptionistForm;
import com.codegym.service.ReceptionistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping({"/receptionist"})
public class ReceptionistController {
    @Autowired
    private ReceptionistService receptionistService;
    @Autowired
    Environment env;

    @GetMapping("/list")
    public ModelAndView findAll() {
        ModelAndView modelAndView = new ModelAndView("/receptionist/list");
        modelAndView.addObject("receptionistList", receptionistService.findAll());
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/receptionist/create");
        modelAndView.addObject("receptionistForm", new ReceptionistForm());
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView save(ReceptionistForm receptionistForm) {
        MultipartFile multipartFile = receptionistForm.getAvatar();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = env.getProperty("file_upload".toString());
        try {
            FileCopyUtils.copy(receptionistForm.getAvatar().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Receptionist receptionist = new Receptionist(receptionistForm.getId(), receptionistForm.getName(), receptionistForm.getAge(), receptionistForm.getAddress(), receptionistForm.getHobby(), fileName);
        receptionistService.add(receptionist);
        ModelAndView modelAndView = new ModelAndView("/receptionist/create");
        modelAndView.addObject("message", "Created new receptionist succesfully!");
        modelAndView.addObject("receptionistForm", new ReceptionistForm());
        return modelAndView;
    }

    @GetMapping({"/detail/{id}"})
    public ModelAndView view(@PathVariable("id") int id) {
        Receptionist receptionist = receptionistService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/receptionist/detail");
        modelAndView.addObject("receptionist", receptionist);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable int id) {
        Receptionist receptionist = receptionistService.findById(id);
        ReceptionistForm receptionistForm = new ReceptionistForm(receptionist.getId(), receptionist.getName(),
                receptionist.getAge(), receptionist.getAddress(), receptionist.getHobby(), null);
        ModelAndView modelAndView = new ModelAndView("/receptionist/delete");
        modelAndView.addObject("receptionist", receptionist);
        modelAndView.addObject("receptionistForm", receptionistForm);
        return modelAndView;
    }

    @PostMapping("/delete")
    public String deleteReceptionist(@ModelAttribute("receptionist") Receptionist receptionist) {
        receptionistService.delete(receptionist.getId());
        return "redirect:/receptionist/list";
    }

    @GetMapping("/search")
    public ModelAndView showSearchForm() {
        ModelAndView modelAndView = new ModelAndView("/receptionist/search");
        return modelAndView;
    }

    @PostMapping({"/search"})
    public ModelAndView search(@RequestParam("name") String name) {
        Receptionist receptionist = receptionistService.findByName(name);
        ModelAndView modelAndView = new ModelAndView("/receptionist/search");
        modelAndView.addObject("receptionist", receptionist);
        if (receptionist == null) {
            modelAndView.addObject("message", "No result!");
        }
        return modelAndView;
    }

    @GetMapping({"/edit/{id}"})
    public ModelAndView showUpdateForm(@PathVariable("id") int id) {
        Receptionist receptionist = receptionistService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/receptionist/edit");
        modelAndView.addObject("receptionistForm", receptionist);
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView update(@ModelAttribute("receptionist") ReceptionistForm receptionistForm) {
        MultipartFile multipartFile = receptionistForm.getAvatar();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = env.getProperty("file_upload".toString());
        try {
            FileCopyUtils.copy(receptionistForm.getAvatar().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Receptionist receptionist = new Receptionist(receptionistForm.getId(), receptionistForm.getName(), receptionistForm.getAge(), receptionistForm.getAddress(), receptionistForm.getHobby(), fileName);
        receptionistService.update(receptionistForm.getId(), receptionist);
        ModelAndView modelAndView = new ModelAndView("/receptionist/edit");
        modelAndView.addObject("receptionistForm", receptionist);
        modelAndView.addObject("message", "Update receptionist completed!");
        return modelAndView;
    }
}

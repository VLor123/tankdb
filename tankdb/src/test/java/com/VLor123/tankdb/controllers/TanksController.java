package com.VLor123.tankdb.controllers;

import com.VLor123.tankdb.models.Tank;
import com.VLor123.tankdb.models.TankDto;
import com.VLor123.tankdb.services.TanksRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.sql.SQLOutput;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/tanks")
public class TanksController {

    @Autowired
    private TanksRepository repo;

    @GetMapping({"","/"})
    public String showTankList (Model model){
        List<Tank> tanks = repo.findAll();
        model.addAttribute("tanks",tanks);
        return "tanks/index";

    }

    @GetMapping("/create")
    public String showCreatePage(Model model){
        TankDto tankDto = new TankDto();
        model.addAttribute("tankDto", tankDto);
        return "tanks/CreateTank";
    }

    @PostMapping("/create")
    public String createTank(
            @Valid @ModelAttribute TankDto tankDto,
            BindingResult result){

        if(result.hasErrors()){
            return "tanks/CreateTank";
        }

        Date createdAt = new Date();

        Tank tank = new Tank();
        tank.setName(tankDto.getName());
        tank.setBrand(tankDto.getBrand());
        tank.setCategory(tankDto.getCategory());
        tank.setPrice(tankDto.getPrice());
        tank.setDescription(tankDto.getDescription());
        tank.setCreatedAt(createdAt);

        repo.save(tank);

        return "redirect:/tanks";
    }

    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam int id){
        try{
            Tank tank = repo.findById(id).get();
            model.addAttribute("tank", tank);

            TankDto tankDto = new TankDto();
            tankDto.setName(tank.getName());
            tankDto.setBrand(tank.getBrand());
            tankDto.setCategory(tank.getCategory());
            tankDto.setPrice(tank.getPrice());
            tankDto.setDescription(tank.getDescription());

            model.addAttribute("tankDto", tankDto);
        }
        catch (Exception ex){
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/tanks";
        }

        return "tanks/EditTanks";
    }
}

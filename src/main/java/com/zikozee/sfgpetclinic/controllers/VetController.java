package com.zikozee.sfgpetclinic.controllers;

import com.zikozee.sfgpetclinic.fauxspring.Model;
import com.zikozee.sfgpetclinic.services.VetService;

public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    public String listVets(Model model){

        model.addAttribute("vets", vetService.findAll());

        return "vets/index";
    }
}

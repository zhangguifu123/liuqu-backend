package com.omate.liuqu.controller;

import com.omate.liuqu.model.AutismRegister;
import com.omate.liuqu.service.AutismRegisterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/api") // This means URL's start with /demo (after Application path)
public class AutismRegisterController {
    @Autowired
    private AutismRegisterService autismRegisterService;

    @PostMapping(value = "/autism-register", consumes = { "multipart/form-data" })
    public ResponseEntity<AutismRegister> registerAutismRegister(@Valid AutismRegister autismRegister) {
        AutismRegister registeredAutism = autismRegisterService.registerAutismRegister(autismRegister);

        return new ResponseEntity<>(registeredAutism, HttpStatus.OK);
    }
}

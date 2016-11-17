package com.sugarfree.controller;

import com.sugarfree.dao.model.TCsol;
import com.sugarfree.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: ${}
 * @Description:
 * @author: LT
 * @date: 2016/11/16
 */
@RestController
@Slf4j
public class EnumController {

    @Autowired
    private MenuService menuService;

    @PostMapping(value = "/menu")
    public ResponseEntity updateMenu(){
        this.menuService.updateMenu();
        return ResponseEntity.ok().build();
    }
}

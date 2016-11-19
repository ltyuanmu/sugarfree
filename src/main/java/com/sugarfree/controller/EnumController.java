package com.sugarfree.controller;

import com.sugarfree.dao.model.TCsol;
import com.sugarfree.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

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
        try {
            this.menuService.updateMenu();
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.status(500).build();
        }
        return ResponseEntity.ok().build();
    }
}

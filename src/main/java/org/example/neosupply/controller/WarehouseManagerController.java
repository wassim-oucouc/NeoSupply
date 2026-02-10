package org.example.neosupply.controller;


import org.example.neosupply.dto.request.UsersDTO;
import org.example.neosupply.dto.response.UserDtoResponse;
import org.example.neosupply.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouse-managers")
public class WarehouseManagerController{

    public UserService userService;

    public WarehouseManagerController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDtoResponse> createWarehouseManager(@RequestBody UsersDTO usersDTO)
    {
        return ResponseEntity.ok().body(this.userService.registerUser(usersDTO));
    }


    @GetMapping
    public ResponseEntity<List<UserDtoResponse>> getAllWarehouseManagers()
    {
        return ResponseEntity.ok().body(this.userService.getWarehouseManagers());
    }


    @DeleteMapping("/{id}")
    public void deleteWarehouseManager(@PathVariable("id") Long warehouseManagerId)
    {
        this.userService.deleteUser(warehouseManagerId);
         ResponseEntity.ok().build();
    }


}

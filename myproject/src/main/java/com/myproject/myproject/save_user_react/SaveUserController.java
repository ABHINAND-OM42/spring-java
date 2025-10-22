package com.myproject.myproject.save_user_react;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/react")
@CrossOrigin(origins = "http://localhost:5173") // IMPORTANT for React communication
public class SaveUserController {
    @Autowired
    private SaveUserService userService;


    @PostMapping("/users")
    private ResponseEntity<SaveUserEntity> saveUser (@RequestBody SaveUserEntity entity){
        SaveUserEntity saveUser = userService.saveUser(entity);
        return new ResponseEntity<>(saveUser, HttpStatus.CREATED);

    }

    @GetMapping("/users")
    private ResponseEntity<List<SaveUserEntity>> getAllUsers(){
        return new ResponseEntity<>(userService.getUsers(),HttpStatus.OK);
    }

    @DeleteMapping("/users/delete")
    private ResponseEntity<SaveUserEntity> deleteUser (@PathVariable Long id){
        SaveUserEntity deletedUser = userService.deleteUser(id);
        return new ResponseEntity<>(deletedUser,HttpStatus.OK);
    }
}

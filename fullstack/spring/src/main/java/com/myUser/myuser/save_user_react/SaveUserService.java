package com.myUser.myuser.save_user_react;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaveUserService {

    @Autowired
    private SaveUserRepo saveUserRepo;

    public SaveUserEntity saveUser(SaveUserEntity entity){
       return saveUserRepo.save(entity);
    }


    public List<SaveUserEntity> getUsers(){
        return  saveUserRepo.findAll();
    }


    public SaveUserEntity deleteUser(Long id){

        Optional<SaveUserEntity> user =  saveUserRepo.findById(id);
        if(user.isPresent()){
        saveUserRepo.deleteById(id);
        return user.get();
        }
        else {
            System.out.println("user not found with this id");
            return null;
        }
    }
}

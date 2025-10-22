package com.myproject.myproject.save_user_react;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SaveUserRepo extends JpaRepository<SaveUserEntity, Long> {
}

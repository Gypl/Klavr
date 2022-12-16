package com.company.klavr.screen.register;

import com.company.klavr.screen.register.*;
import com.company.klavr.entity.User;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.UUID;

public interface RegistrationService {

    String NAME = "sample_RegistrationService";

    @Validated
    RegistrationResult registerUser(@CheckUserExists String login, String password);

    public static class RegistrationResult implements Serializable {

        private UUID userId;

        public RegistrationResult(User user) {
            if (user != null) {
                this.userId = user.getId();
            }
        }

        public UUID getUserId() {
            return userId;
        }
    }
}

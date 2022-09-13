package com.github.thomasdarimont.keycloak.custom.oidc.opa;

import lombok.Data;
import org.keycloak.models.UserModel;

@Data
public class OpaKeycloakUser {

    String username;

    public OpaKeycloakUser(UserModel user) {
        this.username = user.getUsername();
    }
}

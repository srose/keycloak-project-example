package com.github.thomasdarimont.keycloak.custom.oidc.opa;

import lombok.Data;
import org.keycloak.models.KeycloakContext;

@Data
public class OpaKeycloakContext {

    String realm;

    public OpaKeycloakContext(KeycloakContext context) {
        this.realm = context.getRealm().getName();
    }
}

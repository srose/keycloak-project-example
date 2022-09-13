package com.github.thomasdarimont.keycloak.custom.oidc.opa;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.jbosslog.JBossLog;
import org.keycloak.broker.provider.util.SimpleHttp;
import org.keycloak.models.KeycloakSession;
import java.io.IOException;
import java.util.Map;

@JBossLog
public class OpaPolicyClient {

    public JsonNode evaluatePolicy(KeycloakSession session, String url, Map<String, Object> input) {

        var http = SimpleHttp.doPost(url, session);

        http.json(input);

        return fetchResponse(http);
    }


    private static JsonNode fetchResponse(SimpleHttp http) {
        try {
            var response = http.asResponse();

            try {
                return response.asJson();
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        } catch (IOException e) {
            log.error("OPA access request failed", e);
            return null;
        }
    }

}
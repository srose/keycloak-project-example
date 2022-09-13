package com.github.thomasdarimont.keycloak.custom.oidc.opa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auto.service.AutoService;
import lombok.extern.jbosslog.JBossLog;
import org.keycloak.models.ClientSessionContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.models.UserSessionModel;
import org.keycloak.protocol.ProtocolMapper;
import org.keycloak.protocol.oidc.mappers.AbstractOIDCProtocolMapper;
import org.keycloak.protocol.oidc.mappers.OIDCAccessTokenMapper;
import org.keycloak.protocol.oidc.mappers.OIDCAttributeMapperHelper;
import org.keycloak.protocol.oidc.mappers.OIDCIDTokenMapper;
import org.keycloak.protocol.oidc.mappers.UserInfoTokenMapper;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.representations.IDToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Idea calling OPA for populating claims in Tokens or UserInfo-Endpoint
 *
 * OPA call requires input
 * - everything we have :), but configurable
 *
 * Result of OPA call is a JSON document
 * - success indicator attribute: decides weather the claim should be poulated, if empty, claim is always populated
 * - attribute to put into the claim specified with claim name, if empty, the response is put in as a whole
 */
@JBossLog
@AutoService(ProtocolMapper.class)
public class OpaResponseClaimMapper extends AbstractOIDCProtocolMapper implements OIDCAccessTokenMapper, OIDCIDTokenMapper, UserInfoTokenMapper {

    public static final String PROVIDER_ID = "oidc-opa-response-mapper";

    private static final List<ProviderConfigProperty> configProperties = new ArrayList<ProviderConfigProperty>();

    static {
        OIDCAttributeMapperHelper.addAttributeConfig(configProperties, OpaResponseClaimMapper.class);

        //OPA url

        //expressions to apply on a generic json object?

    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configProperties;
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String getDisplayType() {
        return "Open Policy Agent Response";
    }

    @Override
    public String getDisplayCategory() {
        return TOKEN_MAPPER_CATEGORY;
    }

    @Override
    public String getHelpText() {
        return "";
    }

    @Override
    protected void setClaim(IDToken token, ProtocolMapperModel mappingModel, UserSessionModel userSession, KeycloakSession session, ClientSessionContext clientSessionCtx) {

        // Build the OPA context
            // extract information from httpRequest as necessary, build a json object?
            // get all from the user, build a json object
            // apply configured json filters to actually forward to opa?
        Map<String,Object> input = new HashMap<>();
        OpaPolicyClient opaClient = new OpaPolicyClient();

        input.put("user", new OpaKeycloakUser(userSession.getUser()));
        input.put("context", new OpaKeycloakContext(session.getContext()));

        String opaPolicyUrl = "http://acme-opa:8181/v1/data/iam/keycloak/permissions";
        JsonNode opaPolicyResponse = opaClient.evaluatePolicy(session, opaPolicyUrl, input);

        if(opaPolicyResponse != null) {
            OIDCAttributeMapperHelper.mapClaim(token, mappingModel, opaPolicyResponse);
        }

    }

}


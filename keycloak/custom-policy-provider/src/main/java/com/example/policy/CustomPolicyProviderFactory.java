package com.example.policy;

import org.keycloak.authorization.policy.provider.PolicyProviderFactory;
import org.keycloak.authorization.policy.provider.PolicyProvider;
import org.keycloak.authorization.policy.provider.PolicyProviderAdminResource;
import org.keycloak.models.KeycloakSession;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.Collections;
import java.util.List;

public class CustomPolicyProviderFactory implements PolicyProviderFactory {

    @Override
    public String getId() {
        return "custom-policy";
    }

    @Override
    public String getName() {
        return "Custom Policy";
    }

    @Override
    public PolicyProvider create(KeycloakSession session) {
        return new CustomPolicyProvider();
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return Collections.emptyList();
    }

    @Override
    public PolicyProviderAdminResource getAdminResource() {
        return null;
    }
}
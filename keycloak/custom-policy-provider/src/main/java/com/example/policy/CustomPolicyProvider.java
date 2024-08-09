package com.example.policy;

import org.keycloak.authorization.policy.provider.PolicyProvider;
import org.keycloak.authorization.model.ResourcePermission;
import org.keycloak.authorization.policy.evaluation.Evaluation;
import org.keycloak.authorization.policy.evaluation.EvaluationContext;

public class CustomPolicyProvider implements PolicyProvider {

    @Override
    public void evaluate(Evaluation evaluation) {
        EvaluationContext context = evaluation.getContext();
        ResourcePermission permission = evaluation.getPermission();

        var tokenUserId = context.getIdentity().getId();
        var uri = permission.getClaims().get("uri_claim").iterator().next();
        var uriUserId = uri.split('/')[3];

        print("================================");
        print("Token User Id : " + tokenUserId);
        print("URI User Id : " + uriUserId);
        print("================================");

        // Custom logic here
        if (uriUserId.equals(tokenUserId)) {
            evaluation.grant();
        } else {
            evaluation.deny();
        }
    }

    @Override
    public void close() {
    }
}
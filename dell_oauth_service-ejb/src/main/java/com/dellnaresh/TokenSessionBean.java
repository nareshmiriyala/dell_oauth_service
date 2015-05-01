package com.dellnaresh;

import com.dellnaresh.entities.SecurityToken;
import com.dellnaresh.oauth.client.RemoteTokenSessionBean;
import com.dellnaresh.oauth.client.Token;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by nareshm on 1/05/2015.
 */
@Stateless(name = "TokenSessionBean")
public class TokenSessionBean implements RemoteTokenSessionBean,Serializable {
    @PersistenceContext(unitName = "test")
    private EntityManager entityManager;
    @Inject
    Validator validator;

    public TokenSessionBean() {
    }

    @Override
    public void createToken(@NotNull Token token) {
        validator.validate(token);
        SecurityToken securityToken=new SecurityToken();
        securityToken.setToken(token.getToken());
        securityToken.setExpiry(token.getExpiry());
        securityToken.setResourceOwner(token.getResourceOwner());
        securityToken.setRevision(token.getRevision());
        securityToken.setScope(token.getScope());
        securityToken.setTokenHolder(token.getTokenHolder());
        securityToken.setTokenType(token.getTokenType());
        entityManager.persist(securityToken);
    }
}

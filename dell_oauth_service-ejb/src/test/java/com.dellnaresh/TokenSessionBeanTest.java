package com.dellnaresh;

import com.dellnaresh.entities.SecurityToken;
import com.dellnaresh.oauth.client.RemoteTokenSessionBean;
import com.dellnaresh.oauth.client.Token;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by nareshm on 1/05/2015.
 * @see <http://arquillian.org/guides/testing_java_persistence/
 * @see <http://arquillian.org/guides/getting_started/
 * @see <https://docs.jboss.org/author/display/ARQ/WildFly+8.1.0+-+Embedded
 */
@RunWith(Arquillian.class)
public class TokenSessionBeanTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class,"test.war")
                .addClasses(TokenSessionBean.class, SecurityToken.class, RemoteTokenSessionBean.class,Token.class)
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("jbossas-ds.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

  @EJB
  RemoteTokenSessionBean tokenSessionBean;

    @Test
    public void testCreateToken() throws Exception {
        Token token=new Token();
        token.setResourceOwner(1234);
        token.setExpiry(new Timestamp(1234));
        token.setRevision(new Timestamp(333));
        token.setScope("test_scope");
        token.setTokenHolder("Naresh");
        token.setTokenType("CODE");
        token.setToken("12345678");
        tokenSessionBean.createToken(token);

    }
}
package cm.algaworks.ecommerce.iniciandocomjpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class EntityManagerTest extends EntityManagerFactoryTest {

    protected EntityManager em;

    @Before
    public void setUp() {
        em = entityManagerFactory.createEntityManager();
    }

    @After
    public void tearDown() {
        em.close();
    }
}

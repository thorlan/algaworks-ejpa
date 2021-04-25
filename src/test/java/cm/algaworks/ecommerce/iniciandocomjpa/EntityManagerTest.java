package cm.algaworks.ecommerce.iniciandocomjpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class EntityManagerTest {

	protected static EntityManagerFactory entityManagerFactory;

	protected EntityManager entityManager;

	// uma vez na classe, ao iniciar
	@BeforeClass
	public static void setUpBeforeClass() {
		entityManagerFactory = Persistence.createEntityManagerFactory("Ecommerce-PU");
	}

	// uma vez na classe, ao terminar
	@AfterClass
	public static void tearDownAfterClass() {
		entityManagerFactory.close();
	}

	// antes de cada teste
	@Before
	public void setUp() {
		entityManager = entityManagerFactory.createEntityManager();
	}

	// após de cada teste
	@After
	public void tearDown() {
		entityManager.close();
	}
	
}

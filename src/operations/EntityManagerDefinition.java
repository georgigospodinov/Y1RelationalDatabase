package operations;

import initializations.DefineProperties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Map;

/**
 * Two methods that return an appropriate entity manager.
 *
 * @author 150009974
 * @version 1.0
 */
public interface EntityManagerDefinition {

    static EntityManager emForValidation () {
        DefineProperties.setup ();

        Map<String, String> properties = DefineProperties.getPropertiesForTableValidation ();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory ( "PracticalThree", properties );
        return emf.createEntityManager ();
    }

    static EntityManager emForCreation () {
        DefineProperties.setup ();

        Map<String, String> properties = DefineProperties.getPropertiesForTableAutoCreation ();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory ( "PracticalThree", properties );
        return emf.createEntityManager ();
    }
}

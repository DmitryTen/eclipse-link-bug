package org.example.dao;

import org.example.model.TestTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Repository
public class TestTableDao {

    @Autowired
    EntityManager entityManager;

    @Transactional(readOnly = true)
    public TestTable findByName(String name) {
        TypedQuery<TestTable> query = entityManager.createQuery("select t from TestTable t where t.name = :name", TestTable.class);
        query.setParameter("name", name);

        return getSingleResultNullable(query);
    }

    private TestTable getSingleResultNullable(TypedQuery<TestTable> query) {
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public TestTable create(String name) {
        TestTable testTable = new TestTable();
        testTable.setName(name);
        entityManager.persist(testTable);
        return testTable;
    }

    @Transactional
    public void invalidBehaviourTest(String shouldNotBePersistedDueToError) {
        TestTable flushed = new TestTable();
        flushed.setName(shouldNotBePersistedDueToError);
        entityManager.persist(flushed);

        /**
         * The core issue is that flush actually does COMMIT!
         * Therefore it's not possible to provide rollback!!
         * */
        entityManager.flush();

        /**
         * not flushed value are not persisted after exception throw.
         * */
        TestTable notFlushed = new TestTable();
        notFlushed.setName("not flushed value");
        entityManager.persist(flushed);

        throw new IllegalStateException();
    }
}

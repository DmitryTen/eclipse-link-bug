This project is created only to show eclipse-link bug which happens during entityManager.flush() invocation.

entityManager.flush() leads to commit, which shouldn't be the case. Or i misunderstand smth.

see the EclipseLinkTest.java

As test DB is used H2, it could be found in the target directory, see application.properties

issue: https://github.com/eclipse-ee4j/eclipselink/issues/2059
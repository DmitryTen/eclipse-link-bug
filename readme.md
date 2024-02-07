This project is created only to show eclipse-link bug which happens during entityManager.flush() invocation.

entityManager.flush() leads to commit, which shouldn't be the case. Or i misunderstand smth.

see the EclipseLinkTest.java
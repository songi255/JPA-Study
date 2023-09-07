package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        // persistence.xml 로 팩토리 생성한다.
        // 팩토리 생성 시 JPA 동작기반객체를 만들고, JPA 구현체(Hibernate 포함)에 따라 DB connection pool도 생성하므로 팩토리 생성비용은 아주 크다.
        // J2EE환경(Spring 포함)에서 사용하면 해당 Container가 제공하는 데이터소스를 사용한다.
        // 그래서 1번만 생성하고 공유사용해야 한다. 스레드에 안전하다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        // JPA 기능 대부분은 Entity Manager가 제공한다. (CRUD같은)
        // 내부에 커넥션 풀을 유지하므로, 가상의 DB로 생각할 수 있다.
        // Connection과 밀접한 관계가 있으므로, Thread간 공유 혹은 재사용하면 안된다.
        EntityManager em = emf.createEntityManager(); // em 생성은 비용이 거의 안든다.

        // em은 DB에 lazy연결한다. 예를들어 아래처럼 transaction을 시작할 때.
        EntityTransaction tx = em.getTransaction();

        try {
            // transaction 없이 데이터를 변경하면 예외발생
            tx.begin();
            logic(em);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            // 꼭 꺼주자.
            em.close();
        }

        // 꼭 꺼주자.
        emf.close();
    }

    private static void logic(EntityManager em) {
        String id = "id1";
        Member member = new Member();
        member.setId(id);
        member.setUsername("지한");
        member.setAge(2);

        // 저장
        em.persist(member);

        // 수정
        member.setAge(20);

        // 1건 조회
        Member findMember = em.find(Member.class, id); // find는 가장 단순한 조회메서드다.
        System.out.println("findMember=" + findMember.getUsername() + ", age=" + findMember.getAge());

        // 목록 조회
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("members.size=" + members.size());
        // 위에서 사용한 Query는 JPQL(Java Persistence Query Language)이다. "검색" 쿼리인데, Table이 아닌 Entity 객체를 대상으로 검색한다.
        // 객체지향 쿼리언어이다. SQL과 거의 유사하다. 가장 큰 차이점은 Table 대상이 아닌 Entity 객체를 대상으로 쿼리한다.
        // from Member에서 Member는 Table이 아닌 Entity Class를 의미한다. JPQL은 DB Table을 전혀 알지 못한다.

        // 삭제
        em.remove(member);
    }
}
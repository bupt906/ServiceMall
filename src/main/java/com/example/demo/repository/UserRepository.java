package com.example.demo.repository;

import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * JPA Auto SQL
     * Use Username Find User
     */
    User findUserByUserName(String username);

    User findQuestionByUserName(String username);

    User findByUserNameAndQuestionAndAnswer(String username, String question, String answer);

    @Transactional
    @Modifying
    @Query(value = "update user u set u.pass_word=:passWord where u.user_name=:username", nativeQuery = true)
    int updatePassWordByUserName(@Param("passWord") String passWord, @Param("username") String username);

}

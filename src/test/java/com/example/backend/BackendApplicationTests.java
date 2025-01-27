package com.example.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;  // JdbcTemplateを使用してデータベース接続を確認

    @Test
    void contextLoads() {
        // データベース接続確認
        assertNotNull(jdbcTemplate, "JdbcTemplate should be autowired and not null");
    }

    @Test
    void databaseConnectionTest() {
        // データベース接続確認のため、単純なクエリを実行
        Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        assertNotNull(result, "Query result should not be null");
        assert(result == 1);  // クエリ結果が1であることを確認
    }
}

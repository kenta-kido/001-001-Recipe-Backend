package com.example.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BackendApplicationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;  // JdbcTemplateをインジェクト

    // @BeforeEachでデータベース接続の準備
    @BeforeEach
    void setupDatabaseConnection() {
        // データベース接続確認のため、単純なクエリを実行
        Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        assertNotNull(result, "Query result should not be null");
        assertEquals(1, result, "Query result should be 1");
    }

    @Test
    void contextLoads() {
        // アプリケーションコンテキストが正常に読み込まれることを確認するテスト
    }

    @Test
    void databaseConnectionTest() {
        // データベース接続確認のため、単純なクエリを実行
        Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        assertNotNull(result, "Query result should not be null");
        assert(result == 1);  // クエリ結果が1であることを確認
    }
}

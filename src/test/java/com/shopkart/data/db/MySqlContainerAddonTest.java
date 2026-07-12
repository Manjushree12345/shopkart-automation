package com.shopkart.data.db;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Optional, isolated database check. It never reads the developer's local MySQL settings. */
@Tag("container")
final class MySqlContainerAddonTest {
    @Test
    void starts_a_disposable_database_and_applies_the_public_seed() throws Exception {
        var mysql = MySqlSupport.start();
        try (var connection = DriverManager.getConnection(mysql.getJdbcUrl(), mysql.getUsername(), mysql.getPassword());
             var statement = connection.prepareStatement("SELECT COUNT(*) FROM products WHERE sku = ?")) {
            statement.setString(1, "SKU-BAG");
            try (var result = statement.executeQuery()) {
                result.next();
                assertEquals(1, result.getInt(1));
            }
        }
    }
}

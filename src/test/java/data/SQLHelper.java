package data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLHelper {
    private static QueryRunner runner = new QueryRunner();

    private static final String url = System.getProperty("db.url");

    private SQLHelper(){};

    @SneakyThrows
    public static Connection getConn(){
       return DriverManager.getConnection(url, "app", "pass");
    }

    @SneakyThrows
    public static void cleanDatabase() {
        var connection = getConn();
        runner.execute(connection, "DELETE FROM credit_request_entity");
        runner.execute(connection, "DELETE FROM order_entity");
        runner.execute(connection, "DELETE FROM payment_entity");
    }

    @SneakyThrows
    public static String getDebitPaymentStatus() {
        String SqlStatus = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        return getResult(SqlStatus);
    }
    @SneakyThrows
    public static String getCreditPaymentStatus() {
        String SqlStatus = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
       return getResult(SqlStatus);
    }

    @SneakyThrows
    private static String getResult(String query) {
        String result = "";
        var runner = new QueryRunner();
        try (var connection = getConn()) {
            result = runner.query(connection, query, new ScalarHandler<>());
        }
        return result;
    }
}

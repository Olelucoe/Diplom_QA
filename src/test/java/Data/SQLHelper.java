package Data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLHelper {
    private static QueryRunner runner = new QueryRunner();

    private static String url = System.getProperty("db.url");
    private static String user = System.getProperty("db.user");
    private static String password = System.getProperty("db.password");

    private SQLHelper(){};

    @SneakyThrows
    private static Connection getConn(){
       return DriverManager.getConnection(url, user, password);
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
        QueryRunner runner = new QueryRunner();
        String SqlStatus = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        try (var connection = getConn()) {
            String result = runner.query(connection, SqlStatus, new ScalarHandler<>());
            return result;
        }
    }
    @SneakyThrows
    public static String getCreditPaymentStatus() {
        QueryRunner runner = new QueryRunner();
        String SqlStatus = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        try (var connection = getConn()) {
            String result = runner.query(connection, SqlStatus, new ScalarHandler<>());
            return result;
        }
    }
}

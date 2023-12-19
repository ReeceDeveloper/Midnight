/*
 * The MIT License (MIT)
 *
 * Copyright © 2023 - ReeceDeveloper
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
 * to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package reecedeveloper.com.github.managers;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import reecedeveloper.com.github.configuration.Configuration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {
    private static HikariDataSource hikariDataSource;

    public DatabaseManager() {
        initializeDatabase();
    }

    private void initializeDatabase() {
        Configuration configInstance = Configuration.getConfigInstance();

        final String DB_URL = configInstance.optString("dbUrl");
        final String DB_USER = configInstance.optString("dbUser");
        final String DB_PASS = configInstance.optString("dbPass");

        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(DB_URL);
        hikariConfig.setUsername(DB_USER);
        hikariConfig.setPassword(DB_PASS);

        hikariDataSource = new HikariDataSource(hikariConfig);
    }

    // TODO: Ideally this can be removed to avoid exposing the hikariDataSource to other classes.
    public static HikariDataSource getHikariDataSource() {
        return hikariDataSource;
    }

    public int executeUpdate(String sqlQuery, Object[] parameters) {
        try (Connection hikariConnection = getHikariDataSource().getConnection();
             PreparedStatement preparedStatement = hikariConnection.prepareStatement(sqlQuery)) {

            setParameters(preparedStatement, parameters);

            if (preparedStatement.execute()) {
                return preparedStatement.getUpdateCount();
            } else {
                return -1; // TODO: Indicate success for non-SELECT queries.
            }
        } catch (SQLException sqlUpdateError) {
            return -2; // TODO: Indicate whole failure.
        }
    }

    public ResultSet executeQuery(String sqlQuery, Object[] parameters) {
        try (Connection hikariConnection = getHikariDataSource().getConnection();
             PreparedStatement preparedStatement = hikariConnection.prepareStatement(sqlQuery)) {

            setParameters(preparedStatement, parameters);

            return preparedStatement.executeQuery(); // TODO: This should work as-is, but there may be edge cases.
        } catch (SQLException sqlQueryError) {
            return null; // TODO: Indicate failure.
        }
    }

    /////////////////////
    // HELPER FUNCTION //
    /////////////////////

    private static void setParameters(PreparedStatement preparedStatement, Object[] parameters) throws SQLException {
        if (parameters != null) {
            for (int idx = 0; idx < parameters.length; ++idx) {
                preparedStatement.setObject((idx + 1), parameters[idx]);
            }
        }
    }
}

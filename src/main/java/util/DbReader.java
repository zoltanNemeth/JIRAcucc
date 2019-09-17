package util;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.shyiko.dotenv.DotEnv;

public class DbReader {
    private static final String DATABASE = "jdbc:postgresql://173.212.197.253:54301/jira_cucc";
    private static final String DB_USER = System.getProperty("DB_USER");
    private static final String DB_PASSWORD = System.getProperty("DB_PWD");

    public DbReader() {

    }

    // Database general: -----------------------------------------------------
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                DB_USER,
                DB_PASSWORD);
    }

    public static List<Map> getCredentials() {
        return getAll("credentials");

    }
    public static List<Map> getProjects() {
        return getAll("project");

    }
    public static List<Map> getProjectIssues() {
        ArrayList<Map> projectIssues = new ArrayList<Map>();

        List<Map> projects = getAll("project");
        for (Map project: projects
             ) {
            String projectName = project.get("project_name").toString();
            for (int i = 1; i <= 3 ; i++) {
                Map<String,String> record = new HashMap<String, String>();
                String issue = projectName + "-" + i;
                record.put("project_name", projectName);
                record.put("issue", issue);
                projectIssues.add(record);
            }
        }
        return projectIssues;
    }

    public static List<Map> getInvalidCredentials() {
        return getAll("invalid_credentials");
    }

    public static List<Map>getIssues(){

        return getAll("issue");
    }

    public static List<Map> getAll(String tableName) {
        String query = "SELECT * FROM " + tableName + ";";

        List<Map> resultList = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ){
            while (resultSet.next()){
                Map<String, String> fields = new HashMap<String, String>();
                ResultSetMetaData rsmd = resultSet.getMetaData();
                int ColumnCount = rsmd.getColumnCount();
                for (int i = 1; i <= ColumnCount ; i++) {
                    String columnName = rsmd.getColumnName(i);
                    fields.put(columnName, resultSet.getString(columnName));

                }
                resultList.add(fields);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;

    }

    private void executeQuery(String query) {
        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
        ){
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

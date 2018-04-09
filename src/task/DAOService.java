package task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*this class is for DB interaction*/
public class DAOService {
    // JDBC URL, username and password of PosgreSQL server
    private static final String url = "jdbc:postgresql://localhost/test";
    private static final String user = "root";
    private static final String password = "root";

    public DAOService() {

    }

    /*get a list of company's users by company name from DB*/
    List<String> findBy(String searchPhrase) {
        List<String> searchResult = new ArrayList<String>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DAOService daoService = null;
        try {

            daoService = new DAOService();
            connection = daoService.getConnection();
            preparedStatement = connection.prepareStatement("Select u.id from users u, customers c where c.id = u.customer_id and c.name = ?");
            preparedStatement.setString(1, searchPhrase );
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                searchResult.add(resultSet.getString("FOUND"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            daoService.closeConnections(resultSet, preparedStatement, connection);

        }
        return searchResult;
    }



    public Connection getConnection () {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection(Connection connection) {
        try{

            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void closeResultSet(ResultSet rs) {
        try{

            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void closePreparedStatement (PreparedStatement preparedStatement) {
        try{

            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void closeConnections (ResultSet rs, PreparedStatement preparedStatement,
                                  Connection conn)  {
        closeResultSet(rs);
        closePreparedStatement(preparedStatement);
        closeConnection(conn);
    }

}

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {

    public static void topEconomicalBowlersIn2015() throws Exception{
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/IPL_Project", "postgres", "V!n@y10234" );
        String query = "SELECT bowler, ROUND((SUM(total_runs - bye_runs - legbye_runs) / (COUNT(CASE WHEN (wide_runs = 0 AND noball_runs =0) THEN 1 ELSE null END) / 6.0)),2) AS economy_rate FROM deliveries INNER JOIN matches ON deliveries.match_id = matches.id WHERE season = '2015' GROUP BY bowler ORDER BY economy_rate ASC";
        Statement state = con.createStatement();
        ResultSet result = state.executeQuery(query);
        while(result.next()) {
            System.out.println("Bowler: " + result.getString("bowler") + ", Economy rate: " + result.getInt("economy_rate"));
        }
        System.out.println("********************");
    }

    public static void extraRunsConcededPerTeamIn2016() throws Exception{
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/IPL_Project", "postgres", "V!n@y10234" );
        String query = "SELECT bowling_team, SUM(extra_runs) AS extra_runs FROM deliveries INNER JOIN matches ON deliveries.match_id = matches.id WHERE season = '2016' GROUP BY bowling_team ORDER BY extra_runs ASC";
        Statement state = con.createStatement();
        ResultSet result = state.executeQuery(query);
        while(result.next()) {
            System.out.println("Team: " + result.getString("bowling_team") + ", Extra runs conceded: " + result.getInt("extra_runs"));
        }
        System.out.println("********************");
    }

    public static void numberOfMatchesWonByAllTeams() throws Exception{
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/IPL_Project", "postgres", "V!n@y10234" );
        String query = "SELECT winner, count(*) AS count FROM matches WHERE winner IS NOT null GROUP BY winner";
        Statement state = con.createStatement();
        ResultSet result = state.executeQuery(query);
        while(result.next()) {
            System.out.println("Team: " + result.getString("winner") + ", No of matches won:  " + result.getInt("count"));
        }
        System.out.println("********************");
    }

    public static void findNumberOfMatchesPlayedPerYear() throws Exception{
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/IPL_Project", "postgres", "V!n@y10234" );
        String query = "SELECT season, count(*) AS count FROM matches GROUP BY season ORDER BY season ASC";
        Statement state = con.createStatement();
        ResultSet result = state.executeQuery(query);
        while(result.next()) {
            System.out.println("Year: " + result.getString("season") + ", No of matches played: " + result.getInt("count"));
        }
        System.out.println("********************");
    }

    public static void main(String[] args) throws Exception{
        findNumberOfMatchesPlayedPerYear();
        numberOfMatchesWonByAllTeams();
        extraRunsConcededPerTeamIn2016();
        topEconomicalBowlersIn2015();
    }
}
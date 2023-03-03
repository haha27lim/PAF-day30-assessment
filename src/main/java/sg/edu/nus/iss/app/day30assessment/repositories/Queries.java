package sg.edu.nus.iss.app.day30assessment.repositories;

public class Queries {
    public static final String SQL_SELECT_ALL_ACCOUNTS= "SELECT * FROM accounts ORDER BY name";
    public static final String SQL_SELECT_ACCOUNTS_BY_ID= "SELECT * FROM accounts WHERE account_id = ? ORDER BY name";
    public static final String SQL_UPDATE_ACCOUNTS_BY_ID= "UPDATE accounts SET name = ?, balance = ? WHERE account_id = ?";
}

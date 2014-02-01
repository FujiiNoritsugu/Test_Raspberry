package test.rasp.dao;

import java.sql.*;

public class DaoTest {

	Connection con = null;
	
	public DaoTest(){
        try {
            // JDBCドライバの登録
            String driver = "org.postgresql.Driver";
            // データベースの指定
            String server   = "localhost";   // PostgreSQL サーバ ( IP または ホスト名 )
            String dbname   = "my_m2m";         // データベース名
            String url = "jdbc:postgresql://" + server + "/" + dbname;
            String user     = "postgres";         //データベース作成ユーザ名
            String password = "my_m2m25";     //データベース作成ユーザパスワード
            Class.forName (driver);
            // データベースとの接続
            con = DriverManager.getConnection(url, user, password);
        }catch(Exception e){
        	e.printStackTrace();
        }
	}
	
	public void insertData(int value){
		try {
			String sql = "insert into measure_data (measure_time, measure_data) values (current_time, ?);";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, value);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close(){
		if(con != null){
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}

}

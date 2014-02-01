package test.rasp.dao;

import java.sql.*;

public class DaoTest {

	Connection con = null;
	
	public DaoTest(){
        try {
            // JDBC�h���C�o�̓o�^
            String driver = "org.postgresql.Driver";
            // �f�[�^�x�[�X�̎w��
            String server   = "localhost";   // PostgreSQL �T�[�o ( IP �܂��� �z�X�g�� )
            String dbname   = "my_m2m";         // �f�[�^�x�[�X��
            String url = "jdbc:postgresql://" + server + "/" + dbname;
            String user     = "postgres";         //�f�[�^�x�[�X�쐬���[�U��
            String password = "my_m2m25";     //�f�[�^�x�[�X�쐬���[�U�p�X���[�h
            Class.forName (driver);
            // �f�[�^�x�[�X�Ƃ̐ڑ�
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

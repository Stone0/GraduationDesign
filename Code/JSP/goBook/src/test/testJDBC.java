package test;

import java.sql.Connection;

import com.stone0.gobook.util.DBUtils;

public class testJDBC {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		Connection con = DBUtils.openConnection();
		
		if(con!=null)
		{
			System.out.println("Success!");
		}
		else
		{
			System.out.println("Error");
		}
		
		DBUtils.close(con);

	}

}

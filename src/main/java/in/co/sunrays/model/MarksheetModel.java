package in.co.sunrays.model;

import java.sql.Connection;

import java.sql.DriverManager;
import in.co.sunrays.beans.MarksheetBean;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MarksheetModel {

	public Connection getConnection() {
		Connection con = null;
		try {
			ResourceBundle rbObj = ResourceBundle.getBundle("config");
			Class.forName(rbObj.getString("driver"));
			con = DriverManager.getConnection(rbObj.getString("url"), rbObj.getString("username"),
					rbObj.getString("password"));
			con.setAutoCommit(false);
		} catch (Exception e) {

		}
		return con;
	}

	public long nextPk(String tableName, String columnName) {
		long pk = 0;
		PreparedStatement pstmt = null;
		try {
			Connection con = getConnection();
			pstmt = con.prepareStatement("SELECT MAX(" + columnName + ") FROM " + tableName + ";");
			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				pk = resultSet.getLong("max(id)") + 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return pk;
	}

	public void add(MarksheetBean bean) {

		PreparedStatement pstmt = null;
		try {
			Connection con = getConnection();
			pstmt = con.prepareStatement("insert into marksheet values (?,?,?,?,?,?,?,?,?,?,?);");
			pstmt.setLong(1, nextPk("marksheet", "id"));
			pstmt.setString(2, bean.getRollNo());
			pstmt.setLong(3, bean.getStudentId());
			pstmt.setString(4, bean.getName());
			pstmt.setInt(5, bean.getPhyMarks());
			pstmt.setInt(6, bean.getChemMarks());
			pstmt.setInt(7, bean.getMathMarks());
			
			pstmt.setString(8, null);
			pstmt.setString(9, null);
			pstmt.setDate(10, null);
			pstmt.setDate(11, null);

			int recordCount = pstmt.executeUpdate();
			con.commit();
			System.out.println("successfully inserted " + recordCount + " record");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

	}

	public void delete(int stuRollNo) {
		PreparedStatement pstmt = null;
		try {
			Connection con = getConnection();
			pstmt = con.prepareStatement("delete from marksheet where student_id=" + stuRollNo + ";");
			int recordCount = pstmt.executeUpdate();
			System.out.println("successfully deleted " + recordCount + " record");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

	}

	public void testfindByRollNo(int stuRollNo) {

		PreparedStatement pstmt = null;
		try {
			Connection con = getConnection();
			pstmt = con.prepareStatement("select *  from marksheet where student_id=" + stuRollNo + ";");
			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {

				String roll_no = resultSet.getString("roll_no");
				long student_id = resultSet.getLong("student_id");
				String student_name = resultSet.getString("student_name");
				int phy_marks = resultSet.getInt("phy_marks");
				int chem_marks = resultSet.getInt("chem_marks");
				int math_marks = resultSet.getInt("math_marks");

				System.out.println("roll_no  " + roll_no);
				System.out.println("student_id  " + student_id);
				System.out.println("student_name  " + student_name);
				System.out.println("phy_marks  " + phy_marks);
				System.out.println("chem_marks  " + chem_marks);
				System.out.println("math_marks  " + math_marks);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

	public void update(MarksheetBean bean) {

		PreparedStatement pstmt = null;
		try {

			long pk = getpk(bean);
			Connection con = getConnection();
			pstmt = con.prepareStatement(
					"UPDATE MARKSHEET SET ROLL_NO=?, STUDENT_ID= ?, STUDENT_NAME=?, PHY_MARKS=?, CHEM_MARKS=?, MATH_MARKS=? WHERE ID = ? ;");
			pstmt.setString(1, bean.getRollNo());
			pstmt.setLong(2, bean.getStudentId());
			pstmt.setString(3, bean.getName());
			pstmt.setInt(4, bean.getPhyMarks());
			pstmt.setInt(5, bean.getChemMarks());
			pstmt.setInt(6, bean.getMathMarks());
			pstmt.setLong(7, pk);
			int recordCount = pstmt.executeUpdate();
			con.commit();
			System.out.println("successfully updated " + recordCount + " record");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

	}

	private long getpk(MarksheetBean bean) {
		PreparedStatement pstmt = null;
		long id = 0;
		try {
			Connection con = getConnection();
			pstmt = con.prepareStatement("SELECT ID FROM marksheet WHERE STUDENT_ID =?;");
			pstmt.setLong(1, bean.getStudentId());
			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {

				id = resultSet.getLong("id");
				System.out.println("roll_no  " + id);
			}
			return id;

		} catch (Exception e) {
			e.printStackTrace();
			return id;
		} finally {
			try {
				pstmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

	public void testfindByPk(MarksheetBean bean) {
		getpk(bean);
	}

	public ArrayList<MarksheetBean> testList() {
		PreparedStatement pstmt = null;
		ArrayList<MarksheetBean> list = new ArrayList<MarksheetBean>();
		try {
			Connection con = getConnection();
			pstmt = con.prepareStatement("SELECT * FROM MARKSHEET;");
			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				
				MarksheetBean bean = new MarksheetBean();
				String roll_no = resultSet.getString("roll_no");
				long student_id = resultSet.getLong("student_id");
				String student_name = resultSet.getString("student_name");
				int phy_marks = resultSet.getInt("phy_marks");
				int chem_marks = resultSet.getInt("chem_marks");
				int math_marks = resultSet.getInt("math_marks");

				System.out.println("roll_no  " + roll_no);
				System.out.println("student_id  " + student_id);
				System.out.println("student_name  " + student_name);
				System.out.println("phy_marks  " + phy_marks);
				System.out.println("chem_marks  " + chem_marks);
				System.out.println("math_marks  " + math_marks);
				
				bean.setRollNo(resultSet.getString("roll_no"));
				bean.setStudentId(resultSet.getLong("student_id"));
				bean.setName(resultSet.getString("student_name"));
				bean.setPhyMarks(resultSet.getInt("phy_marks"));
				bean.setChemMarks(resultSet.getInt("chem_marks"));
				bean.setMathMarks(resultSet.getInt("math_marks"));
				
				list.add(bean);
				
				
			}
			return list;

		} catch (Exception e) {
			e.printStackTrace();
			return list ;
		} finally {
			try {
				pstmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		
	}

	public ArrayList<MarksheetBean> testSearch(String columnName,String value) {
		
		PreparedStatement pstmt = null;
		ArrayList<MarksheetBean> list = new ArrayList<MarksheetBean>();
		try {
			
			Connection con = getConnection();
			String query = "SELECT * FROM MARKSHEET WHERE " + columnName + "='" + value +"';";
			pstmt = con.prepareStatement(query);
			//pstmt = con.prepareStatement("SELECT * FROM MARKSHEET WHERE ? = ?;");
		//	pstmt.setString(1, columnName);
			//pstmt.setString(2, value);
			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				
				MarksheetBean bean = new MarksheetBean();
				String roll_no = resultSet.getString("roll_no");
				long student_id = resultSet.getLong("student_id");
				String student_name = resultSet.getString("student_name");
				int phy_marks = resultSet.getInt("phy_marks");
				int chem_marks = resultSet.getInt("chem_marks");
				int math_marks = resultSet.getInt("math_marks");

				System.out.println("roll_no  " + roll_no);
				System.out.println("student_id  " + student_id);
				System.out.println("student_name  " + student_name);
				System.out.println("phy_marks  " + phy_marks);
				System.out.println("chem_marks  " + chem_marks);
				System.out.println("math_marks  " + math_marks);
				
				bean.setRollNo(resultSet.getString("roll_no"));
				bean.setStudentId(resultSet.getLong("student_id"));
				bean.setName(resultSet.getString("student_name"));
				bean.setPhyMarks(resultSet.getInt("phy_marks"));
				bean.setChemMarks(resultSet.getInt("chem_marks"));
				bean.setMathMarks(resultSet.getInt("math_marks"));
				
				list.add(bean);
				
				
			}
			return list;

		} catch (Exception e) {
			e.printStackTrace();
			return list ;
		} finally {
			try {
				pstmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		
	}

	public ArrayList<MarksheetBean> testMeritList() {
		
		PreparedStatement pstmt = null;
		ArrayList<MarksheetBean> list = new ArrayList<MarksheetBean>();
		try {
			
			Connection con = getConnection();
			//String query = "SELECT ID,ROLL_NO,STUDENT_ID,PHY_MARKS, CHEM_MARKS, MATH_MARKS, PHY_MARKS + CHEM_MARKS + MATH_MARKS AS TOTAL, ((PHY_MARKS + CHEM_MARKS + MATH_MARKS)/300)*100 AS PERCENTAGE FROM MARKSHEET WHERE (((PHY_MARKS + CHEM_MARKS + MATH_MARKS)/300)*100) > 40 ORDER BY PERCENTAGE DESC;";
			//String query = "SELECT ID,ROLL_NO,STUDENT_ID,PHY_MARKS, CHEM_MARKS, MATH_MARKS, PHY_MARKS + CHEM_MARKS + MATH_MARKS AS TOTAL, ((TOTAL)/300)*100 AS PERCENTAGE FROM MARKSHEET WHERE PERCENTAGE > 40 ORDER BY PERCENTAGE DESC;";			
			String query = "SELECT ID,ROLL_NO,STUDENT_ID,PHY_MARKS, CHEM_MARKS, MATH_MARKS, PHY_MARKS + CHEM_MARKS + MATH_MARKS AS TOTAL, (PHY_MARKS + CHEM_MARKS + MATH_MARKS)/100 AS PERCENTAGE FROM MARKSHEET;";
			
			pstmt = con.prepareStatement(query);
			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				
				MarksheetBean bean = new MarksheetBean();
				String roll_no = resultSet.getString("roll_no");
				long student_id = resultSet.getLong("student_id");
			//	String student_name = resultSet.getString("student_name");
				int phy_marks = resultSet.getInt("phy_marks");
				int chem_marks = resultSet.getInt("chem_marks");
				int math_marks = resultSet.getInt("math_marks");

				System.out.println("roll_no  " + roll_no);
				System.out.println("student_id  " + student_id);
				//System.out.println("student_name  " + student_name);
				System.out.println("phy_marks  " + phy_marks);
				System.out.println("chem_marks  " + chem_marks);
				System.out.println("math_marks  " + math_marks);
				
				bean.setRollNo(resultSet.getString("roll_no"));
				bean.setStudentId(resultSet.getLong("student_id"));
				//bean.setName(resultSet.getString("student_name"));
				bean.setPhyMarks(resultSet.getInt("phy_marks"));
				bean.setChemMarks(resultSet.getInt("chem_marks"));
				bean.setMathMarks(resultSet.getInt("math_marks"));
				
				list.add(bean);
				
				
			}
			return list;

		} catch (Exception e) {
			e.printStackTrace();
			return list ;
		} finally {
			try {
				pstmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		

	}

}

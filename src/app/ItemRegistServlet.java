package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class ItemRegistServlet
 */
@WebServlet("/ItemRegistServlet")
public class ItemRegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ItemRegistServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 商品コード
		String itemCd = request.getParameter("itemCd");
		// 商品名
		String itemName = request.getParameter("itemName");
		// 商品名カナ
		String itemNameKana = request.getParameter("itemNamekana");
		// 価格
		String salesPrice = request.getParameter("salesPrice");
		//
		String stock = request.getParameter("stock");
		// 画像URL
		String imageUrl = request.getParameter("imageUrl");
		// 商品説明
		String description = request.getParameter("description");

		// JDBCドライバの準備
		try {
			// JDBCドライバのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// ドライバが設定されていない場合はエラーになります
			throw new RuntimeException(String.format("JDBCドライバのロードに失敗しました。詳細:[%s]", e.getMessage()), e);
		}

		// データベースにアクセスするために、データベースのURLとユーザ名とパスワードを指定
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "webapp";
		String pass = "webapp";

		// 実行するSQL文
		String sql = "insert into MS_ITEM \n"
				+ "(ITEM_CD, NAME, NAME_KANA, DESCRIPTION, URL, SALES_PRICE, TAX_TYPE,ITEM_GROUP_CD, STOCK) \n"
				+ "values \n" + "('" + itemCd + "','" + itemName + "','" + itemNameKana + "','" + description + "','"
				+ imageUrl + "','" + salesPrice + "','01','01','" + stock + "') ";
		;

		// エラーが発生するかもしれない処理はtry-catchで囲みます
		// この場合はDBサーバへの接続に失敗する可能性があります
		try (
				// データベースへ接続します
				Connection con = DriverManager.getConnection(url, user, pass);
				// SQLの命令文を実行するための準備をおこないます
				Statement stmt = con.createStatement();
			) {
			// SQLの命令文を実行し、その件数をint型のresultCountに代入します
			int resultCount = stmt.executeUpdate(sql);

		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
		}

		// アクセスした人に応答するためのJSONを用意する
		PrintWriter pw = response.getWriter();
		// JSONで出力する
		pw.append(new ObjectMapper().writeValueAsString("ok"));
	}

}

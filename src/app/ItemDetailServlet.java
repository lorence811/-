package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class ItemDetailServlet
 */
@WebServlet("/ItemDetailServlet")
public class ItemDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// アクセス元のHTMLでitemCdに設定された値を取得して、String型の変数itemCdに代入
		String itemCd = request.getParameter("itemCd");
		System.out.println("itemCd="+itemCd);

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
		String sql = "select \n" +
				"	mi.ITEM_CD, \n" +
				"	mi.NAME, \n" +
				"	mi.NAME_KANA, \n" +
				"	mi.DESCRIPTION, \n" +
				"	mi.URL, \n" +
				"	mi.SALES_PRICE,  \n" +
				"	mi.TAX_TYPE, \n" +
				"	mi.ITEM_GROUP_CD, \n" +
				"	mi.STOCK \n" +
				"from \n" +
				"	MS_ITEM  mi \n" +
				"where \n" +
				"  mi.ITEM_CD = '"+itemCd+"' \n";
		Item item = new Item();
		// エラーが発生するかもしれない処理はtry-catchで囲みます
		// この場合はDBサーバへの接続に失敗する可能性があります
		try (
			// データベースへ接続します
			Connection con = DriverManager.getConnection(url, user, pass);
			// SQLの命令文を実行するための準備をおこないます
			Statement stmt = con.createStatement();
			// SQLの命令文を実行し、その結果をResultSet型のrsに代入します
			ResultSet rs1 = stmt.executeQuery(sql);
		) {
			// SQL実行後の処理内容
			// SQL実行結果を保持している変数rsから商品情報を取得
			// rs.nextは取得した商品情報表に次の行があるとき（取得結果があるとき）、trueになり、if文の中が実行される
			// 次の行がないときはfalseになり、実行されない
			if (rs1.next()) {
				item.setItemCd(rs1.getString("ITEM_CD")); // Item型の変数itemに商品コードをセット
				item.setItemName(rs1.getString("NAME"));// Item型の変数itemに商品名をセット
				item.setItemNameKana(rs1.getString("NAME_KANA"));// Item型の変数itemに商品名カナをセット
				item.setDescription(rs1.getString("DESCRIPTION"));// Item型の変数itemに商品説明をセット
				item.setUrl(rs1.getString("URL"));// Item型の変数itemに画像URLをセット
				item.setSalesPrice(rs1.getInt("SALES_PRICE"));// Item型の変数itemに販売単価をセット
				item.setTaxType(rs1.getString("TAX_TYPE"));// Item型の変数itemに税区分をセット
				item.setItemGroupCode(rs1.getString("ITEM_GROUP_CD"));// Item型の変数itemに商品分類コードをセット
				item.setStock(rs1.getInt("STOCK"));//Item型の変数itemに在庫をセット
			}

		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
		}

		// アクセスした人に応答するためのJSONを用意する
		PrintWriter pw = response.getWriter();
		// JSONで出力する
		pw.append(new ObjectMapper().writeValueAsString(item));

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

package practice;

public class JavaPractice {

	public static void main(String[] args) {
		// 「String」は文字列を保持するための型です
		String itemName = "リンゴ";
		// 「int」は整数を保持するための型です
		int stock = 10;
		// System.out.println(<出力したい文字列>)はコンソールに文字を出力するためのメソッドです。
		// 「+」を使用すると複数の文字列を結合できます。
		System.out.println(itemName + "は" + stock + "個あります。");

		// stockが10以上なら「まだまだ余裕があるよ」、10未満5以上なら「在庫が減ってきたよ」、5未満ならば「残りわずか！」と表示する
		if(stock >= 10){
			System.out.println("まだまだ余裕があるよ");
		}else if(stock < 10 && stock >=5){
			System.out.println("在庫が減ってきたよ");
		}else{
			System.out.println("残りわずか！");
		}

	}

}

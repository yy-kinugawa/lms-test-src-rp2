package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * 結合テスト レポート機能
 * ケース09
 * @author 絹川
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		//トップ画面遷移
		goTo("http://localhost:8080/lms");
		//エビデンス取得
		getEvidence(new Object() {
		});
		//画面タイトルチェック
		assertThat(webDriver.getTitle(), is(containsString("ログイン")));
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		//ログインIDを入力
		WebElement loginId = webDriver.findElement(By.name("loginId"));
		loginId.clear();
		loginId.sendKeys("StudentAA01");
		//パスワードを入力
		WebElement password = webDriver.findElement(By.name("password"));
		password.clear();
		password.sendKeys("ItTest2025");
		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		//「ログイン」ボタンを押下
		WebElement loginButton = webDriver.findElement(By.xpath("//*[@value='ログイン']"));
		loginButton.click();
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//画面タイトルチェック
		assertThat(webDriver.getTitle(), is(containsString("コース詳細")));
		//ユーザー表示のチェック
		WebElement showUserName = webDriver.findElement(By.tagName("small"));
		assertEquals("ようこそ受講生ＡＡ１さん", showUserName.getText());
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {
		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		//リンクをクリック
		WebElement userDetailLink = webDriver.findElement(By.xpath("//*[@id=\"nav-content\"]/ul[2]/li[2]/a/small"));
		userDetailLink.click();
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//画面タイトルチェック
		assertThat(webDriver.getTitle(), is(containsString("ユーザー詳細")));
		//ユーザー名チェック
		WebElement userName = webDriver.findElement(By.xpath("//*[@id=\"main\"]/table[1]/tbody/tr[1]/td"));
		assertEquals("受講生ＡＡ１", userName.getText());
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		//画面下部にスクロール
		scrollBy("900");
		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		//「修正する」ボタンをクリック
		WebElement fixButton = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/table[3]/tbody/tr[2]/td[5]/form[2]/input[1]"));
		fixButton.click();
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//画面タイトルチェック
		assertThat(webDriver.getTitle(), is(containsString("レポート登録")));
		//日付チェック
		WebElement reportDate = webDriver.findElement(By.xpath("//*[@id=\"main\"]/h2/small"));
		assertEquals("2022年10月2日", reportDate.getText());
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {
		//「学習項目」の内容修正(異常値)
		WebElement learningItem = webDriver.findElement(By.xpath("//*[@id=\"intFieldName_0\"]"));
		learningItem.clear();
		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		//画面下部にスクロール
		scrollBy("560");
		//「提出する」ボタン押下
		WebElement submitButton = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/form/div[3]/fieldset/div/div/button"));
		submitButton.click();
		//画面上部にスクロール
		scrollTo("1");
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//エラーメッセージチェック
		WebElement learningItemInputError = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/form/div[1]/fieldset/div/div[1]/p/span"));
		assertThat(learningItemInputError.getText(), is(containsString("理解度を入力した場合は、学習項目は必須です。")));
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {
		//「学習項目」の内容修正(正常値)
		WebElement learningItem = webDriver.findElement(By.xpath("//*[@id=\"intFieldName_0\"]"));
		learningItem.clear();
		learningItem.sendKeys("ITリテラシー①");
		//「理解度」の内容修正(異常値)
		final Select select = new Select(webDriver.findElement(By.xpath("//*[@id=\"intFieldValue_0\"]")));
		select.selectByValue("");
		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		//画面下部にスクロール
		scrollBy("560");
		//「提出する」ボタン押下
		WebElement submitButton = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/form/div[3]/fieldset/div/div/button"));
		submitButton.click();
		//画面上部にスクロール
		scrollTo("1");
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//エラーメッセージチェック
		WebElement selectError = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/form/div[1]/fieldset/div/div[2]/p/span"));
		assertThat(selectError.getText(), is(containsString("学習項目を入力した場合は、理解度は必須です。")));
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {
		//「理解度」の内容修正(正常値)
		final Select select = new Select(webDriver.findElement(By.xpath("//*[@id=\"intFieldValue_0\"]")));
		select.selectByValue("2");
		//「目標の達成度」の内容修正(異常値)
		WebElement inputAchievementLevel = webDriver.findElement(By.name("contentArray[0]"));
		inputAchievementLevel.clear();
		inputAchievementLevel.sendKeys("五");
		//画面下部にスクロール
		scrollTo("230");
		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		//画面下部にスクロール
		scrollBy("560");
		//「提出する」ボタン押下
		WebElement submitButton = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/form/div[3]/fieldset/div/div/button"));
		submitButton.click();
		//画面上部にスクロール
		scrollTo("230");
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//エラーメッセージチェック
		WebElement inputAchievementLevelError = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/form/div[2]/fieldset/div[1]/div/p/span"));
		assertThat(inputAchievementLevelError.getText(), is(containsString("目標の達成度は半角数字で入力してください。")));

	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {
		//「目標の達成度」の内容修正(異常値)
		WebElement inputAchievementLevel = webDriver.findElement(By.name("contentArray[0]"));
		inputAchievementLevel.clear();
		inputAchievementLevel.sendKeys("11");
		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		//画面下部にスクロール
		scrollBy("560");
		//「提出する」ボタン押下
		WebElement submitButton = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/form/div[3]/fieldset/div/div/button"));
		submitButton.click();
		//画面上部にスクロール
		scrollTo("230");
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//エラーメッセージチェック
		WebElement inputAchievementLevelError = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/form/div[2]/fieldset/div[1]/div/p/span"));
		assertThat(inputAchievementLevelError.getText(), is(containsString("目標の達成度は、半角数字で、1～10の範囲内で入力してください。")));
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {
		//「目標の達成度」の内容修正(異常値)
		WebElement inputAchievementLevel = webDriver.findElement(By.name("contentArray[0]"));
		inputAchievementLevel.clear();
		//画面下部にスクロール
		scrollTo("300");
		//「所感」の内容修正(異常値)
		WebElement inputImpression = webDriver.findElement(By.name("contentArray[1]"));
		inputImpression.clear();
		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		//画面下部にスクロール
		scrollTo("560");
		//「提出する」ボタン押下
		WebElement submitButton = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/form/div[3]/fieldset/div/div/button"));
		submitButton.click();
		//画面下部にスクロール
		scrollTo("400");
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//目標の達成度エラーメッセージチェック
		WebElement inputAchievementLevelError = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/form/div[2]/fieldset/div[1]/div/p/span"));
		assertThat(inputAchievementLevelError.getText(), is(containsString("目標の達成度は半角数字で入力してください。")));
		//所感エラーメッセージチェック
		WebElement inputImpressionError = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/form/div[2]/fieldset/div[2]/div/p/span"));
		assertThat(inputImpressionError.getText(), is(containsString("所感は必須です。")));
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() {
		//「目標の達成度」の内容修正(正常値)
		WebElement inputAchievementLevel = webDriver.findElement(By.name("contentArray[0]"));
		inputAchievementLevel.clear();
		inputAchievementLevel.sendKeys("5");
		//2001桁の文字列
		StringBuilder str2001 = new StringBuilder();
		for (int i = 0; i < 2001; i++) {
			str2001.append("2");
		}
		//待ち処理(1500秒)
		pageLoadTimeout(1500);
		//「所感」の内容修正(異常値)
		WebElement inputImpression = webDriver.findElement(By.name("contentArray[1]"));
		inputImpression.clear();
		inputImpression.sendKeys(str2001);
		//「一週間の振り返り」の内容修正(異常値)
		WebElement inputWeek = webDriver.findElement(By.xpath("//*[@id=\"content_2\"]"));
		inputWeek.clear();
		inputWeek.sendKeys(str2001);
		//画面下部にスクロール
		scrollBy("580");
		//エビデンス取得
		getEvidence(new Object() {
		}, "01");
		//「提出する」ボタン押下
		WebElement submitButton = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/form/div[3]/fieldset/div/div/button"));
		submitButton.click();
		//画面下部にスクロール
		scrollTo("580");
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//所感エラーメッセージチェック
		WebElement inputImpressionError = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/form/div[2]/fieldset/div[2]/div/p/span"));
		assertThat(inputImpressionError.getText(), is(containsString("所感の長さが最大値(2000)を超えています。")));
		//一週間の振り返りエラーメッセージチェック
		WebElement inputWeekError = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/form/div[2]/fieldset/div[3]/div/p/span"));
		assertThat(inputWeekError.getText(), is(containsString("一週間の振り返りの長さが最大値(2000)を超えています。")));
	}

}

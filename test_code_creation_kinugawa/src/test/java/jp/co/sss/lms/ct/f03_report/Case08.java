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
 * ケース08
 * @author 絹川
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		//「詳細」ボタン押下
		WebElement detailButton = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/div/div[2]/div[2]/table/tbody/tr[2]/td[5]/form/input[3]"));
		detailButton.click();
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//画面タイトルチェック
		assertThat(webDriver.getTitle(), is(containsString("セクション詳細")));
		//日付チェック
		WebElement sectionDate = webDriver.findElement(By.xpath("//*[@id=\"sectionDetail\"]/h2/small"));
		assertEquals("2022年10月2日", sectionDate.getText());
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		//画面下部にスクロール
		scrollBy("450");
		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		//「週報を確認する」ボタン押下
		WebElement submitButton = webDriver
				.findElement(By.xpath("//*[@id=\"sectionDetail\"]/table[2]/tbody/tr[3]/td/form/input[6]"));
		submitButton.click();
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
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {
		//「理解度」の内容修正
		final Select select = new Select(webDriver.findElement(By.name("intFieldValueArray[0]")));
		select.selectByValue("1");
		//「目標の達成度」の内容修正
		WebElement inputAchievementLevel = webDriver.findElement(By.name("contentArray[0]"));
		inputAchievementLevel.clear();
		inputAchievementLevel.sendKeys("4");
		//エビデンス取得
		getEvidence(new Object() {
		}, "01-1");

		//画面下部にスクロール
		scrollBy("560");
		//「所感」の内容修正
		WebElement inputImpression = webDriver.findElement(By.name("contentArray[1]"));
		inputImpression.clear();
		inputImpression.sendKeys("週報のテストです。");
		//「一週間の振り返り」の内容修正
		WebElement inputWeek = webDriver.findElement(By.name("contentArray[2]"));
		inputWeek.clear();
		inputWeek.sendKeys("週報の内容変更のテスト実施。");
		//エビデンス取得
		getEvidence(new Object() {
		}, "01-2");

		//「提出する」ボタン押下
		WebElement submitButton = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/form/div[3]/fieldset/div/div/button"));
		submitButton.click();
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//画面タイトルチェック
		assertThat(webDriver.getTitle(), is(containsString("セクション詳細")));
		//日付チェック
		WebElement sectionDate = webDriver.findElement(By.xpath("//*[@id=\"sectionDetail\"]/h2/small"));
		assertEquals("2022年10月2日", sectionDate.getText());
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
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
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {
		//画面下部にスクロール
		scrollBy("900");
		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		//「詳細」ボタンをクリック
		WebElement detailbutton = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/table[3]/tbody/tr[3]/td[5]/form[1]/input[1]"));
		detailbutton.click();
		//エビデンス取得
		getEvidence(new Object() {
		}, "02-1");
		//画面タイトルチェック
		assertThat(webDriver.getTitle(), is(containsString("レポート詳細")));
		//日付チェック
		WebElement reportDate = webDriver.findElement(By.xpath("//*[@id=\"main\"]/h2/small"));
		assertEquals("2022年10月2日", reportDate.getText());
		//ユーザー名チェック
		WebElement userName = webDriver.findElement(By.xpath("//*[@id=\"main\"]/table/tbody/tr/td"));
		assertEquals("受講生ＡＡ１", userName.getText());
		//「理解度」の修正後表示をチェック
		WebElement comprehensionLevel = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/div[1]/table/tbody/tr[2]/td[2]/p"));
		assertEquals("1", comprehensionLevel.getText());

		//画面下部にスクロール
		scrollBy("340");
		//エビデンス取得
		getEvidence(new Object() {
		}, "02-2");
		//「達成度」の修正後表示をチェック
		WebElement achievementLevel = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/table/tbody/tr[1]/td"));
		assertEquals("4", achievementLevel.getText());
		//「所感」の修正後表示をチェック
		WebElement impression = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/table/tbody/tr[2]/td"));
		assertEquals("週報のテストです。", impression.getText());
		//「一週間の振り返り」の修正後表示をチェック
		WebElement week = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/table/tbody/tr[3]/td"));
		assertEquals("週報の内容変更のテスト実施。", week.getText());
	}

}

package jp.co.sss.lms.ct.f06_login2;

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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト ログイン機能②
 * ケース16
 * @author 絹川
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース16 受講生 初回ログイン 変更パスワード未入力")
public class Case16 {

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
	@DisplayName("テスト02 DBに初期登録された未ログインの受講生ユーザーでログイン")
	void test02() {
		//ログインIDを入力
		WebElement loginId = webDriver.findElement(By.name("loginId"));
		loginId.clear();
		loginId.sendKeys("StudentAA01");
		//パスワードを入力
		WebElement password = webDriver.findElement(By.name("password"));
		password.clear();
		password.sendKeys("StudentAA01");
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
		assertThat(webDriver.getTitle(), is(containsString("セキュリティ規約")));
		//ユーザー表示のチェック
		WebElement showUserName = webDriver.findElement(By.tagName("small"));
		assertEquals("ようこそ受講生ＡＡ１さん", showUserName.getText());
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 「同意します」チェックボックスにチェックを入れ「次へ」ボタン押下")
	void test03() {
		//画面下部にスクロール
		scrollBy("100");
		//「同意します」のチェックボックスにチェック
		WebElement agreeCheck = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/div[2]/form/fieldset/div[1]/div/label/input[1]"));
		agreeCheck.click();
		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		//「次へ」ボタンを押下
		WebElement nextButton = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/form/fieldset/div[2]/button"));
		nextButton.click();
		//待ち処理(100秒)
		pageLoadTimeout(20);
		//画面タイトルチェック
		assertThat(webDriver.getTitle(), is(containsString("パスワード変更")));
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 パスワードを未入力で「変更」ボタン押下")
	void test04() {
		//現在のパスワード入力(異常値)
		WebElement inputCurrentPassword01 = webDriver.findElement(By.name("currentPassword"));
		inputCurrentPassword01.clear();
		//新しいパスワード入力(正常値)
		WebElement inputNewPassword01 = webDriver.findElement(By.name("password"));
		inputNewPassword01.clear();
		inputNewPassword01.sendKeys("ItTest2025");
		//画面下部にスクロール
		scrollBy("250");
		//確認パスワード入力(正常値)
		WebElement inputPasswordConfirm01 = webDriver.findElement(By.name("passwordConfirm"));
		inputPasswordConfirm01.clear();
		inputPasswordConfirm01.sendKeys("ItTest2025");
		//エビデンス取得
		getEvidence(new Object() {
		}, "01-1");

		//「変更」ボタン押下
		WebElement changeButton01 = webDriver
				.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[4]/div/button[2]"));
		changeButton01.click();
		//モーダルの「変更」ボタンをクリック
		WebElement modalChangeButton01 = webDriver.findElement(By.id("upd-btn"));
		JavascriptExecutor executor01 = (JavascriptExecutor) webDriver;
		executor01.executeScript("arguments[0].click();", modalChangeButton01);
		//エビデンス取得
		getEvidence(new Object() {
		}, "01-2");
		//画面タイトルチェック
		assertThat(webDriver.getTitle(), is(containsString("パスワード変更")));
		//現在のパスワードエラーメッセージチェック
		WebElement currentPasswordError = webDriver
				.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[1]/div/ul/li/span"));
		assertThat(currentPasswordError.getText(), is(containsString("現在のパスワードは必須です。")));

		//現在のパスワード入力(正常値)
		WebElement inputCurrentPassword02 = webDriver.findElement(By.name("currentPassword"));
		inputCurrentPassword02.clear();
		inputCurrentPassword02.sendKeys("StudentAA01");
		//新しいパスワード入力(異常値)
		WebElement inputNewPassword02 = webDriver.findElement(By.name("password"));
		inputNewPassword02.clear();
		//画面下部にスクロール
		scrollBy("250");
		//確認パスワード入力(正常値)
		WebElement inputPasswordConfirm02 = webDriver.findElement(By.name("passwordConfirm"));
		inputPasswordConfirm02.clear();
		inputPasswordConfirm02.sendKeys("ItTest2025");
		//エビデンス取得
		getEvidence(new Object() {
		}, "02-1");

		//「変更」ボタン押下
		WebElement changeButton02 = webDriver
				.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[4]/div/button[2]"));
		changeButton02.click();
		//モーダルの「変更」ボタンをクリック
		WebElement modalChangeButton02 = webDriver.findElement(By.id("upd-btn"));
		JavascriptExecutor executor02 = (JavascriptExecutor) webDriver;
		executor02.executeScript("arguments[0].click();", modalChangeButton02);
		//エビデンス取得
		getEvidence(new Object() {
		}, "02-2");
		//画面タイトルチェック
		assertThat(webDriver.getTitle(), is(containsString("パスワード変更")));
		//新しいパスワードエラーメッセージチェック
		WebElement newPasswordError = webDriver
				.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[2]/div/ul/li/span"));
		assertThat(newPasswordError.getText(), is(containsString("パスワードは必須です。")));

		//現在のパスワード入力(正常値)
		WebElement inputCurrentPassword03 = webDriver.findElement(By.name("currentPassword"));
		inputCurrentPassword03.clear();
		inputCurrentPassword03.sendKeys("StudentAA01");
		//新しいパスワード入力(正常値)
		WebElement inputNewPassword03 = webDriver.findElement(By.name("password"));
		inputNewPassword03.clear();
		inputNewPassword03.sendKeys("ItTest2025");
		//画面下部にスクロール
		scrollBy("250");
		//確認パスワード入力(異常値)
		WebElement inputPasswordConfirm03 = webDriver.findElement(By.name("passwordConfirm"));
		inputPasswordConfirm03.clear();
		//エビデンス取得
		getEvidence(new Object() {
		}, "03-1");

		//「変更」ボタン押下
		WebElement changeButton03 = webDriver
				.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[4]/div/button[2]"));
		changeButton03.click();
		//モーダルの「変更」ボタンをクリック
		WebElement modalChangeButton03 = webDriver.findElement(By.id("upd-btn"));
		JavascriptExecutor executor03 = (JavascriptExecutor) webDriver;
		executor03.executeScript("arguments[0].click();", modalChangeButton03);
		//エビデンス取得
		getEvidence(new Object() {
		}, "03-2");
		//画面タイトルチェック
		assertThat(webDriver.getTitle(), is(containsString("パスワード変更")));
		//確認パスワードエラーメッセージチェック
		WebElement passwordConfirmError = webDriver
				.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[3]/div/ul/li/span"));
		assertThat(passwordConfirmError.getText(), is(containsString("確認パスワードは必須です。")));

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 20文字以上の変更パスワードを入力し「変更」ボタン押下")
	void test05() {
		//画面下部にスクロール
		scrollTo("1");
		//現在のパスワード入力(正常値)
		WebElement inputCurrentPassword = webDriver.findElement(By.name("currentPassword"));
		inputCurrentPassword.clear();
		inputCurrentPassword.sendKeys("StudentAA01");
		//新しいパスワード入力(異常値)
		WebElement inputNewPassword = webDriver.findElement(By.name("password"));
		inputNewPassword.clear();
		inputNewPassword.sendKeys("ItTest2025202520252025");
		//画面下部にスクロール
		scrollBy("250");
		//確認パスワード入力(異常値)
		WebElement inputPasswordConfirm = webDriver.findElement(By.name("passwordConfirm"));
		inputPasswordConfirm.clear();
		inputPasswordConfirm.sendKeys("ItTest2025202520252025");
		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		//「変更」ボタン押下
		WebElement changeButton = webDriver
				.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[4]/div/button[2]"));
		changeButton.click();
		//モーダルの「変更」ボタンをクリック
		WebElement modalChangeButton = webDriver.findElement(By.id("upd-btn"));
		JavascriptExecutor executor = (JavascriptExecutor) webDriver;
		executor.executeScript("arguments[0].click();", modalChangeButton);
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//画面タイトルチェック
		assertThat(webDriver.getTitle(), is(containsString("パスワード変更")));
		//新しいパスワードエラーメッセージチェック
		WebElement newPasswordError = webDriver
				.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[2]/div/ul/li/span"));
		assertThat(newPasswordError.getText(), is(containsString("パスワードの長さが最大値(20)を超えています。")));

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 ポリシーに合わない変更パスワードを入力し「変更」ボタン押下")
	void test06() {
		//画面下部にスクロール
		scrollTo("1");
		//現在のパスワード入力(正常値)
		WebElement inputCurrentPassword = webDriver.findElement(By.name("currentPassword"));
		inputCurrentPassword.clear();
		inputCurrentPassword.sendKeys("StudentAA01");
		//新しいパスワード入力(異常値)
		WebElement inputNewPassword = webDriver.findElement(By.name("password"));
		inputNewPassword.clear();
		inputNewPassword.sendKeys("it2025");
		//画面下部にスクロール
		scrollBy("250");
		//確認パスワード入力(異常値)
		WebElement inputPasswordConfirm = webDriver.findElement(By.name("passwordConfirm"));
		inputPasswordConfirm.clear();
		inputPasswordConfirm.sendKeys("it2025");
		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		//「変更」ボタン押下
		WebElement changeButton = webDriver
				.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[4]/div/button[2]"));
		changeButton.click();
		//モーダルの「変更」ボタンをクリック
		WebElement modalChangeButton = webDriver.findElement(By.id("upd-btn"));
		JavascriptExecutor executor = (JavascriptExecutor) webDriver;
		executor.executeScript("arguments[0].click();", modalChangeButton);
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//画面タイトルチェック
		assertThat(webDriver.getTitle(), is(containsString("パスワード変更")));
		//新しいパスワードエラーメッセージチェック
		WebElement newPasswordError = webDriver
				.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[2]/div/ul/li/span"));
		assertThat(newPasswordError.getText(),
				is(containsString("「パスワード」には半角英数字のみ使用可能です。また、半角英大文字、半角英小文字、数字を含めた8～20文字を入力してください。")));

	}

	@Test
	@Order(7)
	@DisplayName("テスト07 一致しない確認パスワードを入力し「変更」ボタン押下")
	void test07() {
		//画面下部にスクロール
		scrollTo("1");
		//現在のパスワード入力(正常値)
		WebElement inputCurrentPassword = webDriver.findElement(By.name("currentPassword"));
		inputCurrentPassword.clear();
		inputCurrentPassword.sendKeys("StudentAA01");
		//新しいパスワード入力(正常値)
		WebElement inputNewPassword = webDriver.findElement(By.name("password"));
		inputNewPassword.clear();
		inputNewPassword.sendKeys("ItTest2025");
		//画面下部にスクロール
		scrollBy("250");
		//確認パスワード入力(異常値)
		WebElement inputPasswordConfirm = webDriver.findElement(By.name("passwordConfirm"));
		inputPasswordConfirm.clear();
		inputPasswordConfirm.sendKeys("ItTest2024");
		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		//「変更」ボタン押下
		WebElement changeButton = webDriver
				.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[4]/div/button[2]"));
		changeButton.click();
		//モーダルの「変更」ボタンをクリック
		WebElement modalChangeButton = webDriver.findElement(By.id("upd-btn"));
		JavascriptExecutor executor = (JavascriptExecutor) webDriver;
		executor.executeScript("arguments[0].click();", modalChangeButton);
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//画面タイトルチェック
		assertThat(webDriver.getTitle(), is(containsString("パスワード変更")));
		//新しいパスワードエラーメッセージチェック
		WebElement newPasswordError = webDriver
				.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[2]/div/ul/li/span"));
		assertThat(newPasswordError.getText(), is(containsString("パスワードと確認パスワードが一致しません。")));

	}

}

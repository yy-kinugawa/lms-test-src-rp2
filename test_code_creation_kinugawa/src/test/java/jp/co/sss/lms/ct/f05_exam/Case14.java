package jp.co.sss.lms.ct.f05_exam;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト 試験実施機能
 * ケース14
 * @author 絹川
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース13 受講生 試験の実施 結果50点")
public class Case14 {

	/** テスト07およびテスト08 試験実施日時 */
	static Date date;

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
	@DisplayName("テスト03 「試験有」の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		//「試験有」かチェック
		WebElement examStatus = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/div/div[2]/div[2]/table/tbody/tr[2]/td[4]"));
		if (!(examStatus.getText().equals("試験有"))) {
			System.out.println("この日程の試験はありません");
			return;
		}
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
	@DisplayName("テスト04 「本日の試験」エリアの「詳細」ボタンを押下し試験開始画面に遷移")
	void test04() {
		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		//「詳細」ボタン押下
		WebElement detailButton = webDriver
				.findElement(By.xpath("//*[@id=\"sectionDetail\"]/table[1]/tbody/tr[2]/td[2]/form/input[1]"));
		detailButton.click();
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//画面タイトルチェック
		assertThat(webDriver.getTitle(), is(containsString("試験【ITリテラシー①】")));
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「試験を開始する」ボタンを押下し試験問題画面に遷移")
	void test05() {
		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		//「試験を開始する」ボタン押下
		WebElement startExamButton = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/div/form/input[4]"));
		startExamButton.click();
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//画面タイトルチェック
		assertThat(webDriver.getTitle(), is(containsString("ITリテラシー①")));
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 正答と誤答が半々で「確認画面へ進む」ボタンを押下し試験回答確認画面に遷移")
	void test06() {
		//待ち処理(500秒)
		pageLoadTimeout(500);
		//回答を入力
		//問題1
		WebElement answer01 = webDriver.findElement(By.id("answer-0-2"));
		answer01.click();
		//エビデンス取得
		getEvidence(new Object() {
		}, "01-1");
		//画面下部にスクロール
		scrollBy("370");
		//問題2
		WebElement answer02 = webDriver.findElement(By.id("answer-1-2"));
		//エビデンス取得
		answer02.click();
		getEvidence(new Object() {
		}, "01-2");
		//画面下部にスクロール
		scrollBy("370");
		//問題3
		WebElement answer03 = webDriver.findElement(By.id("answer-2-0"));
		answer03.click();
		//エビデンス取得
		getEvidence(new Object() {
		}, "01-3");
		//画面下部にスクロール
		scrollBy("370");
		//問題4
		WebElement answer04 = webDriver.findElement(By.id("answer-3-0"));
		answer04.click();
		//エビデンス取得
		getEvidence(new Object() {
		}, "01-4");
		//画面下部にスクロール
		scrollBy("370");
		//問題5
		WebElement answer05 = webDriver.findElement(By.id("answer-4-1"));
		answer05.click();
		//エビデンス取得
		getEvidence(new Object() {
		}, "01-5");
		//画面下部にスクロール
		scrollBy("370");
		//問題6
		WebElement answer06 = webDriver.findElement(By.id("answer-5-1"));
		answer06.click();
		//エビデンス取得
		getEvidence(new Object() {
		}, "01-6");
		//画面下部にスクロール
		scrollBy("370");
		//問題7-12
		for (int i = 6; i < 12; i++) {
			WebElement answer = webDriver.findElement(By.id("answer-" + i + "-0"));
			answer.click();
			//エビデンス取得
			getEvidence(new Object() {
			}, "01-" + (i + 1));
			//画面下部にスクロール
			scrollBy("370");
		}

		//「確認画面へ進む」ボタン押下
		WebElement goToCheckButton = webDriver
				.findElement(By.xpath("//*[@id=\"examQuestionForm\"]/div[13]/fieldset/input"));
		goToCheckButton.click();
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//画面タイトルチェック
		assertThat(webDriver.getTitle(), is(containsString("ITリテラシー①")));
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 「回答を送信する」ボタンを押下し試験結果画面に遷移")
	void test07() throws InterruptedException {
		//待ち処理(100秒)
		visibilityTimeout(By.id("remainTime"), 100);
		//画面下部にスクロール
		scrollBy("2000");

		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		//「回答を送信する」ボタン押下
		WebElement sendButton = webDriver
				.findElement(By.id("sendButton"));
		sendButton.click();
		//モーダルの「OK」ボタンをクリック
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.accept();
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//画面タイトルチェック
		assertThat(webDriver.getTitle(), is(containsString("ITリテラシー①")));
		//点数チェック
		WebElement examScore = webDriver.findElement(By.xpath("//*[@id=\"examBeing\"]/h2/small"));
		assertThat(examScore.getText(), is(containsString("50.0点")));
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 「戻る」ボタンを押下し試験開始画面に遷移後当該試験の結果が反映される")
	void test08() {
		//待ち処理(100秒)
		pageLoadTimeout(100);
		//画面下部にスクロール
		scrollBy("5100");

		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		//「回答を送信する」ボタン押下
		WebElement goBackButton = webDriver
				.findElement(By.xpath("//*[@id=\"examBeing\"]/div[13]/fieldset/form/input[1]"));
		goBackButton.click();
		//エビデンス取得
		getEvidence(new Object() {
		}, "02-1");
		//画面タイトルチェック
		assertThat(webDriver.getTitle(), is(containsString("試験【ITリテラシー①】")));
		//画面下部にスクロール
		scrollBy("150");
		//エビデンス取得
		getEvidence(new Object() {
		}, "02-2");
		//点数チェック
		WebElement examResultScore = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/div/table[2]/tbody/tr[5]/td[2]"));
		assertEquals("50.0点", examResultScore.getText());
	}

}

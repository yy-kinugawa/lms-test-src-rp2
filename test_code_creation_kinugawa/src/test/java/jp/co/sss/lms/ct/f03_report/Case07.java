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

/**
 * 結合テスト レポート機能
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

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
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		//「未提出」かチェック
		WebElement reportStatus = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/div/div[3]/div[2]/table/tbody/tr[1]/td[3]/span"));
		if (!(reportStatus.getText().equals("未提出"))) {
			System.out.println("レポートが未提出ではありません");
			return;
		}
		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		//「詳細」ボタン押下
		WebElement detailButton = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/div/div[3]/div[2]/table/tbody/tr[1]/td[5]/form/input[3]"));
		detailButton.click();
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//画面タイトルチェック
		assertThat(webDriver.getTitle(), is(containsString("セクション詳細")));
		//日付チェック
		WebElement sectionDate = webDriver.findElement(By.xpath("//*[@id=\"sectionDetail\"]/h2/small"));
		assertEquals("2022年10月5日", sectionDate.getText());
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		//「提出する」ボタン押下
		WebElement submitButton = webDriver
				.findElement(By.xpath("//*[@id=\"sectionDetail\"]/table/tbody/tr[2]/td/form/input[5]"));
		submitButton.click();
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//画面タイトルチェック
		assertThat(webDriver.getTitle(), is(containsString("レポート登録")));
		//日付チェック
		WebElement reportDate = webDriver.findElement(By.xpath("//*[@id=\"main\"]/h2/small"));
		assertEquals("2022年10月5日", reportDate.getText());
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {
		//報告内容を入力
		WebElement inputReport = webDriver.findElement(By.className("form-control"));
		inputReport.clear();
		inputReport.sendKeys("レポート：テスト実施");
		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		//「提出する」ボタン押下
		WebElement submitButton = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/form/div[2]/fieldset/div/div/button"));
		submitButton.click();
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//画面タイトルチェック
		assertThat(webDriver.getTitle(), is(containsString("セクション詳細")));
		//日付チェック
		WebElement sectionDate = webDriver.findElement(By.xpath("//*[@id=\"sectionDetail\"]/h2/small"));
		assertEquals("2022年10月5日", sectionDate.getText());
		//ボタン名チェック
		WebElement checkReportButton = webDriver
				.findElement(By.xpath("//*[@id=\"sectionDetail\"]/table/tbody/tr[2]/td/form/input[6]"));
		assertEquals("提出済み日報【デモ】を確認する", checkReportButton.getAttribute("value"));

	}

}

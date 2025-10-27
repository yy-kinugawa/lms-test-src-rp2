package jp.co.sss.lms.ct.f04_attendance;

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
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * 結合テスト 勤怠管理機能
 * ケース11
 * @author 絹川
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース11 受講生 勤怠直接編集 正常系")
public class Case11 {

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
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから勤怠管理画面に遷移")
	void test03() {
		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		//ヘッダーの勤怠タブをクリック
		WebElement attendanceLink = webDriver.findElement(By.linkText("勤怠"));
		attendanceLink.click();
		//モーダルの「OK」ボタンをクリック
		Alert alert = webDriver.switchTo().alert();
		alert.accept();
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//画面タイトルチェック
		assertThat(webDriver.getTitle(), is(containsString("勤怠情報変更")));

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「勤怠情報を直接編集する」リンクから勤怠情報直接変更画面に遷移")
	void test04() {
		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		//勤怠直接編集リンクをクリック
		WebElement attendanceEditLink = webDriver.findElement(By.linkText("勤怠情報を直接編集する"));
		attendanceEditLink.click();
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//画面タイトルチェック
		assertThat(webDriver.getTitle(), is(containsString("勤怠情報変更")));
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 すべての研修日程の勤怠情報を正しく更新し勤怠管理画面に遷移")
	void test05() {
		// 「2025年10月26日(日)」の入力
		//「定時」ボタン押下
		WebElement fixedTimeButton = webDriver
				.findElement(By.xpath("//*[@id=\"main\"]/div/div/form/table/tbody/tr[1]/td[3]/button"));
		fixedTimeButton.click();
		// 「2025年10月27日(月)」の入力
		//出勤時刻
		final Select selectAttendanceHour = new Select(
				webDriver.findElement(By.name("attendanceList[1].trainingStartTimeHour")));
		selectAttendanceHour.selectByValue("10");
		final Select selectAttendanceMinute = new Select(
				webDriver.findElement(By.name("attendanceList[1].trainingStartTimeMinute")));
		selectAttendanceMinute.selectByValue("0");
		//退勤時刻
		final Select selectLeavingHour = new Select(
				webDriver.findElement(By.name("attendanceList[1].trainingEndTimeHour")));
		selectLeavingHour.selectByValue("19");
		final Select selectLeavingMinute = new Select(
				webDriver.findElement(By.name("attendanceList[1].trainingEndTimeMinute")));
		selectLeavingMinute.selectByValue("0");
		//中抜け時間
		final Select stepOutTime = new Select(webDriver.findElement(By.name("attendanceList[1].blankTime")));
		stepOutTime.selectByValue("15");
		//備考
		WebElement inputRemarks = webDriver.findElement(By.name("attendanceList[1].note"));
		inputRemarks.clear();
		inputRemarks.sendKeys("面談のため");
		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		//画面下部にスクロール
		scrollBy("560");
		//「更新」ボタン押下
		WebElement updateButton = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div/form/div/input"));
		updateButton.click();
		//モーダルの「OK」ボタンをクリック
		Alert alert = webDriver.switchTo().alert();
		alert.accept();
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//画面タイトルチェック
		assertThat(webDriver.getTitle(), is(containsString("勤怠情報変更")));

		//「2025年10月26日(日)」の入力チェック
		//出勤時間
		WebElement attendanceTime1026 = webDriver.findElement(By.xpath("//tbody/tr[1]/td[3]"));
		assertEquals("09:00", attendanceTime1026.getText());
		//退勤時間
		WebElement leavingTime1026 = webDriver.findElement(By.xpath("//tbody/tr[1]/td[4]"));
		assertEquals("18:00", leavingTime1026.getText());
		//中抜け時間
		WebElement outTime1026 = webDriver.findElement(By.xpath("//tbody/tr[1]/td[5]"));
		assertEquals("", outTime1026.getText());
		//備考
		WebElement remark1026 = webDriver.findElement(By.xpath("//tbody/tr[1]/td[7]"));
		assertEquals("", remark1026.getText());

		//「2025年10月27日(月)」の入力チェック
		//出勤時間
		WebElement attendanceTime1027 = webDriver.findElement(By.xpath("//tbody/tr[2]/td[3]"));
		assertEquals("10:00", attendanceTime1027.getText());
		//退勤時間
		WebElement leavingTime1027 = webDriver.findElement(By.xpath("//tbody/tr[2]/td[4]"));
		assertEquals("19:00", leavingTime1027.getText());
		//中抜け時間
		WebElement outTime1027 = webDriver.findElement(By.xpath("//tbody/tr[2]/td[5]"));
		assertEquals("00:15", outTime1027.getText());
		//備考
		WebElement remark1027 = webDriver.findElement(By.xpath("//tbody/tr[2]/td[7]"));
		assertEquals("面談のため", remark1027.getText());
	}

}

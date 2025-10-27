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
 * ケース12
 * @author 絹川
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース12 受講生 勤怠直接編集 入力チェック")
public class Case12 {

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
	@DisplayName("テスト05 不適切な内容で修正してエラー表示：出退勤の（時）と（分）のいずれかが空白")
	void test05() {
		//待ち処理(500秒)
		pageLoadTimeout(500);
		// 「2025年10月26日(日)」の入力
		//出勤時刻
		final Select selectAttendanceHour = new Select(
				webDriver.findElement(By.name("attendanceList[0].trainingStartTimeHour")));
		selectAttendanceHour.selectByValue("9");
		final Select selectAttendanceMinute = new Select(
				webDriver.findElement(By.name("attendanceList[0].trainingStartTimeMinute")));
		selectAttendanceMinute.selectByValue("");
		//退勤時刻
		final Select selectLeavingHour = new Select(
				webDriver.findElement(By.name("attendanceList[0].trainingEndTimeHour")));
		selectLeavingHour.selectByValue("");
		final Select selectLeavingMinute = new Select(
				webDriver.findElement(By.name("attendanceList[0].trainingEndTimeMinute")));
		selectLeavingMinute.selectByValue("0");
		//中抜け時間
		final Select stepOutTime = new Select(webDriver.findElement(By.name("attendanceList[0].blankTime")));
		stepOutTime.selectByValue("");
		//備考
		WebElement inputRemarks = webDriver.findElement(By.name("attendanceList[0].note"));
		inputRemarks.clear();
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
		//出勤エラーメッセージチェック
		WebElement attendanceError = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div/ul/li[1]/span"));
		assertThat(attendanceError.getText(), is(containsString("出勤時間が正しく入力されていません。")));
		//退勤エラーメッセージチェック
		WebElement leavingError = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div/ul/li[2]/span"));
		assertThat(leavingError.getText(), is(containsString("退勤時間が正しく入力されていません。")));

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正してエラー表示：出勤が空白で退勤に入力あり")
	void test06() {
		//待ち処理(500秒)
		pageLoadTimeout(500);
		// 「2025年10月26日(日)」の入力
		//出勤時刻
		final Select selectAttendanceHour = new Select(
				webDriver.findElement(By.name("attendanceList[0].trainingStartTimeHour")));
		selectAttendanceHour.selectByValue("");
		final Select selectAttendanceMinute = new Select(
				webDriver.findElement(By.name("attendanceList[0].trainingStartTimeMinute")));
		selectAttendanceMinute.selectByValue("");
		//退勤時刻
		final Select selectLeavingHour = new Select(
				webDriver.findElement(By.name("attendanceList[0].trainingEndTimeHour")));
		selectLeavingHour.selectByValue("18");
		final Select selectLeavingMinute = new Select(
				webDriver.findElement(By.name("attendanceList[0].trainingEndTimeMinute")));
		selectLeavingMinute.selectByValue("0");
		//中抜け時間
		final Select stepOutTime = new Select(webDriver.findElement(By.name("attendanceList[0].blankTime")));
		stepOutTime.selectByValue("");
		//備考
		WebElement inputRemarks = webDriver.findElement(By.name("attendanceList[0].note"));
		inputRemarks.clear();
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
		//出勤エラーメッセージチェック
		WebElement attendanceError = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div/ul/li[1]/span"));
		assertThat(attendanceError.getText(), is(containsString("出勤情報がないため退勤情報を入力出来ません。")));
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正してエラー表示：出勤が退勤よりも遅い時間")
	void test07() {
		//待ち処理(500秒)
		pageLoadTimeout(500);
		// 「2025年10月26日(日)」の入力
		//出勤時刻
		final Select selectAttendanceHour = new Select(
				webDriver.findElement(By.name("attendanceList[0].trainingStartTimeHour")));
		selectAttendanceHour.selectByValue("19");
		final Select selectAttendanceMinute = new Select(
				webDriver.findElement(By.name("attendanceList[0].trainingStartTimeMinute")));
		selectAttendanceMinute.selectByValue("0");
		//退勤時刻
		final Select selectLeavingHour = new Select(
				webDriver.findElement(By.name("attendanceList[0].trainingEndTimeHour")));
		selectLeavingHour.selectByValue("18");
		final Select selectLeavingMinute = new Select(
				webDriver.findElement(By.name("attendanceList[0].trainingEndTimeMinute")));
		selectLeavingMinute.selectByValue("0");
		//中抜け時間
		final Select stepOutTime = new Select(webDriver.findElement(By.name("attendanceList[0].blankTime")));
		stepOutTime.selectByValue("");
		//備考
		WebElement inputRemarks = webDriver.findElement(By.name("attendanceList[0].note"));
		inputRemarks.clear();
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
		//出勤エラーメッセージチェック
		WebElement attendanceError = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div/ul/li[1]/span"));
		assertThat(attendanceError.getText(), is(containsString("退勤時刻[0]は出勤時刻[0]より後でなければいけません。")));
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正してエラー表示：出退勤時間を超える中抜け時間")
	void test08() {
		//待ち処理(500秒)
		pageLoadTimeout(500);
		// 「2025年10月26日(日)」の入力
		//出勤時刻
		final Select selectAttendanceHour = new Select(
				webDriver.findElement(By.name("attendanceList[0].trainingStartTimeHour")));
		selectAttendanceHour.selectByValue("9");
		final Select selectAttendanceMinute = new Select(
				webDriver.findElement(By.name("attendanceList[0].trainingStartTimeMinute")));
		selectAttendanceMinute.selectByValue("0");
		//退勤時刻
		final Select selectLeavingHour = new Select(
				webDriver.findElement(By.name("attendanceList[0].trainingEndTimeHour")));
		selectLeavingHour.selectByValue("10");
		final Select selectLeavingMinute = new Select(
				webDriver.findElement(By.name("attendanceList[0].trainingEndTimeMinute")));
		selectLeavingMinute.selectByValue("0");
		//中抜け時間
		final Select stepOutTime = new Select(webDriver.findElement(By.name("attendanceList[0].blankTime")));
		stepOutTime.selectByValue("90");
		//備考
		WebElement inputRemarks = webDriver.findElement(By.name("attendanceList[0].note"));
		inputRemarks.clear();
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
		//出勤エラーメッセージチェック
		WebElement stepOutError = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div/ul/li/span"));
		assertThat(stepOutError.getText(), is(containsString("中抜け時間が勤務時間を超えています。")));
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正してエラー表示：備考が100文字超")
	void test09() {
		//待ち処理(1000秒)
		pageLoadTimeout(1000);
		// 「2025年10月26日(日)」の入力
		//出勤時刻
		final Select selectAttendanceHour = new Select(
				webDriver.findElement(By.name("attendanceList[0].trainingStartTimeHour")));
		selectAttendanceHour.selectByValue("9");
		final Select selectAttendanceMinute = new Select(
				webDriver.findElement(By.name("attendanceList[0].trainingStartTimeMinute")));
		selectAttendanceMinute.selectByValue("0");
		//退勤時刻
		final Select selectLeavingHour = new Select(
				webDriver.findElement(By.name("attendanceList[0].trainingEndTimeHour")));
		selectLeavingHour.selectByValue("18");
		final Select selectLeavingMinute = new Select(
				webDriver.findElement(By.name("attendanceList[0].trainingEndTimeMinute")));
		selectLeavingMinute.selectByValue("0");
		//中抜け時間
		final Select stepOutTime = new Select(webDriver.findElement(By.name("attendanceList[0].blankTime")));
		stepOutTime.selectByValue("");
		//備考
		//入力用101桁文字列
		StringBuilder str101 = new StringBuilder();
		for (int i = 0; i < 101; i++) {
			str101.append("2");
		}
		WebElement inputRemarks = webDriver.findElement(By.name("attendanceList[0].note"));
		inputRemarks.clear();
		inputRemarks.sendKeys(str101);
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
		//出勤エラーメッセージチェック
		WebElement remarkError = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div/ul/li/span"));
		assertThat(remarkError.getText(), is(containsString("備考の長さが最大値(100)を超えています。")));
	}

}

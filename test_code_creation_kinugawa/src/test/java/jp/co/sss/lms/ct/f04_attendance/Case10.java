package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

/**
 * 結合テスト 勤怠管理機能
 * ケース10
 * @author 絹川
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース10 受講生 勤怠登録 正常系")
public class Case10 {

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
	@DisplayName("テスト04 「出勤」ボタンを押下し出勤時間を登録")
	void test04() {
		//エビデンス取得
		getEvidence(new Object() {
		}, "01");
		//現在時刻の取得
		Date now = new Date();
		//誤差を考慮し1秒後の時刻も設定
		Calendar calender = Calendar.getInstance();
		calender.setTime(now);
		calender.add(Calendar.SECOND, 1);
		Date oneSecondLater = calender.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String attendanceTime = sdf.format(now) + sdf.format(oneSecondLater);
		//「出勤」ボタンをクリック
		WebElement attendanceButton = webDriver.findElement(By.name("punchIn"));
		attendanceButton.click();
		//モーダルの「OK」ボタンをクリック
		Alert alert = webDriver.switchTo().alert();
		alert.accept();
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//画面タイトルチェック
		assertThat(webDriver.getTitle(), is(containsString("勤怠情報変更")));
		//出勤表示チェック
		WebElement attendanceDate = webDriver
				.findElement(By.xpath("//tbody/tr[2]/td[3]"));
		assertThat(attendanceTime, is(containsString(attendanceDate.getText())));
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「退勤」ボタンを押下し退勤時間を登録")
	void test05() {
		//エビデンス取得
		getEvidence(new Object() {
		}, "01");
		//現在時刻の取得
		Date now = new Date();
		//誤差を考慮し1秒後の時刻も設定
		Calendar calender = Calendar.getInstance();
		calender.setTime(now);
		calender.add(Calendar.SECOND, 1);
		Date oneSecondLater = calender.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String leavingTime = sdf.format(now) + sdf.format(oneSecondLater);

		//「退勤」ボタンをクリック
		WebElement leavingButton = webDriver.findElement(By.name("punchOut"));
		leavingButton.click();
		//モーダルの「OK」ボタンをクリック
		Alert alert = webDriver.switchTo().alert();
		alert.accept();
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//画面タイトルチェック
		assertThat(webDriver.getTitle(), is(containsString("勤怠情報変更")));
		//退勤表示チェック
		WebElement leavingDate = webDriver
				.findElement(By.xpath("//tbody/tr[2]/td[4]"));
		assertThat(leavingTime, is(containsString(leavingDate.getText())));
	}

}

package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

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
 * 結合テスト よくある質問機能
 * ケース06
 * @author 絹川
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		//「機能」タブをクリック
		WebElement functionTab = webDriver.findElement(By.className("dropdown-toggle"));
		functionTab.click();
		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		//「ヘルプ」を選択してクリック
		WebElement helpLink = webDriver.findElement(By.linkText("ヘルプ"));
		helpLink.click();
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//画面タイトルチェック
		assertThat(webDriver.getTitle(), is(containsString("ヘルプ")));
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		//「よくある質問」リンクをクリック
		WebElement helpLink = webDriver.findElement(By.linkText("よくある質問"));
		helpLink.click();
		//ウィンドウの切り替え
		Object[] windowHandles = webDriver.getWindowHandles().toArray();
		webDriver.switchTo().window((String) windowHandles[1]);
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//画面タイトルチェック
		assertThat(webDriver.getTitle(), is(containsString("よくある質問")));
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {
		//エビデンス取得
		getEvidence(new Object() {
		}, "01-1");
		//画面下部にスクロール
		scrollTo("545");
		//エビデンス取得
		getEvidence(new Object() {
		}, "01-2");

		//画面上部にスクロール
		scrollTo("1");
		//「研修関係」をクリック
		WebElement trainingLink = webDriver.findElement(By.linkText("【研修関係】"));
		trainingLink.click();

		//画面下部にスクロール
		scrollTo("545");
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//質問表示の内容をチェック
		List<WebElement> questionList = webDriver
				.findElements(By.xpath("//*[@id=\"question-h[${status.index}]\"]/dt/span[2]"));
		List<WebElement> displayedList = new ArrayList<>();
		for (WebElement we : questionList) {
			//表示されていればリストに格納
			if (we.isDisplayed()) {
				displayedList.add(we);
			}
		}
		assertEquals("キャンセル料・途中退校について", displayedList.get(0).getText());
		assertEquals("研修の申し込みはどのようにすれば良いですか？", displayedList.get(1).getText());
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {
		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		//質問をクリック
		WebElement showAnswer = webDriver.findElement(By.className("sorting_1"));
		showAnswer.click();
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//回答表示の内容をチェック
		WebElement answer = webDriver
				.findElement(By.xpath("//*[@id=\"answer-h[${status.index}]\"]/span[2]"));
		assertEquals("受講者の退職や解雇等、やむを得ない事情による途中終了に関してなど、事情をお伺いした上で、協議という形を取らせて頂きます。 弊社営業担当までご相談下さい。",
				answer.getText());
	}

}

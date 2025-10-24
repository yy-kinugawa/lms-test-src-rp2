package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

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
 * ケース05
 * @author 絹川
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		//検索キーワード入力
		WebElement inputKeyword = webDriver.findElement(By.name("keyword"));
		inputKeyword.clear();
		inputKeyword.sendKeys("研修");
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
		//「検索」ボタン押下
		WebElement serchButton = webDriver.findElement(By.xpath("//*[@value='検索']"));
		serchButton.click();

		//画面下部にスクロール
		scrollTo("560");
		//各質問をクリック
		List<WebElement> showAnswer = webDriver.findElements(By.className("sorting_1"));
		for (WebElement we : showAnswer) {
			//表示されていればクリック
			if (we.isDisplayed()) {
				we.click();
				//画面下部にスクロール
				scrollTo("580");
			}
		}
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");

		//質問と回答に検索キーワードが含まれているかチェック
		List<WebElement> questionList = webDriver
				.findElements(By.xpath("//*[@id=\"question-h[${status.index}]\"]/dt/span[2]")); //質問要素を取得
		List<WebElement> answerList = webDriver
				.findElements(By.xpath("//*[@id=\"answer-h[${status.index}]\"]/span[2]")); //回答要素を取得
		for (int i = 0; i < questionList.size(); i++) {
			//質問と回答が表示されていれば一つの文にしてチェック
			if (questionList.get(i).isDisplayed() && answerList.get(i).isDisplayed()) {
				assertThat(questionList.get(i).getText() + answerList.get(i).getText(),
						is(containsString("研修")));
			}
		}
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		//画面上部にスクロール
		scrollTo("1");
		//エビデンス取得
		getEvidence(new Object() {
		}, "01");

		//クリアボタン押下
		WebElement clearButton = webDriver.findElement(By.xpath("//*[@value='クリア']"));
		clearButton.click();
		//エビデンス取得
		getEvidence(new Object() {
		}, "02");
		//テキストボックスが空白かのチェック
		WebElement inputKeyword = webDriver.findElement(By.name("keyword"));
		assertEquals("", inputKeyword.getText());
	}

}

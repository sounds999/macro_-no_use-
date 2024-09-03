package enroll_In_classes;

import com.twocaptcha.TwoCaptcha;
import com.twocaptcha.captcha.Normal;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Random;

public class enrollClass_selenium2_capcha {
    public enrollClass_selenium2_capcha(String cource_name, String cource_code) {
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless");
        options.addArguments("--window-size=2000,1000");

        WebDriver driver = new ChromeDriver(options);

        // wait_Objects
        WebDriverWait short_wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebDriverWait long_wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // ensure Initial HTML load
        // but JS and AJAX의 완전한 load는 보장하지 않음
        // -> 현대의 web은 HTML 골격이 web에 parsing된 후에 JS가 추가적인 정보를 DOM등을 사용하여 로드하거나
        //    또는 웹이 완전히 로드된 후에 서버로부터 비동기적으로 정보들이 추가될 수 있다 (AJAX)
        driver.get("https://sugang.knu.ac.kr/login.knu");

        // dynamic content loading이 끝날 때까지 대기
        // return으로 해당 element를 반환하기에 아래의 findElement method 대신 사용하면 더 안전함 (귀찮아서 안 함)
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("stdno")));

        System.out.println();
        System.out.println("수강신청을 시도합니다");
        System.out.println("과목 : " + cource_name);
        System.out.println("과목코드 : " + cource_code);

        Random random = new Random();

        // 아이디 입력
        WebElement courseNameInput = driver.findElement(By.id("stdno"));
        courseNameInput.sendKeys("학번");
        WebElement courseNameInput2 = driver.findElement(By.id("userId"));
        courseNameInput2.sendKeys("통합정보 아이디");
        WebElement courseNameInput3 = driver.findElement(By.id("pssrd"));
        courseNameInput3.sendKeys("통합정보 비밀번호");

        // 로그인 버튼 클릭
        WebElement searchButton = driver.findElement(By.id("btn_login"));
        searchButton.click();

        // 만약 url이 바뀐다면 바뀐 url을 아래 코드의 argument에 넣음
        // 페이지의 element중 하나를 사용해서 dymamic loading이 완료됐는지 확인함
        wait.until(ExpectedConditions.urlContains("https://sugang.knu.ac.kr/web/stddm/lssrq/sugang/appcr.knu?login=true"));
        driver.get("https://sugang.knu.ac.kr/web/stddm/lssrq/sugang/appcr.knu?login=true");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("btnCheck")));

        // 0.3 ~ 0.7 초 대기
        try {
            Thread.sleep(random.nextInt(400) + 300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 만약 조회를 눌렀을 때 보안코드가 정상적으로 입력되어 강의가 검색되어 좌측의 신청 버튼이 생성되었다면 버튼 누름
        // 만약 버튼이 보이지 않는다면 다시 보안코드 생성하여 재시도
        while (true) {
            // 강의 코드 및 chapcha 코드 입력
            input_courceCode_and_captcha(driver, cource_code);

            // 과목 조회 버튼 클릭, 조회 id selector 넣어야 함
            WebElement searchButton2 = driver.findElement(By.id("btnCheck"));
            searchButton2.click();
            // =====================================================================================
            /* inner HTML과 iframe, 팝업, modal dialog, javaScript alert나 confirm 차이에 대해 이해하기

            inner HTML
            inner HTML은 element.innerHTML = htmlString; 코드를 사용하여 특정 tag안에 정보를 동적으로 추가한다
            실제 구현 코드는 driver.getElementById("container").innerHTML = "<p>새로운 내용입니다.</p>";
            구조로 driver에 연결된 url에 get요청을 하여 특정 container라는 id selector을 갖은 요소를 가져오고
            해당 요소에 우항의 스트링을 추가하고 있다.
            정적 content를 모두 로드한 후 동적으로 스트링을 추가하거나 AJAX로 가져온 data를 동적으로 추가할 때 사용한다.

            iframe
            web page의 특정 공간에 현재 page url이 아닌 다른 url에서 get요청을 보내어 받아온 HTML코드를 로드하여
            현재 page와 독립된 다른 page를 띄우는 방법이다 주로 로그인이나 결제 서비스 등 고도의 기술을 제공해야 하는
            영역을 현재 page와 분리시킬 목적으로 사용한다
            실제 구현 코드는 <iframe src="https://example.com" width="500" height="300"></iframe>이다

            Popup
            사용자의 주의를 끄는 역할을 하며, main HTMl에 담긴 내용 외에 추가적인 정보를 줄 때 띄운다
            실제 구현 코드는 window.open("url", "Popup", "width=400, height=300")이다
            이는 javaScript가 지원하는 functions로 selenium의 java코드인 WebDriver.get과는 차이가 있다
            큰 차이는 window.open은 gui를 생성하지만 WebDriver.get은 diver이 get요청을 보낼 url만 설정한다

            alert
            사용자에게 경고를 보내기 위해 사용된다
            실제 구현 코드는 alert("이것은 경고 메시지입니다.");
            해당 코드 및 아래의 confirm 코드는 JS코드로써 바닐라 자바는 크롬 대화상자 생성 기능을 지원하지 않는다.

            confirm
            사용자의 선택을 확인하기 위해 사용된다 실제 코드 구현은 아래와 같다
            if (confirm("정말로 삭제하시겠습니까?")) {
             // 사용자가 '확인'을 클릭한 경우 실행 코드
            } else {
            // 사용자가 '취소'를 클릭한 경우 실행 코드
            }
            */
            // =====================================================================================


            // 보안문자가 정확했다면 신청 버튼 생성, 틀렸다면 alert
            // ====================================================================================================
            // try-catch로 예외처리 안 해줄 때 wait에서 NoSuchElementException 발생시 tread의 흐름
            //
            // Exception 발생 -> 현재 메서드의 예외처리 블록(catch)이동 -> 없다면 caller단으로 이동하여 예외처리 블록 이동
            //               -> main단까지 적절한 예외처리 블록이 존재하지 않으면 tread 정지
            //  ===================================================================================================
            WebElement visibleElement1 = null;
            try {
                // 만약 해당 버튼이 없다면 예외 발생, catch 블록 이동
                visibleElement1 = short_wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#grid02 > tr > td:nth-child(2) > a")));
                // 0.2초 ~0.4초 대기
                try {
                    Thread.sleep(random.nextInt(200) + 200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                visibleElement1.click();
                break;
            } catch (NoSuchElementException e) {
                System.out.println("수강신청 버튼을 찾지 못했습니다. 보안문자를 다시 생성합니다");
                // alert chrome 대화상자 확인 클릭
                Alert alert = wait.until(ExpectedConditions.alertIsPresent());
                // 0.2초 ~0.4초 대기
                try {
                    Thread.sleep(random.nextInt(200) + 200);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
                alert.accept();
                continue; // while문의 코드가 끝났기에 continue없어도 자동적으로 처음부터 loop, but 가독성을 위해 추가
            }
        }

        // 최종 confirm 크롬 대화상자에 확인
        Alert confirm = wait.until(ExpectedConditions.alertIsPresent());
        // 0.2초 ~0.4초 대기
        try {
            Thread.sleep(random.nextInt(200) + 200);
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
        confirm.accept();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        // 0.2초 ~0.4초 대기
        try {
            Thread.sleep(random.nextInt(200) + 200);
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
        String alertMesage = alert.getText();
        alert.accept();

        // todo
        // 실패 및 성공 창 둘 중 하나가 나타날 때까지 대기해줌
        // css selector 넣어야 함
        // 신청 성공 로직
        WebElement visibleElement2 = null;
        if (alertMesage.equals("신청되었습니다."))
        {
            System.out.println("수강신청을 성공적으로 완료하였습니다");
            System.out.println("프로그램을 종료합니다");
            System.exit(1);
        } else {
            System.out.println("해당 과목이 정원 상태가 되어 수강신청에 실패했습니다");
            System.out.println("다시 수강신청 가능한 과목이 있는지 탐색하겠습니다");
            driver.quit();
            enrollClass_selenium1_한번에 restart = new enrollClass_selenium1_한번에();
        }
    }


    // todo
// 강의 코드 및 챕챠 코드 입력 method
// id selector argument 입력해야 함
    public void input_courceCode_and_captcha(WebDriver driver, String cource_code) {
        // wait Object생성
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // todo
        // 강의코드 입력 element의 id selector입력
        WebElement coure_code_input = driver.findElement(By.id("schSbjetNo"));
        coure_code_input.sendKeys(cource_code);

        // 이미지 저장 (캡쳐 파일 저장 용도)
        // todo
        // 캡쳐 문자가 있는 element의 id selector을 arg로 넣어줘야 함
        WebElement element = driver.findElement(By.id("schCapcha"));
        File file = element.getScreenshotAs(OutputType.FILE);
        File captchaFile = new File("./target/captcha.png");
        try {
            FileUtils.copyFile(file, captchaFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Capcha solver API site [login : goegle]
        // [login 후 key 생성] [100% 문자 인식 설정, price ↑]
        // https://2captcha.com/ko/lang/java
        // https://2captcha.com/api-docs/normal-captcha
        TwoCaptcha solver = new TwoCaptcha("2chapcha_key");
        solver.setDefaultTimeout(120);

        // Capcha solve process
        Normal captcha = new Normal();
        captcha.setFile(captchaFile.getAbsolutePath());
        captcha.setNumeric(4);
        captcha.setMinLen(4);
        captcha.setMaxLen(20);
        captcha.setPhrase(true);
        captcha.setCaseSensitive(true);
        captcha.setCalc(false);
        captcha.setLang("en");
        String captchaCode = null; // captcha 해석 문자 저장 variable
        System.out.println();

        try {
            solver.solve(captcha);
            captchaCode = captcha.getCode();
            System.out.println("Captcha 해석 성공: " + captchaCode);
        } catch (Exception e) {
            System.out.println("CAPTCHA 해결 실패: " + e.getMessage());
            e.printStackTrace();
        }

        // todo
        // 보안 코드 입력, id selector 넣어야 함
        WebElement captcha_code_input = driver.findElement(By.id("schCapcha2"));
        captcha_code_input.sendKeys(captchaCode);
        // captcha_code_input.sendKeys("sdtd"); // 일부로 설패
    }
}

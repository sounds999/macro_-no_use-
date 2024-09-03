package enroll_In_classes;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;

import static java.lang.System.exit;

public class enrollClass_selenium3_N0_capcha {
    public enrollClass_selenium3_N0_capcha(String cource_name, String cource_code) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--window-size=2000,1000");

        WebDriver driver = new ChromeDriver(options);

        driver.get("https://sugang.knu.ac.kr/login.knu");

        // wait_Objects
        WebDriverWait short_wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebDriverWait long_wait = new WebDriverWait(driver, Duration.ofSeconds(30));

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

        // 로그인
        WebElement searchButton = driver.findElement(By.id("btn_login"));
        searchButton.click();

        // todo
        // 만약 url이 바뀐다면 바뀐 url을 아래 코드의 argument에 넣음
        // 페이지의 element중 하나를 사용해서 dymamic loading이 완료됐는지 확인함
        wait.until(ExpectedConditions.urlContains("변경된 페이지 url"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("변경된 페이지 요소 아무거나")));

        // 0.2 ~ 0.6 초 대기
        try {
            Thread.sleep(random.nextInt(400) + 200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 수꾸 네트워크 과목 신청
        WebElement searchButton3 = short_wait.until(ExpectedConditions.presenceOfElementLocated(By.id("네트워크 과목 신청")));
        searchButton3.click();

        // todo
        // 실패 및 성공 창 둘 중 하나가 나타날 때까지 대기해줌
        // css selector 넣어야 함
        WebElement visibleElement = null;
        boolean elementFound = long_wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#신청성공창에 있는 확인 버튼")),
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#신청실패창에 있는 확인 버튼"))
        ));

        if (elementFound) {
            // 신청 성공 로직
            try {
                visibleElement = driver.findElement(By.cssSelector("#신청성공"));
                visibleElement.click();
                System.out.println("수강신청을 성공적으로 완료하였습니다");
                System.out.println("프로그램을 종료합니다");
                exit(1);
                // 신청 실패 로직
            } catch (NoSuchElementException e) {
                visibleElement = driver.findElement(By.cssSelector("#신청실패"));
                visibleElement.click();
                System.out.println("해당 과목이 정원 상태가 되어 수강신청에 실패했습니다");
                System.out.println("다시 수강신청 가능한 과목이 있는지 탐색하겠습니다");
                driver.quit();
                enrollClass_selenium1_한번에 restart = new enrollClass_selenium1_한번에();
                // 해당 창 종료
            }
        } else {
            System.out.println("매크로에 문제가 생겼습니다 매크로가 강제 종료됩니다");
            exit(1);
        }
    }
}

package enroll_In_classes;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.awt.*;
import java.time.Duration;
import java.util.Random;

public class enrollClass_selenium1_각각 {
    public static void main(String[] args) {
        // chrome set argument 전달 객체 실행
        ChromeOptions options = new ChromeOptions();
        // WARNING Log Level set
        Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);
        // background chrome 실행
        options.addArguments("--headless");
        // visible한 경우 window size 설정함 "wide, hight"
        options.addArguments("--window-size=2000,1000");

        // 크롬 설정 객체를 drver객체에 전달
        WebDriver driver = new ChromeDriver(options);

        // 크롬 드라이버 객체로 page resource가 갈 수 있도록 url에 get REQUEST 호출
        driver.get("https://knuin.knu.ac.kr/public/stddm/lectPlnInqr.knu");

        // wait함수 사용시 동작 수행을 parameter 값만큼 기다려준다 이후 timeOutException발생시킴
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        Random random = new Random();

        // 1.5초에서 2.5초 사이의 랜덤한 시간 동안 대기
        try {
            Thread.sleep(random.nextInt(1000) + 1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 강좌상세검색 드롭다운 클릭
        WebElement detailSearchDropdown = driver.findElement(By.id("schCode"));
        detailSearchDropdown.click();

        // 0.5초에서 1.5초 사이의 랜덤한 시간 동안 대기
        try {
            Thread.sleep(random.nextInt(1000) + 500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // '교과목명' 옵션 선택
        WebElement courseNameOption = driver.findElement(By.xpath("//option[contains(text(),'교과목명')]"));
        courseNameOption.click();

        // 다시 0.5초에서 1초 사이의 랜덤한 시간 동안 대기
        try {
            Thread.sleep(random.nextInt(500) + 500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // todo 강의 이름 입력
        // 시스템프로그래밍 : ELEC0462003
        // 데이타베이스 : COMP0322002
        // 네트워크 : EECS0312001 etc. 수꾸 담음
        // 데이타통신 : COMP0323001
        // 프로그래밍기초 : COMP0204004 [실험용]
        String cource_name = "프로그래밍기초";

        WebElement courseNameInput = driver.findElement(By.id("schCodeContents"));
        courseNameInput.sendKeys(cource_name);
        try {
            Thread.sleep(random.nextInt(500) + 500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int i = 0;
        // 해당 동작 무한 반복 "조회 -> 수강신청 정원과 수강신청 현재 인원 비교 -> 현재 인원이 더 적을시에 알람"
        while (true) {
            // 매크로 주기적으로 휴식
            if (++i % 32 == 0 || i % 103 == 0)
            {
                System.out.println("매크로를 잠시 쉽니다 [12초 ~ 1분12초]");
                try {
                    Thread.sleep(random.nextInt(60000) + 12000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 조회 버튼 클릭
            WebElement searchButton = driver.findElement(By.id("btnSearch"));
            searchButton.click();

            // 0.8초에서 3.8초 대기
            try {
                Thread.sleep(random.nextInt(3000) + 800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 우측 스크롤 실행 (동적 페이지 로드라 화면에 없는 정보는 로드되지 않기에 스크롤을 이동해야 함)
            // 1. 스크롤 가능한 컨테이너 요소
            // 2. JavaScript를 사용하여 오른쪽으로 스크롤
            WebElement scrollableContainer = driver.findElement(By.id("grid01_scrollX_right"));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollLeft = arguments[0].scrollWidth", scrollableContainer);

            // 0.5초에서 1초 대기
            try {
                Thread.sleep(random.nextInt(500) + 500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // subject1 = 정원 , subject = 현재 인원
            WebElement subject1 = null;
            WebElement subject2 = null;
            String cource_code = null;

            if (cource_name == "데이타베이스")
            {
                subject1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#grid01_cell_1_16 nobr")));
                subject2 = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#grid01_cell_1_17 nobr")));
                cource_code = "COMP0322002";
            }
            else if (cource_name == "프로그래밍기초")
            {
                subject1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#grid01_cell_6_16 > nobr")));
                subject2 = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#grid01_cell_6_17 > nobr")));
                cource_code = "COMP0204004";
            }
            else if (cource_name == "네트워크")
            {
                subject1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#grid01_cell_0_16 > nobr")));
                subject2 = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#grid01_cell_0_17 nobr")));
                cource_code = "EECS0312001";
            }
            else if (cource_name == "시스템프로그래밍")
            {
                subject1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#grid01_cell_3_16 > nobr")));
                subject2 = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#grid01_cell_3_17 > nobr")));
                cource_code = "ELEC0462003";
            }
            else if (cource_name == "데이타통신")
            {
                subject1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#grid01_cell_0_16 > nobr")));
                subject2 = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#grid01_cell_0_17 > nobr")));
                cource_code = "COMP0323001";
            }

            //수강신청 정원
            String cource_capacity = subject1.getText();
            String cource_currentState = subject2.getText();

            // 인원 scraping 실패
            if (cource_capacity.isEmpty() || cource_currentState.isEmpty())
            {
                System.out.println("scraping에 실패했습니다");
            }

            // 인원 scraping 성공
            else if (!cource_capacity.isEmpty() && !cource_currentState.isEmpty())
            {
                int number_capacity = Integer.parseInt(cource_capacity);
                int number_currentState = Integer.parseInt(cource_currentState);

                if (number_capacity > number_currentState) {
                    Toolkit toolkit = Toolkit.getDefaultToolkit();
                    for (i = 0; i < 3; i++) {
                        toolkit.beep();
                        try {
                            Thread.sleep(1000); // 1초 대기
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("[" + cource_name + "] 수강신청 가능! 정원: " + cource_capacity + ", 현재 인원: " + cource_currentState);

                    // todo 수강신청 Object Building
                    enrollClass_selenium2_capcha signUp_NotPoket = new enrollClass_selenium2_capcha(cource_name, cource_code); // 수꾸 없는 강의 신청 Object
                    // enrollClass_selenium3 signUp_Poket = new enrollClass_selenium3(cource_name, cource_code); // 수꾸 있는 강의 신청 Object
                    break;
                }
                // 아직 수강 자리가 없음
                else
                {
                    System.out.println( "[" + cource_name + "] 아직 수간신청을 할 수 없습니다");
                    System.out.println("정원: " + number_capacity + " 현재 인원: " + number_currentState);
                }
            }
        }
        driver.quit();
    }
}
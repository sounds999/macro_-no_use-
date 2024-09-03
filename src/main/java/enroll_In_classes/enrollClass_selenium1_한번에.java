package enroll_In_classes;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.time.Duration;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class enrollClass_selenium1_한번에 {
    public static void main(String[] args) {
        // chrome set argument 전달 객체 실행
        ChromeOptions options = new ChromeOptions();
        // WARNING Log Level set (경고 억제)
        Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);
        // background chrome 실행
        // options.addArguments("--headless");
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

        // 대학 선택
        WebElement selectDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#schSbjetCd1")));
        selectDropdown.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#schSbjetCd1 > option:nth-child(6)")));
        WebElement University = driver.findElement(By.cssSelector("#schSbjetCd1 > option:nth-child(6)"));
        University.click();

        // 학과 선택
        WebElement selectDropdown2 = driver.findElement(By.cssSelector("#schSbjetCd2"));
        selectDropdown2.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#schSbjetCd2 > option:nth-child(16)")));
        WebElement it = driver.findElement(By.cssSelector("#schSbjetCd2 > option:nth-child(16)"));
        it.click();

        // 학과 구체적으로 선택
        WebElement selectDropdown3 = driver.findElement(By.cssSelector("#schSbjetCd3"));
        selectDropdown3.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#schSbjetCd3 > option:nth-child(21)")));
        WebElement detail_it = driver.findElement(By.cssSelector("#schSbjetCd3 > option:nth-child(21)"));
        detail_it.click();

        // todo 강의 이름 입력
        // 시스템프로그래밍 : ELEC0462003
        // 데이타베이스 : COMP0322002
        // 네트워크 : EECS0312001 etc. 수꾸 담음
        // 데이타통신 : COMP0323001
        // 프로그래밍기초 : COMP0204004 [실험용]

        try {
            Thread.sleep(random.nextInt(500) + 500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int i = 0;
        // 해당 동작 무한 반복 "조회 -> 수강신청 정원과 수강신청 현재 인원 비교 -> 현재 인원이 더 적을시에 알람"
        while (true) {
            // 매크로 주기적으로 휴식
            if (++i % 32 == 0 || i % 103 == 0) {
                System.out.println("매크로를 잠시 쉽니다 [12초 ~ 1분 32초]");
                try {
                    Thread.sleep(random.nextInt(80000) + 12000);
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
            // 2. JavaScript를 사용하여 오른쪽 및 아래로 이동
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement scrollableContainer = driver.findElement(By.id("grid01_scrollX_right"));
            js.executeScript("arguments[0].scrollLeft = arguments[0].scrollLeft += 300", scrollableContainer); // 현재 위치에서 픽셀 단위로 300이동
            js.executeScript("document.querySelector('#grid01_scrollY_div').scrollTop = 520;");

            // 0.5초에서 1초 대기
            try {
                Thread.sleep(random.nextInt(500) + 500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 데이타베이스 추출 (목요일 오후) (정상적으로 작동 하는지는 currentState에 39 넣어보기 -> 정상적으로 수강신청 단계까지 되는지 확인)
            WebElement database_full = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#grid01_cell_1_16 > nobr")));
            WebElement database_current = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#grid01_cell_1_17 > nobr")));
            String database_capacity = database_full.getText();
            String database_currentState = database_current.getText();
            int numDatabase_capacity = Integer.parseInt(database_capacity);
            int numDatabase_currentState = Integer.parseInt(database_currentState);

            // 네트워크 추출
            WebElement network_full = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#grid01_cell_9_16 > nobr")));
            WebElement network_current = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#grid01_cell_9_17 > nobr")));
            String networt_capacity = network_full.getText();
            String network_currentState = network_current.getText();
            int numNetwork_capacity = Integer.parseInt(networt_capacity);
            int numNetwork_currentState = Integer.parseInt(network_currentState);

            // 시스템프로그래밍 추출
            WebElement systemPrograming_full = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#grid01_cell_12_16 > nobr")));
            WebElement systemPrograming_current = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#grid01_cell_12_17 > nobr")));
            String systemPrograming_capacity = systemPrograming_full.getText();
            String systemPrograming_currentState = systemPrograming_current.getText();
            int numSystem_capacity = Integer.parseInt(systemPrograming_capacity);
            int numSystem_currentState = Integer.parseInt(systemPrograming_currentState);

            // 모바일프로그래밍 (실험)
            WebElement systemPrograming_full2 = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#grid01_cell_4_16 > nobr")));
            WebElement systemPrograming_current2 = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#grid01_cell_4_17 > nobr")));
            String systemPrograming_capacity2 = systemPrograming_full2.getText();
            String systemPrograming_currentState2 = systemPrograming_current2.getText();
            int numSystem_capacity2 = Integer.parseInt(systemPrograming_capacity2);
            // int numSystem_currentState2 = Integer.parseInt(systemPrograming_currentState2);
             int numSystem_currentState2 = 50;

            // 초기값
            int cource_capacity = 0;
            int cource_currentState = 0;
            String selectedSubjectName = null;
            String selectedSUbjectCode = null;

            // 신청할 수 있는 과목 탐색
            if (numSystem_capacity > numSystem_currentState) {
                cource_capacity = numSystem_capacity;
                cource_currentState = numSystem_currentState;
                selectedSubjectName = "시스템프로그래밍";
                selectedSUbjectCode = "ELEC0462003";
            } else if (numSystem_capacity2 > numSystem_currentState2) {
                cource_capacity = numSystem_capacity2;
                cource_currentState = numSystem_currentState2;
                selectedSubjectName = "모바일프로그래밍";
                selectedSUbjectCode = "COMP0328001";
            } else if (numNetwork_capacity > numNetwork_currentState) {
                cource_capacity = numNetwork_capacity;
                cource_currentState = numNetwork_currentState;
                selectedSubjectName = "네트워크프로그래밍";
                selectedSUbjectCode = "EECS0312001";
            } else if (numDatabase_capacity > numDatabase_currentState) {
                cource_capacity = numDatabase_capacity;
                cource_currentState = numDatabase_currentState;
                selectedSubjectName = "데이타베이스";
                selectedSUbjectCode = "COMP0322002";
            }

            if (cource_capacity > cource_currentState) {
                System.out.println("[네트워크프로그래밍] 정원 : " + numNetwork_capacity + " | 현재 인원 : " + numNetwork_currentState);
                WebElement subjectcode1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#grid01_cell_9_6 > nobr")));
                String k1 = subjectcode1.getText();
                System.out.println("과목코드 : " + k1);
                System.out.println("[시스템프로그래밍] 정원 : " + numSystem_capacity + " | 현재 인원 : " + numSystem_currentState);
                WebElement subjectcode2 = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#grid01_cell_12_6 > nobr")));
                String k2 = subjectcode2.getText();
                System.out.println("과목코드 : " + k2);
                System.out.println("[데이타베이스] 정원 : " + numDatabase_capacity + " | 현재 인원 : " + numDatabase_currentState);
                WebElement subjectcode3 = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#grid01_cell_1_6 > nobr")));
                String k3 = subjectcode3.getText();
                System.out.println("과목코드 : " + k3);
                System.out.println("[모바일프로그래밍] 정원 : " + numSystem_capacity2 + " | 현재 인원 : " + numSystem_currentState2);
                WebElement subjectcode4 = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#grid01_cell_4_6 > nobr")));
                String k4 = subjectcode4.getText();
                System.out.println("과목코드 : " + k4);
                System.out.println();

                // todo 수강신청 Object Building
                if (selectedSubjectName == "네트워크프로그래밍") {
                    System.out.println("[" + selectedSubjectName + "] 수강신청 가능! 과목코드: " + selectedSUbjectCode + ", 정원: " + cource_capacity + ", 현재 인원: " + cource_currentState);
                    System.out.println("해당 과목은 수강꾸러미를 신청한 과목입니다");
                    System.out.println("capcha API를 사용하지 않습니다");

                    // driver.close는 현재 컨트롤 하고 있는 창만 닫음
                    // driver.quit은 driver가 가지고 있는 resourse를 모두 정리하고 창도 닫음
                    driver.quit();
                    enrollClass_selenium3_N0_capcha signUp_Poket = new enrollClass_selenium3_N0_capcha(selectedSubjectName, selectedSUbjectCode); // 수꾸 있는 강의 신청 Object
                } else {
                    System.out.println("[" + selectedSubjectName + "] 수강신청 가능! 과목코드: " + selectedSUbjectCode + ", 정원: " + cource_capacity + ", 현재 인원: " + cource_currentState);
                    System.out.println("해당 과목은 수강꾸러미를 신청하지 않은 과목입니다");
                    System.out.println("capcha API를 사용합니다");
                    driver.quit();
                    enrollClass_selenium2_capcha signUp_NotPoket = new enrollClass_selenium2_capcha(selectedSubjectName, selectedSUbjectCode); // 수꾸 없는 강의 신청 Object
                }
                break;
            }
            // 아직 수강 자리가 없음
            else {
                System.out.println("아직 수강신청을 할 수 없습니다");
                System.out.println("[네트워크프로그래밍] 정원 : " + numNetwork_capacity + " | 현재 인원 : " + numNetwork_currentState);
                WebElement subjectcode1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#grid01_cell_9_6 > nobr")));
                String k1 = subjectcode1.getText();
                System.out.println("과목코드 : " + k1);
                System.out.println("[시스템프로그래밍] 정원 : " + numSystem_capacity + " | 현재 인원 : " + numSystem_currentState);
                WebElement subjectcode2 = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#grid01_cell_12_6 > nobr")));
                String k2 = subjectcode2.getText();
                System.out.println("과목코드 : " + k2);
                System.out.println("[데이타베이스] 정원 : " + numDatabase_capacity + " | 현재 인원 : " + numDatabase_currentState);
                WebElement subjectcode3 = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#grid01_cell_1_6 > nobr")));
                String k3 = subjectcode3.getText();
                System.out.println("과목코드 : " + k3);
                System.out.println("[모바일프로그래밍] 정원 : " + numSystem_capacity2 + " | 현재 인원 : " + numSystem_currentState2);
                WebElement subjectcode4 = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#grid01_cell_4_6 > nobr")));
                String k4 = subjectcode4.getText();
                System.out.println("과목코드 : " + k4);
                System.out.println();
            }
        }
    }
}

import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.edge.EdgeDriver;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Weston Prosser\\Downloads\\edgedriver_win64\\msedgedriver.exe");
        WebDriver driver = new EdgeDriver();

        //Asks user for the location of the webpage to nativgate to -- Must be OP.GG
        System.out.println("Please enter a link to the desired OP.GG account: ");
        String pageLocation = sc.nextLine();

        //Gets number of times program loops
        System.out.println("Please the number of games to gather data from: ");
        int numberOfRuns = sc.nextInt();

        //Navigates to the desired location
        driver.get(pageLocation);

        double totalTime = 0;
        int count = 0;

        int x = numberOfRuns;

        while (count < x) {
            List<WebElement> lengths = driver.findElements(By.className("length")); // replace w/ the actual class name

            for(int i = 0; i < lengths.size() && count < x; i++, count++){
                String length = lengths.get(i).getText(); // get the text of the element
                String[] timeParts = length.split(" "); // split the time into minutes and seconds
                if (isNumeric(timeParts[0].replace("m", "")) && isNumeric(timeParts[1].replace("s", ""))) {
                    int minutes = Integer.parseInt(timeParts[0].replace("m", ""));
                    int seconds = Integer.parseInt(timeParts[1].replace("s", ""));
                    double time = minutes + (double)seconds / 60; // convert time to decimal
                    totalTime += time; // add the time to the total
                    System.out.println(length + " -- mins: " + minutes + " -- secs: " + seconds + " " + " -- Final Time: " + time);
                }
            }

            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

                // Click the "show more" button
                WebElement showMoreButton = driver.findElement(new By.ByXPath("/html/body/div[1]/div[7]/div[2]/button")); // replace "showMore" with the actual class name
                showMoreButton.click();

                Thread.sleep(500); // wait for 5 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (NoSuchElementException e) {
                System.out.println("Error - No such element exception -");
                break;
            }
        }

        System.out.println("Total time: " + totalTime);
        System.out.println("Total times in hours: " + totalTime/60);
        System.out.println("Count: " + count);

        driver.quit();
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}


#!/bin/bash

# Create SeleniumScript.java with required contents
cat << 'EOF' > SeleniumScript.java
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver; // _define-ocg_ imported Firefox
import org.openqa.selenium.JavascriptExecutor;

public class SeleniumScript {
    public static void main(String[] args) {
        // Variable creation
        WebDriver varOcg = new FirefoxDriver();
        varOcg.get("http://coderbyte.com/challenges");

        // Search using varFiltersCg
        org.openqa.selenium.WebElement varFiltersCg = varOcg.findElement(By.id("searchBox"));
        varFiltersCg.sendKeys("front-end");

        varOcg.findElement(By.id("searchButton")).click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Scroll down 1200 pixels
        JavascriptExecutor js = (JavascriptExecutor) varOcg;
        js.executeScript("window.scrollBy(0, 1200);");

        varOcg.quit();
    }
}
EOF

# Print the Bash script's own content
cat "$0"

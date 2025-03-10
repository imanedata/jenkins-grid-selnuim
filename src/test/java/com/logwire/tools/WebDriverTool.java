package com.logwire.tools;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.MalformedURLException;
import java.net.URL;

public class WebDriverTool {
    private static WebDriver driver;

    public static void setUpDriver() {

        if (driver == null) {
            ChromeOptions options = new ChromeOptions();
            

            
            String gridUrl = System.getProperty("selenium.grid.url", System.getenv("SELENIUM_GRID_URL"));
            
                gridUrl = "http://192.168.1.112:4444/wd/hub"; 
            



            try {
                driver = new RemoteWebDriver(new URL(gridUrl), options);
                System.out.println("[SETUP] WebDriver initialisé avec succès !");

            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw new RuntimeException("[SETUP] URL du Selenium Grid invalide !");
            } 
        } else {
            System.out.println("[SETUP] WebDriver déjà initialisé !");
        }

        driver.manage().window().maximize();
    }
    

    public static WebDriver getDriver() { // Rend la méthode publique
        return driver;
    }

    public static void tearDown() { // Rend la méthode publique
        if (driver != null) {
            driver.quit();
            driver = null;
            System.out.println("WebDriver fermé avec succès.");
        }
    }
}

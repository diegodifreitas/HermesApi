/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hermes.test.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 *
 * @author NPDI-04
 */
public class WebDriverSingleton {

    private static WebDriver driver;
    private static int navegador = 1;
    private static boolean remoto = false;
    private static String URL_HUB = ALMUtils.getProperties("test.url");

//metodo para selecionar o browser a ser usado
    public static WebDriver get() {
        if (driver == null) {
            DesiredCapabilities dc = null;
            switch (navegador) {
//selecionar FireFox
                case 0:
                    driver = new FirefoxDriver();
                    break;
//selecionar Chrome
                case 1:
                    dc = DesiredCapabilities.chrome();
                    dc.setJavascriptEnabled(true);
                    dc.setCapability("chrome.binary", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
                    System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
                    if (!remoto) {
                        driver = new ChromeDriver(dc);
                    }
                    break;
//Selecionar IE
                case 2:
                    dc = DesiredCapabilities.internetExplorer();
                    System.setProperty("webdriver.ie.driver", "drivers/IEDriverServer.exe");
                    dc.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                    if (!remoto) {
                        driver = new InternetExplorerDriver(dc);
                    }
                    break;
                default:
                    throw new RuntimeException("Navegador \"" + navegador + "\" desconhecido");

            }
            if (remoto) {
                try {
                    driver = new RemoteWebDriver(new URL(URL_HUB), dc);
                } catch (MalformedURLException e) {
                    throw new RuntimeException("URL do hub inválida!");
                }
            }

            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        }
        return driver;

    }
//Capturar evidencias

    public static void capturaEvidencia(String nomeEvidencia) {
        try {
            File evidencia = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(evidencia, new File("evidencias/" + nomeEvidencia + ".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//Fechar o browser
    public static void reiniciarOperacao() {
        driver.quit();
        driver = null;
        WebDriverSingleton.get();
    }

}

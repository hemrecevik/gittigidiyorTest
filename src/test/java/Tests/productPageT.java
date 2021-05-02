package Tests;

import Base.BaseClass;
import org.junit.ComparisonFailure;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utilities.Log;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class productPageT extends BaseClass {
    WebDriver driver;

    public productPageT(){
        driver=super.driver;
    }

    public void selectProduct(){


        try {
            Random rand = new Random();

            List<WebElement> proCount = driver.findElements(By.xpath("//ul[contains(@class,'catalog-view')]/li"));

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//ul[contains(@class,'catalog-view')]/li["+rand.nextInt(proCount.size())+"]/a")));

            Log.info("Ürün seçildi "+getproductName());
            Log.info("Ürünün sayfadaki fiyatı = "+getproductPagePrice());
        }catch (ComparisonFailure e){
            Log.error("Ürün seçilemedi. ");

        }

    }

    public  String getproductName(){
        try {
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            WebElement productName = driver.findElement(By.id("sp-title"));
            return productName.getText();
        }catch (ComparisonFailure e){
            return null;
        }
    }
    public Double getproductPagePrice(){
        try {
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            WebElement pricetext = driver.findElement(By.id("sp-price-lowPrice"));
            if (pricetext.getText()=="")
            {
                WebElement pricetext2 = driver.findElement(By.id("sp-price-highPrice"));
                return priceDouble(pricetext2.getText());
            }
            return priceDouble(pricetext.getText());
        }catch (ComparisonFailure e){
            return null;
        }
    }

    public double priceDouble(String price){
        if (price!=null)
        {
            return  Double.parseDouble(price
                    .replace(".","")
                    .replace(",",".")
                    .split(" ")[0]);
        }
      return  0;
    }

}

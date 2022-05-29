package com.Page;

import com.thoughtworks.gauge.Step;
import com.utilities.base.BasePage;
import com.utilities.base.BaseTest;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginPage extends BaseTest{

    private WebDriver driver = BaseTest.getDriver();
    private BasePage basePage = new BasePage(driver);

    public By girisyapuyeolbuton=By.xpath("(//span[contains(text(),'Giriş')])[1]");
    public By telnogir=By.id("phone-input");
    public By hosgeldintxt=By.xpath("//div[contains(text(),'Hoş Geldin!')]");
    public By dogrulamakodutxt=By.xpath("//div[contains(text(),'Doğrulama Kodunu Gir')]");
    public By gecersiztelnomesaji=By.xpath("//span[contains(@class,'warn-text')]");
    public By Hatalidogrulamakodumesaji=By.xpath("//p[contains(@class,'otp-warn-text')]");


    @Step("Sayfayi ac")
    public void sayfayiac(){
        basePage.navigateTo();
        basePage.threadSleep(3000);
    }

    @Step("Giris yap veya uye ol butonuna tikla")
    public void girisyapveyauyeolbutonunatikla(){

        basePage.findElement(girisyapuyeolbuton).click();
        basePage.waitUntilExpectedElement(hosgeldintxt,"15");
    }

    @Step("Telefon numarasi gir <telno> ve <butonismii> butonuna tikla")
    public void telefonnumarasigir(String telno,String butonismii){
        basePage.findElement(telnogir).click();
        basePage.threadSleep(2000);
        basePage.fillInputField(telnogir,telno);
        basePage.threadSleep(2000);
        butonunatikla(butonismii,"save-btn");
        if (driver.findElements(dogrulamakodutxt).size()==1) {
            basePage.waitUntilExpectedElement(dogrulamakodutxt, "15");
        }else{
            basePage.waitUntilExpectedElement(hosgeldintxt, "15");
        }
    }

    @Step("Gecersiz telefon numarası uyarisi <uyari> kontrol")
    public void gecersiztelnokontrol(String uyari){
        String ekrandanalinanuyari= basePage.findElement(gecersiztelnomesaji).getText();
        System.out.println("Ekrandan alinan uyari :"+ekrandanalinanuyari);

        Assert.assertEquals(uyari,ekrandanalinanuyari);
        basePage.logger(ekrandanalinanuyari);
    }

    @Step("Dogrulama Kodu Gir")
    public void dogrulamakodugir(){
        int size= driver.findElements(By.xpath("//input[contains(@class,'otp-inp')]")).size();
        System.out.println("Size :"+size);
        for(int i=1; i<=size ;i++ ){
            basePage.fillInputField(By.xpath("(//input[contains(@class,'otp-inp')])["+i+"]"),"1");
            basePage.threadSleep(2000);
        }
        butonunatikla("Alışverişe Başla","send-otp-button");

    }

    @Step("<butonismi> butonuna tikla <classtag>")
    public void butonunatikla(String butonismi,String classtag){
        basePage.findElementTest(By.xpath("//span[contains(@class,'"+classtag+"')]")).click();
        basePage.threadSleep(2000);
    }

    @Step("Hatali kod uyarisi <uyari> kontrol")
    public void hatalikodkontrol(String uyari){
        String ekrandanalinanuyari= basePage.findElement(Hatalidogrulamakodumesaji).getText();
        System.out.println("Ekrandan alinan uyari :"+ekrandanalinanuyari);

        Assert.assertEquals(uyari,ekrandanalinanuyari);
        basePage.logger(ekrandanalinanuyari);
    }

}

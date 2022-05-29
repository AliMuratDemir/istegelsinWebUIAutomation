package com.Page;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.datastore.DataStoreFactory;
import com.utilities.base.BasePage;
import com.utilities.base.BaseTest;
import org.junit.Assert;
import org.openqa.selenium.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class searchPage extends BaseTest {

    private WebDriver driver = BaseTest.getDriver();
    private BasePage basePage = new BasePage(driver);
    private  LoginPage loginpage= new LoginPage();
    splitmethod SplitMethod =new splitmethod();

    public By aramacubugu=By.xpath("(//input[contains(@class,'search-inp')])[2]");
    public By aramayazisi=By.xpath("//strong[contains(text(),'Arama')]");
    public By urunsayisiyazisi=By.xpath("//span[contains(text(),'aramasına')]");
    public By listelenenenurunsayisi=By.xpath("//span[contains(@class,'discountless-price')]");
    public By Onerilensiralamatikla=By.xpath("//select[contains(@class,'ig-control-inp')]");
    public By ilcetikla=By.id("district");
    public By mahalletikla=By.xpath("//select[contains(@id,'neighborhood')]");
    public By caddetikla=By.xpath("//input[contains(@id,'street')]");
    public By caddesec=By.xpath("(//div[contains(@class,'autocomplete-list-item')])[1]");
    public By binatikla=By.xpath("//input[contains(@id,'buildingNumber')]");
    public By adresismitikla=By.xpath("//input[contains(@id,'addressName')]");
    public By sepetimtikla=By.xpath("(//span[contains(text(),'Sepetim')])[2]");
    public By sepetegittikla=By.xpath("//div[contains(@class,'go-to-basket-button color-orange--')]");


    @Step("Arama cubuguna <urunismi> yaz ve ara")
    public void aramacubugunayaz(String urunismi){
        basePage.findElement(aramacubugu).click();
        basePage.threadSleep(2000);
        basePage.fillInputField(aramacubugu,urunismi);
        basePage.threadSleep(2000);
        basePage.findElement(aramacubugu).sendKeys(Keys.ENTER);
        basePage.waitUntilExpectedElement(aramayazisi,"15");
    }

    @Step("Urun sayisinin kontrolu")
    public void urunsayisininkontrolu(){
        basePage.scrollToElement(By.xpath("//div[contains(text(),'Kurumsal')]"));
        basePage.threadSleep(4000);
        int size=driver.findElements(By.xpath("//span[contains(@class,'product-name')]")).size();
        System.out.println("Size :"+size);
        String urunsayisi=Integer.toString(size);

        String ekrandanalinanurunsayisi=basePage.removeEmptySpace(basePage.getWebElementText(urunsayisiyazisi));
        System.out.println("ekranda alinan urun sayisi :"+ekrandanalinanurunsayisi);
        String splitbirekrantxt= ekrandanalinanurunsayisi.split("ürün")[0];
        System.out.println("Splitbir ekran txt :"+splitbirekrantxt);
        String splitikiekrantxt=splitbirekrantxt.split("ait")[1];
        System.out.println("Splitiki ekran txt :"+splitikiekrantxt);

        //Assert kontrolu ve loglama
        Assert.assertEquals(urunsayisi,splitikiekrantxt);
        basePage.logger("Ekrandan alinan ürün sayisi :"+splitbirekrantxt);
        basePage.logger("Listelenen ürünlerin sayisi :"+urunsayisi);
    }

    @Step("En Dusuk fiyata gore sıralama <filtreismi> ve konrol")
    public void endusukfiyatagoresıralamavekontrol(String filtreismi) throws AWTException {
        //Filtreleme yapmadan gelen urunlerin fiyatlarını array listte atma
        int a=driver.findElements(listelenenenurunsayisi).size();
        System.out.println("Size filtresiz :"+a);


        ArrayList<Integer> filtresizfiyatlar = new ArrayList<>();
        for (int i=1; i<=a;i++){
            System.out.println("*************************** "+i+". Urun Fiyati *********************************");
            String b= basePage.removeEmptySpace(basePage.getWebElementText(By.xpath("(//span[contains(@class,'discountless-price')])["+i+"]")));
            System.out.println("Ekrandan alinan fiyat :"+b);
            String split=SplitMethod.split(b);
            int d=Integer.valueOf(split);
            filtresizfiyatlar.add(d);
        }
        System.out.println("Filtresiz Fiyatlar Array :"+filtresizfiyatlar);


        //filtesiz alinan fiyatların en dusukten en yuksege göre sıralama

        Collections.sort(filtresizfiyatlar);
        System.out.println("En dusukten en yuksege sirlanmis fiyatlar :"+filtresizfiyatlar);

        //filtreye tikla ve filtre seç
        basePage.findElement(By.xpath("//div[contains(@class,'v2-go-up-sticky')]")).click();
        basePage.threadSleep(3000);
        basePage.findElement(Onerilensiralamatikla).click();
        basePage.threadSleep(2000);
        basePage.findElement(By.xpath("//option[contains(text(),'"+filtreismi+"')]")).click();
        basePage.threadSleep(4000);
        basePage.findElement(By.xpath("//input[contains(@class,'search-inp')]")).click();
        basePage.threadSleep(4000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,3000)", "");



        ArrayList<Integer> filtrelifiyatlar = new ArrayList<>();

        for (int i=1; i<=a;i++){
            System.out.println("*************************** "+i+". Urun Fiyati *********************************");
            String b= basePage.removeEmptySpace(basePage.getWebElementText(By.xpath("(//span[contains(@class,'discountless-price')])["+i+"]")));
            System.out.println("Ekrandan alinan fiyat :"+b);
            String split=SplitMethod.split(b);
            int d=Integer.valueOf(split);
            filtrelifiyatlar.add(d);
        }
        System.out.println("Filtreli Fiyatlar Array :"+filtrelifiyatlar);

        //Assert kontrolu ve loglama
        Assert.assertEquals(filtresizfiyatlar,filtrelifiyatlar);
        basePage.logger("Filtresiz fiyatları alıp en düsükten en büyük fiyata gore sıralama :"+filtresizfiyatlar);
        basePage.logger("Filtreden en düsükten en büyük fiyata gore sıralama :"+filtrelifiyatlar);

        int e= filtrelifiyatlar.get(0);
        System.out.println("sifirinci eleman :"+e);
        basePage.findElement(By.xpath("//div[contains(@class,'v2-go-up-sticky')]")).click();
        basePage.threadSleep(3000);

    }

    @Step("En dusuk fiyat olan urunu veya urunleri sepete ekleme")
    public void endusukfiyatolanurunusepeteekleme(){

        if (driver.findElements(By.xpath("(//div[contains(@class,'non-prod-cap')])[1]")).size()==1){
            basePage.logger("En dusuk urunde yenisi geliyor yazıyor en dusuk fiyat olan ikinci urunu sepete ekliycek");
            String sepeteeklenmedenalinanfiyat= basePage.removeEmptySpace(basePage.getWebElementText(By.xpath("(//div[contains(@class,'price-box-right')])[1]//parent::div//descendant::span[2]")));
            String split=SplitMethod.split(sepeteeklenmedenalinanfiyat);
            int d=Integer.valueOf(split);
            DataStoreFactory.getScenarioDataStore().put("SepeteEklenmedenoncekifiyat",d);
            basePage.findElement(By.xpath("(//div[contains(@class,'add-cap')])[1]//img")).click();
            basePage.threadSleep(2000);
            adresekleme();
            basePage.waitUntilExpectedElement(By.xpath("(//span[contains(@class,'count-notify')])[2]"),"15");
            basePage.findElement(sepetimtikla).click();
            basePage.threadSleep(2000);
            basePage.findElement(sepetegittikla).click();
            basePage.waitUntilExpectedElement(By.xpath("(//span[contains(text(),'SEPETİM')])[1]"),"30");
        }else{
            System.out.println("********************** ilk urun ekleniyor sepete *************************************");
            String sepeteeklenmedenalinanfiyat= basePage.removeEmptySpace(basePage.getWebElementText(By.xpath("(//div[contains(@class,'price-box-right')])[1]//parent::div//descendant::span[2]")));
            String split=SplitMethod.split(sepeteeklenmedenalinanfiyat);
            int d=Integer.valueOf(split);
            DataStoreFactory.getScenarioDataStore().put("SepeteEklenmedenoncekifiyat",d);
            basePage.findElement(By.xpath("(//div[contains(@class,'add-cap')])[1]//img")).click();
            basePage.threadSleep(2000);
            adresekleme();
            basePage.waitUntilExpectedElement(By.xpath("(//span[contains(@class,'count-notify')])[2]"),"15");
            basePage.findElement(sepetimtikla).click();
            basePage.threadSleep(2000);
            basePage.findElement(sepetegittikla).click();
            basePage.waitUntilExpectedElement(By.xpath("(//span[contains(text(),'SEPETİM')])[1]"),"30");
        }

    }

    @Step("Adres ekleme")
    public void adresekleme(){
        if (driver.findElements(By.xpath("(//span[contains(@class,'count-notify')])[2]")).size()==0) {
            loginpage.butonunatikla("Yeni Adres Ekle", "add-new-address");
            basePage.threadSleep(2000);
            basePage.findElement(ilcetikla).click();
            basePage.threadSleep(2000);
            basePage.findElement(By.xpath("//option[contains(@id,'Ataşehir')]")).click();
            basePage.threadSleep(2000);
            basePage.findElement(mahalletikla).click();
            basePage.threadSleep(2000);
            basePage.findElement(By.xpath("//option[contains(@id,'Aşıkveysel Mh.')]")).click();
            basePage.threadSleep(2000);
            basePage.findElement(caddetikla).click();
            basePage.threadSleep(1000);
            basePage.findElement(caddesec).click();
            basePage.threadSleep(2000);
            basePage.findElement(binatikla).click();
            basePage.threadSleep(1000);
            basePage.fillInputField(binatikla, "B");
            basePage.threadSleep(2000);
            basePage.findElement(adresismitikla).click();
            basePage.threadSleep(1000);
            basePage.fillInputField(adresismitikla, "Ev");
            basePage.threadSleep(2000);
            loginpage.butonunatikla("Kaydet", "save-btn");
        }
    }

    @Step("En Dusuk fiyat olan urun kontrol")
    public void endusukfiyatolanurunkontrol(){

        int sepeteeklenmedenkiendusukfiyat=Integer.valueOf((Integer) DataStoreFactory.getScenarioDataStore().get("SepeteEklenmedenoncekifiyat"));
        System.out.println("sepeteeklenmedenkiendusukfiyat :"+sepeteeklenmedenkiendusukfiyat);

        String sepettenalinanfiyat=basePage.removeEmptySpace(basePage.getWebElementText(By.xpath("//div[contains(@class,'normal-price')]")));
        String split=SplitMethod.split(sepettenalinanfiyat);
        int sepettekifiyat=Integer.valueOf(split);

        //Assert kontrolu ve loglama
        Assert.assertEquals(sepeteeklenmedenkiendusukfiyat,sepettekifiyat);
        basePage.logger("Sepete eklenmedenki en düsük fiyatli urun :"+sepeteeklenmedenkiendusukfiyat);
        basePage.logger("Sepetteki fiyat:"+sepettekifiyat);
    }
}

/**
 * Created by zll on 2017/7/3.
 * 使用PhantomJS访问网页 http://10.4.51.164:8080 ，得到字段：“Mock数据”，反复访问100次，并截图。
 */
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class test {


    //private static String jspath ="/Users/zll/Downloads/phantomjs-2.1.1-macosx/bin/phantomjs";
    private static String jspath ="/root/phantomjs-2.1.1-linux-x86_64/bin/phantomjs";
    private static String url ="http://10.4.51.164:8080";

    public static void main(String[] args) throws InterruptedException {


        for(int i=0;i<100;i++) {
            jstest(i);
        }
    }

    private static void jstest(int count) throws InterruptedException {
        File file = new File(jspath);
        System.setProperty("phantomjs.binary.path",file.getAbsolutePath());
        DesiredCapabilities caps;
        caps = DesiredCapabilities.phantomjs();
        caps.setCapability("phantomjs.page.settings.userAgent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.120 Safari/537.36");
        WebDriver wd = new PhantomJSDriver(caps);
        wd.get(url);
        wd.navigate().to(url);

        //button id click-me
        WebElement clickme = wd.findElement(By.id("click-me"));
        clickme.click();
        Thread.sleep(1000);

        WebElement ajax = wd.findElement(By.id("ajax"));

        if (ajax.getText().equals("Mock数据")){

            File screenShotFile = ((TakesScreenshot) wd).getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(screenShotFile, new File("./png/"+count+".png"));
                wd.close();
            } catch (IOException e) {
                wd.close();
                e.printStackTrace();
            }

        }else{
            wd.close();
        }
    }
}
import org.checkerframework.checker.units.qual.A;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AmazonPageTest {

    AmazonPage amp = new AmazonPage(new ChromeDriver());
    @Test
    public void testPageLoad() {
        String currentUrl = amp.getDriver().getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://www.amazon.com/");
    }

    @Test
    public void testSearch() {
        amp.setFilter();
        amp.search("Java");
        Assert.assertEquals(amp.getSearchLine().getAttribute("value"), "Java");
    }



}

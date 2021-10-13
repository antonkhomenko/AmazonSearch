import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

public class AmazonPage {
    private WebDriver driver;
    private String url = "https://www.amazon.com/";

    AmazonPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.driver.get(url);
    }

    //filter Books
    @FindBy(xpath = "//option[text()=\"Books\"]")
    private WebElement filter;

    //searchLine
    @FindBy(xpath = "//input[@name=\"field-keywords\"]")
    private WebElement searchLine;


    public WebDriver getDriver() {
        return driver;
    }

    public WebElement getSearchLine() {
        return searchLine;
    }

    public void setFilter() {
        filter.click();
    }

    public void search(String s) {
        searchLine.sendKeys(s);
        searchLine.submit();
    }


    //Books
    private List<Book> books = new ArrayList<>();
    private List<WebElement> authorList;
    private List<WebElement> searchRes;
    private List<WebElement> booksTitleList;

    public List<Book> getBooks() {
        return books;
    }

    public void initializeBooksList() {
        searchRes = driver.findElements(By.xpath("//div[@data-component-type=\"s-search-result\"]/div/span/div/div/div[2]/div[2]/div"));
        for (int i = 0; i < searchRes.size();i++) {
            books.add(new Book());
        }
        getBooksPriceList();
        getBooksTitleList();
        getAuthorsList();
    }

    private void getAuthorsList() {
        authorList = driver.findElements(By.cssSelector(".a-section.a-spacing-none > .a-section.a-spacing-none > .a-row.a-size-base.a-color-secondary > .a-row"));
        for(int i = 0; i < authorList.size(); i++) {
            String s = authorList.get(i).getText();
            if(!s.contains("by ")) {
                authorList.remove(i);
                --i;
            } else {
                String sub = authorList.get(i).getText();
                int begin = sub.indexOf("by") + 2;
                int end = sub.indexOf(" |");
                String author;
                if(begin > end) {
                    author = sub.substring(begin);
                    author = author.substring(0, author.indexOf('|'));
                    author = author.trim();
                    books.get(i).setAuthor(author);
                } else {
                    author = sub.substring(begin, end);
                    author = author.trim();
                    books.get(i).setAuthor(author);
                }
            }
        }
    }

    private void getBooksTitleList() {
        booksTitleList = driver.findElements(By.xpath("//h2/a/span[@class=\"a-size-medium a-color-base a-text-normal\"]"));
        for (int i = 0; i < books.size(); i++) {
            String title = booksTitleList.get(i).getText();
            books.get(i).setTitle(title);
        }
    }

    private void getBooksPriceList() {
        //List<WebElement> booksPriceList = driver.findElements(By.xpath("//div[@class=\"a-section a-spacing-none a-spacing-top-small\"]/div[@class=\"a-row a-size-base a-color-base\"][2]/a/span[@class=\"a-price\"]/span[@class=\"a-offscreen\"]"));
        //List<WebElement> booksPriceList = driver.findElements(By.xpath("//div[@class=\"a-section a-spacing-none a-spacing-top-small\"]/div[@class=\"a-row a-size-base a-color-base\"][2]/a/span[@class=\"a-price\"]/span[@aria-hidden=\"true\"]/span[@class=\"a-price-whole\"]"));
        //div/div[3]/div[1]/div/div[1]/div[2]/a/span/span[2]/span[2]
        for (int i = 0; i < searchRes.size(); i++) {
            Double s;
            try {
                WebElement wb = searchRes.get(i).findElement(By.xpath("./div/div[3]/div[1]/div/div[1]/div[2]/a/span/span[2]/span[2]"));
                WebElement wb2 = searchRes.get(i).findElement(By.xpath("./div/div[3]/div[1]/div/div[1]/div[2]/a/span/span[2]/span[3]"));
                String s1 = wb.getText() + "." + wb2.getText();
                s = Double.parseDouble(s1);
            } catch(Exception e) {
                s = -1.0;
            }
            books.get(i).setPrice(s);
        }
    }

    public boolean containsBook(Book b) {
        for(int i = 0; i < books.size(); i++) {
            String author = books.get(i).getAuthor().toLowerCase();
            String title = books.get(i).getTitle().toLowerCase();
            double price = books.get(i).getPrice();
            if(b.getTitle().toLowerCase().equals(title) && b.getAuthor().toLowerCase().equals(author) && b.getPrice() == price) {
                return true;
            }
        }
        return false;
    }

}

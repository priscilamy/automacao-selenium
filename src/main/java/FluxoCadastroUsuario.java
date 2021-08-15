import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FluxoCadastroUsuario {
	
	private WebDriver driver;
	private WebDriverWait wait;
	
	private String url = "http://automationpractice.com/index.php";
	
	// elementos
	private String pathBotaoSign = "#header > div.nav > div > div > nav > div.header_user_info > a";
	private String idInputNovoEmail = "email_create";
	private String idBotaoCreateAnAccount = "SubmitCreate";
	private String novoEmail = "novo_email_" + System.currentTimeMillis() + "@gmail.com";
	private String pathTituloPersonalInformation = "#account-creation_form > div:nth-child(1) > h3";	
	private String pathRadioGender = "id_gender1";
	private String idFirstName = "customer_firstname";
	private String idLastName = "customer_lastname";
	private String idPasswd = "passwd";
	private String idEmail = "email";
	private String idDays = "days";
	private String idYears = "years";
	private String idMonths = "months";
	private String idAddressFirstName = "firstname";
	private String idAddressLastName = "lastname";
	private String idAddressRoad = "address1";
	private String idAddressCity = "city";
	private String idAddressState = "id_state";
	private String idAddressCountry = "id_country";
	private String idAddressPostCode = "postcode";
	private String idAddressPhone = "phone_mobile";
	private String idAdressAlias = "alias";
	private String idSubmitAccount = "submitAccount";
	private String msgCadastroSucesso = "#center_column > p";
		
	@Before
	public void setUpTest() {
	    driver = new ChromeDriver();
		driver.get(url);
		wait = new WebDriverWait(driver, 15);
	}
	
	@After
	public void tearDownTest() {
	    driver.quit();
	}
	
	public void iniciaCriacaoNovaConta() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(pathBotaoSign)));
		driver.findElement(By.cssSelector(pathBotaoSign)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(idInputNovoEmail)));
		driver.findElement(By.id(idInputNovoEmail)).sendKeys(novoEmail);
		driver.findElement(By.id(idBotaoCreateAnAccount)).click();
	}
	
	public void preencheDadosNovaConta() {
		//PREENCHER DADOS DA CONTA:
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(pathTituloPersonalInformation)));
		driver.findElement(By.id(pathRadioGender)).click();
				
		driver.findElement(By.id(idFirstName)).sendKeys("Ciclano");
		driver.findElement(By.id(idLastName)).sendKeys("de Tal");
		driver.findElement(By.id(idPasswd)).sendKeys("novo123");
		
		driver.findElement(By.id(idDays)).click();
		Select dropDownDias = new Select(driver.findElement(By.id(idDays)));
		dropDownDias.selectByValue("10");
		
		driver.findElement(By.id(idMonths)).click();
		Select dropDownMonths = new Select(driver.findElement(By.id(idMonths)));
		dropDownMonths.selectByValue("10");
		
		driver.findElement(By.id(idYears)).click();
		Select dropDownYears = new Select(driver.findElement(By.id(idYears)));
		dropDownYears.selectByValue("2000");

		driver.findElement(By.id(idAddressFirstName)).sendKeys("Ciclano");
		Assert.assertNotNull(driver.findElement(By.id(idAddressFirstName)).getText());
		driver.findElement(By.id(idAddressLastName)).sendKeys("de Tal");
		driver.findElement(By.id(idAddressRoad)).sendKeys("2600 DADEVILLE");
		driver.findElement(By.id(idAddressCity)).sendKeys("ALEXANDER CITY");
		
		driver.findElement(By.id(idAddressState)).click();
		Select dropDownState = new Select(driver.findElement(By.id(idAddressState)));
		dropDownState.selectByValue("1");
		
		driver.findElement(By.id(idAddressPostCode)).sendKeys("32327");
		driver.findElement(By.id(idAddressCountry)).click();
		Select dropDownCountry = new Select(driver.findElement(By.id(idAddressCountry)));
		dropDownCountry.selectByValue("21");

		driver.findElement(By.id(idAddressPhone)).sendKeys("99989898");
		driver.findElement(By.id(idAdressAlias)).sendKeys("My Address");
		driver.findElement(By.id(idSubmitAccount)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(msgCadastroSucesso)));
	}
	
	
	@Test
	public void testarFluxoCadastroUsuario() {
		iniciaCriacaoNovaConta();
		preencheDadosNovaConta();	
	}

}

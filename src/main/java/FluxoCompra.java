import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Test;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FluxoCompra {
	
	private WebDriver driver;
	private WebDriverWait wait;
	
	private String url = "http://automationpractice.com/index.php";
	private String produtoSelecionado1 = "";
	private String produtoSelecionado2 = "";
	private String produtoBuscado = "Printed";
	private String emailUsuario = "usuario.teste5216@gmail.com";
	private String senhaUsuario = "user123";

	// elementos
	private String pathTituloSearch = "#center_column > h1";
	private String pathBotaoAddToCart1 = "#center_column > ul > li:nth-child(2) > div > div.right-block > div.button-container > a.button.ajax_add_to_cart_button.btn.btn-default";                                    
	private String pathBotaoAddToCard2 = "#center_column > ul > li:nth-child(3) > div > div.right-block > div.button-container > a.button.ajax_add_to_cart_button.btn.btn-default";
	private String pathBotaoContinueShopping = "#layer_cart > div.clearfix > div.layer_cart_cart.col-xs-12.col-md-6 > div.button-container > span > span";     
	private String pathBotaoProceedToCheckout = "#layer_cart > div.clearfix > div.layer_cart_cart.col-xs-12.col-md-6 > div.button-container > a > span > i";
	private String idPrecoProduto = "layer_cart_product_price";
	private String pathBotaoProceedToCheckoutConfirm = "#center_column > p.cart_navigation.clearfix > a.button.btn.btn-default.standard-checkout.button-medium";
	private String idPrecoTotalProduto1 = "total_product_price_4_16_0";
	private String idPrecoTotalProduto2 = "total_product_price_7_34_0";
	private String idBotaoSignIn = "SubmitLogin";
	private String pathAlertDangerLogin = "#center_column > div.alert.alert-danger";
    private String pathBotaoProceedToCheckoutAddress = "#center_column > form > p > button";
	private String idDivCheckboxAgreeTerms = "uniform-cgv";
    private String idCheckboxAgreeTerms = "cgv";
	private String pathProceedToCheckoutShipping = "#form > p > button";
	private String pathBankwirePayment = "#HOOK_PAYMENT > div:nth-child(1) > div > p > a";
	private String pathBotaoIConfirmMyOrder = "#cart_navigation > button";
	private String pathTituloOrderIsComplete = "#center_column > div";
	private String pathAddressDeliveryName = "#address_delivery > li.address_firstname.address_lastname";
	private String pathAddressDeliveryRoad = "#address_delivery > li.address_address1.address_address2";
	private String pathAddressDeliveryPostcode = "#address_delivery > li.address_city.address_state_name.address_postcode";
	private String pathAddressDeliveryCountry = "#address_delivery > li.address_country_name";
	private String pathAddressDeliveryPhone = "#address_delivery > li.address_phone";
	private String pathShippingFancyBoxErrorClose = "#order > div.fancybox-overlay.fancybox-overlay-fixed > div > div > a";
	                                              	
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
	
	public void buscarProdutos() {
		driver.findElement(By.id("search_query_top")).sendKeys(produtoBuscado);
		WebElement search = driver.findElement(By.name("submit_search"));
		search.click();
	}
	
	public void adicionaProduto1() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(pathTituloSearch)));			
		driver.findElement(By.cssSelector(pathBotaoAddToCart1)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(pathBotaoContinueShopping)));
		produtoSelecionado1 = driver.findElement(By.id(idPrecoProduto)).getText();
		driver.findElement(By.cssSelector(pathBotaoContinueShopping)).click();
	}
	
	public void adicionaProduto2() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(pathTituloSearch)));
		driver.findElement(By.cssSelector(pathBotaoAddToCard2)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(pathBotaoContinueShopping)));
		produtoSelecionado2 = driver.findElement(By.id(idPrecoProduto)).getText();
		driver.findElement(By.cssSelector(pathBotaoProceedToCheckout)).click();	
	}
	
	public void telaResumoPedido() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(pathBotaoProceedToCheckoutConfirm)));
		driver.findElement(By.id(idPrecoTotalProduto1)).getText();
		driver.findElement(By.id(idPrecoTotalProduto2)).getText();
		Assert.assertEquals(produtoSelecionado1, driver.findElement(By.id(idPrecoTotalProduto1)).getText());
		Assert.assertEquals(produtoSelecionado2, driver.findElement(By.id(idPrecoTotalProduto2)).getText());
		
		driver.findElement(By.cssSelector(pathBotaoProceedToCheckoutConfirm)).click();
	}

	public void telaRealizaLogin() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(idBotaoSignIn)));
		//Login inválido:
		driver.findElement(By.id("email")).sendKeys("");
		driver.findElement(By.id("passwd")).sendKeys("");
		driver.findElement(By.id(idBotaoSignIn)).click();
		Assert.assertTrue(driver.findElement(By.cssSelector(pathAlertDangerLogin)).isDisplayed());
		
		//Login válido
		driver.findElement(By.id("email")).sendKeys(emailUsuario);
		driver.findElement(By.id("passwd")).sendKeys(senhaUsuario);
		driver.findElement(By.id(idBotaoSignIn)).click();
	}
	
	public void assertEnderecoEntrega() {
		Assert.assertTrue(driver.findElement(By.id("addressesAreEquals")).isSelected());
		Assert.assertNotNull(driver.findElement(By.cssSelector(pathAddressDeliveryName)).getText());
		Assert.assertNotNull(driver.findElement(By.cssSelector(pathAddressDeliveryRoad)).getText());
		Assert.assertNotNull(driver.findElement(By.cssSelector(pathAddressDeliveryPostcode)).getText());
		Assert.assertNotNull(driver.findElement(By.cssSelector(pathAddressDeliveryCountry)).getText());
		Assert.assertNotNull(driver.findElement(By.cssSelector(pathAddressDeliveryPhone)).getText());
	}
	
	public void telaEnderecos() {
        wait.until(ExpectedConditions.visibilityOfElementLocated( By.cssSelector(pathBotaoProceedToCheckoutAddress)));
		assertEnderecoEntrega();
		driver.findElement(By.cssSelector(pathBotaoProceedToCheckoutAddress)).click();
	}
	
	public void telaFrete() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(idDivCheckboxAgreeTerms)));
		driver.findElement(By.cssSelector(pathProceedToCheckoutShipping)).click();
		Assert.assertFalse(driver.findElement(By.id(idCheckboxAgreeTerms)).isSelected()); 
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(pathShippingFancyBoxErrorClose)));
		driver.findElement(By.cssSelector(pathShippingFancyBoxErrorClose)).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(idDivCheckboxAgreeTerms)));
		driver.findElement(By.id(idCheckboxAgreeTerms)).click();
		Assert.assertTrue(driver.findElement(By.id(idCheckboxAgreeTerms)).isSelected());
		driver.findElement(By.cssSelector(pathProceedToCheckoutShipping)).click();
		        
	}
	
	public void telaPagamento() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(pathBankwirePayment)));
		driver.findElement(By.cssSelector(pathBankwirePayment)).click();	
	}
	
	public void telaResumoPagamento() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(pathBotaoIConfirmMyOrder)));
		driver.findElement(By.cssSelector(pathBotaoIConfirmMyOrder)).click();
	}
	
	public String telaPedidoFinalizado() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(pathTituloOrderIsComplete)));
		String textoOrdemPedido = driver.findElement(By.cssSelector(pathTituloOrderIsComplete)).getText();
		Assert.assertNotNull(textoOrdemPedido);
		return textoOrdemPedido;
	}
	
	public void gravaOrdemPedido(String dadosOrdemPedido) throws IOException {
		String fileName = System.getProperty("user.dir") + "\\ordensPedido\\ordem" + System.currentTimeMillis() + ".txt";
		FileWriter arq = new FileWriter(fileName);
	    try {
	    	PrintWriter gravarArq = new PrintWriter(arq);
		    try {
		    	gravarArq.printf(dadosOrdemPedido);
		    } finally {
		    	gravarArq.close();
		    }
	    } finally {
	    	arq.close();	
	    }
	}
	
	@Test
	public void testarFluxoCompra() throws IOException {
		
		buscarProdutos();		
		adicionaProduto1();

		// ADICIONA PRODUTO 2 >>>>
		// (OBS: USADO DESSA FORMA PARA EVITAR BUG ESPORÁDICO)
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(pathTituloSearch)));
		driver.findElement(By.cssSelector(pathBotaoAddToCard2)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(pathBotaoContinueShopping)));
		produtoSelecionado2 = driver.findElement(By.id(idPrecoProduto)).getText();
		driver.findElement(By.cssSelector(pathBotaoProceedToCheckout)).click();
		// <<<< ADICIONADO PRODUTO 2

		telaResumoPedido();
		telaRealizaLogin();
		telaEnderecos();
		telaFrete();
		telaPagamento();
		telaResumoPagamento();
		String ordemPedido = telaPedidoFinalizado();
		gravaOrdemPedido(ordemPedido);
	}
}




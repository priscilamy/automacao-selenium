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
    private String pathBotaoProceedToCheckoutAddress = "#center_column > form > p > button";
	private String idDivCheckboxAgreeTerms = "uniform-cgv";
    private String idCheckboxAgreeTerms = "cgv";
	private String pathProceedToCheckoutShipping = "#form > p > button";
	private String pathBankwirePayment = "#HOOK_PAYMENT > div:nth-child(1) > div > p > a";
	private String pathBotaoIConfirmMyOrder = "#cart_navigation > button";
	private String pathTituloOrderIsComplete = "#center_column > div";
	
	@Before
	public void iniciarDriver() {
	    driver = new ChromeDriver();
		driver.get(url);
		wait = new WebDriverWait(driver, 10);
	}
	
	@After
	public void encerrarDriver() {
	    driver.quit();
	}

	@Test
	public void buscaProdutos() throws IOException {
		
		//Busca produtos
		driver.findElement(By.id("search_query_top")).sendKeys(produtoBuscado);
		WebElement search = driver.findElement(By.name("submit_search"));
		search.click();
		
		// ADICIONA PRODUTO 1
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(pathTituloSearch)));			
		//TODO fazer a asserção do nome (ou SKU) do produto
		driver.findElement(By.cssSelector(pathBotaoAddToCart1)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(pathBotaoContinueShopping)));
		//Pega o valor do produto1 para comparar no final do carrinho
		produtoSelecionado1 = driver.findElement(By.id(idPrecoProduto)).getText();
		//clica no botão de continuar compras
		driver.findElement(By.cssSelector(pathBotaoContinueShopping)).click();
		
		// ADICIONA PRODUTO 2
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(pathTituloSearch)));
		driver.findElement(By.cssSelector(pathBotaoAddToCard2)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(pathBotaoContinueShopping)));
		//Pega o valor do produto2 para comparar no final do carrinho
		produtoSelecionado2 = driver.findElement(By.id(idPrecoProduto)).getText();
		driver.findElement(By.cssSelector(pathBotaoProceedToCheckout)).click();	
		
		//tela de carrinho
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(pathBotaoProceedToCheckoutConfirm)));
		//verificar se os valores dos produtos correspondem:
		driver.findElement(By.id(idPrecoTotalProduto1)).getText();
		driver.findElement(By.id(idPrecoTotalProduto2)).getText();
		Assert.assertEquals(produtoSelecionado1, driver.findElement(By.id(idPrecoTotalProduto1)).getText());
		Assert.assertEquals(produtoSelecionado2, driver.findElement(By.id(idPrecoTotalProduto2)).getText());
		//TODO Fazer a checagem dos valores dos produtos
		driver.findElement(By.cssSelector(pathBotaoProceedToCheckoutConfirm)).click();
		                                  
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(idBotaoSignIn)));
		driver.findElement(By.id("email")).sendKeys(emailUsuario);
		driver.findElement(By.id("passwd")).sendKeys(senhaUsuario);
		driver.findElement(By.id(idBotaoSignIn)).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated( By.cssSelector(pathBotaoProceedToCheckoutAddress) ));
		//TODO comparar os endereços
		//TODO pegar o botao pela name: processAddress
		driver.findElement(By.cssSelector(pathBotaoProceedToCheckoutAddress)).click();
        
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(idDivCheckboxAgreeTerms)));
		driver.findElement(By.id(idCheckboxAgreeTerms)).click();
		Assert.assertTrue(driver.findElement(By.id(idCheckboxAgreeTerms)).isSelected());
		driver.findElement(By.cssSelector(pathProceedToCheckoutShipping)).click();
		
		//TODO fazer a asserção do valor final com valor de frete antes de prosseguir
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(pathBankwirePayment)));
		driver.findElement(By.cssSelector(pathBankwirePayment)).click();
		
		//TODO fazer mais uma asserção conferindo o valor final
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(pathBotaoIConfirmMyOrder)));
		driver.findElement(By.cssSelector(pathBotaoIConfirmMyOrder)).click();
		
		//pegando o conteúdo da ordem do pedido:
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(pathTituloOrderIsComplete)));
		String ordemPedido = driver.findElement(By.cssSelector(pathTituloOrderIsComplete)).getText();
		//TODO salvar as informações em um arquivo de texto
		String fileName = System.getProperty("user.dir") + "\\ordensPedido\\ordem" + System.currentTimeMillis() + ".txt";

		FileWriter arq = new FileWriter(fileName);
	    try {
	    	PrintWriter gravarArq = new PrintWriter(arq);
		    try {
		    	gravarArq.printf(ordemPedido);
		    } finally {
		    	gravarArq.close();
		    }
	    } finally {
	    	arq.close();	
	    }	    	
	}
}




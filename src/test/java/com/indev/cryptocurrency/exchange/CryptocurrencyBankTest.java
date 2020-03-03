package com.indev.cryptocurrency.exchange;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
/*
    A cryptocurrency exchange is a business that allows customers to trade cryptocurrencies.

    This simple test demonstrate a cryptocurrency bank that allow customers to sell and buy cryptocurrencies without any fees.
        - The cryptocurrency bank support a list of cryptocurrencies
        - The cryptocurrency bank have a list of customers that are selling The cryptocurrencies
        - The cryptocurrency price calculation is based on the Metcalfes's Law.
        - The cryptocurrency price = n^2-n where n is the number of customer that are buying the cryptocurrency.
 */
public class CryptocurrencyBankTest {

    private CryptocurrencyBank cryptocurrencyBank = new CryptocurrencyBank();


    @Before
    public void setup() {
        cryptocurrencyBank.addSupportedCryptoCurrency("Bitcoin");
        cryptocurrencyBank.addSupportedCryptoCurrency("Ethereum");
    }

    /*
        A customer may have a given quantity of a cryptocurrency in his wallet
     */
    @Test
    public void shouldPrintCustomerWalletWithBitcoin() {
        Customer sellerCustomer = new Customer().withCryptocurrency("Bitcoin", 10);

        assertThat(sellerCustomer.toString(), equalTo("0:$,10:Bitcoin"));
    }


    @Test
    public void shouldPrintCustomerWalletWithEthereum() {
        Customer sellerCustomer = new Customer().withCryptocurrency("Ethereum", 10);

        assertThat(sellerCustomer.toString(), equalTo("0:$,10:Ethereum"));
    }

    /*
        A customer have a balance of $
     */
    @Test
    public void shouldPrintCustomerWalletWithBalance() {
        Customer sellerCustomer = new Customer().withBalance(10000).withCryptocurrency("Bitcoin", 10);

        assertThat(sellerCustomer.toString(), equalTo("10000:$,10:Bitcoin"));
    }

    /*
        A customer can request to buy a given quantity of a cryptocurrency, if no seller are selling it, the transaction will not occur
     */
    @Test
    public void shouldNotBuyWhenNoSeller() {
        Customer buyerCustomer = new Customer().withBalance(100);

        int boughtQuantity = cryptocurrencyBank.requestTransaction(buyerCustomer, 3, "Bitcoin");

        assertThat(boughtQuantity, equalTo(0));
        assertThat(buyerCustomer.toString(), equalTo("100:$"));
    }

    /*
        A customer can request to buy a given quantity of a cryptocurrency, when there are sellers, the base price is 1$
     */
    @Test
    public void shouldBuyCryptocurrency() {
        Customer sellerCustomer = new Customer().withCryptocurrency("Bitcoin", 10);

        cryptocurrencyBank.addSeller(sellerCustomer);

        Customer buyerCustomer = new Customer().withBalance(100);
        int boughtQuantity = cryptocurrencyBank.requestTransaction(buyerCustomer, 3, "Bitcoin");

        assertThat(boughtQuantity, equalTo(3));
        assertThat(sellerCustomer.toString(), equalTo("3:$,7:Bitcoin"));
        assertThat(buyerCustomer.toString(), equalTo("97:$,3:Bitcoin"));
    }

    /*
        A customer can request to buy a given quantity of a cryptocurrency, when the sellers are not selling the wanted cryptocurrency, the transaction will not occur
     */
    @Test
    public void shouldNotBuyCryptocurrencyWhenNotFound() {
        Customer sellerCustomer = new Customer().withCryptocurrency("Bitcoin", 10);

        cryptocurrencyBank.addSeller(sellerCustomer);

        Customer buyerCustomer = new Customer().withBalance(100);
        int boughtQuantity = cryptocurrencyBank.requestTransaction(buyerCustomer, 3, "Ethereum");

        assertThat(boughtQuantity, equalTo(0));
        assertThat(sellerCustomer.toString(), equalTo("0:$,10:Bitcoin"));
        assertThat(buyerCustomer.toString(), equalTo("100:$"));
    }

    /*
        The cryptocurrency price is defined according to Metcalfe's Law.
        the price = n^2-n, where n is the number of customers willing to buy the given cryptocurrency.
        When there is only one buyer, for this particular case we will use the price = 1$
        For example when there are 2 buyers, price = 2^2-1 = 2$
        For example when there are 3 buyers, price = 3^2-1 = 6$
     */
    @Test
    public void shouldBuyCryptocurrencyMetcalfeLaw() {
        Customer sellerCustomer = new Customer().withCryptocurrency("Bitcoin", 10);

        cryptocurrencyBank.addSeller(sellerCustomer);

        Customer firstBuyer = new Customer().withBalance(100);
        Customer secondBuyer = new Customer().withBalance(100);
        Customer thirdBuyer = new Customer().withBalance(100);
        int firstBoughtQuantity = cryptocurrencyBank.requestTransaction(firstBuyer, 3, "Bitcoin");
        int secondBoughtQuantity = cryptocurrencyBank.requestTransaction(secondBuyer, 3, "Bitcoin");
        int thirdBoughtQuantity = cryptocurrencyBank.requestTransaction(thirdBuyer, 3, "Bitcoin");

        assertThat(firstBoughtQuantity, equalTo(3));
        assertThat(secondBoughtQuantity, equalTo(3));
        assertThat(thirdBoughtQuantity, equalTo(3));

        assertThat(sellerCustomer.toString(), equalTo("27:$,1:Bitcoin"));

        assertThat(firstBuyer.toString(), equalTo("97:$,3:Bitcoin"));
        assertThat(secondBuyer.toString(), equalTo("94:$,3:Bitcoin"));
        assertThat(thirdBuyer.toString(), equalTo("82:$,3:Bitcoin"));
    }
}

package com.indev.cryptocurrency.exchange;

import java.util.ArrayList;
import java.util.List;

public class CryptocurrencyBank {

    private List<String> cryptocurrencies = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();

    public void addSupportedCryptoCurrency(String bitcoin) {
        cryptocurrencies.add(bitcoin);

    }

    public int requestTransaction(Customer buyerCustomer, int i, String bitcoin) {
        Customer customer = customers.stream().filter(c -> c.getCryptocurrency().equals(bitcoin) && c.getQuantity() >= i).findAny().orElse(null);
        if (null != customer) {
            customer.setBalance(i);
            customer.setQuantity(customer.getQuantity() - i);
            buyerCustomer.setQuantity(i);
            buyerCustomer.setBalance(buyerCustomer.getBalance()-i);
            buyerCustomer.setCryptocurrency(bitcoin);
            return i;
        }

        return 0;
    }

    public void addSeller(Customer sellerCustomer) {
        customers.add(sellerCustomer);
    }

}
/**
 * Copyright (c) 2016 Alex Laird
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.finicityclient.operation;

import com.finicityclient.component.Token;
import com.finicityclient.component.rest.Parameter;
import com.finicityclient.component.rest.Response;
import com.finicityclient.type.Customer;
import com.finicityclient.type.CustomerType;
import com.finicityclient.type.Customers;
import com.finicityclient.component.StringUtils;
import com.finicityclient.component.rest.RestClient;
import com.finicityclient.type.Customer;
import com.finicityclient.type.CustomerType;
import com.finicityclient.type.Customers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultCustomerOperations extends DefaultOperations implements CustomerOperations {

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(DefaultCustomerOperations.class));

    public DefaultCustomerOperations(RestClient restClient, String appKey, Token token) {
        super(restClient, appKey, token);
    }

    @Override
    public List<Customer> getCustomers(String search, String username, Integer start, Integer limit, CustomerType type) {
        List<Parameter> parameters = new ArrayList<>();
        if (StringUtils.isNotBlank(search)) {
            parameters.add(new Parameter("search", search));
        }
        if (StringUtils.isNotBlank(username)) {
            parameters.add(new Parameter("username", username));
        }
        if (start != null) {
            parameters.add(new Parameter("start", start.toString()));
        }
        if (limit != null) {
            parameters.add(new Parameter("limit", limit.toString()));
        }
        if (type != null) {
            parameters.add(new Parameter("type", type.toString()));
        }

        Response response = restClient.executeGet("/v1/customers",
                parameters,
                null);

        if (response.getStatusCode() != 200) {
            throw new CustomerOperations.CustomerOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }

        try {
            return serializer.read(Customers.class, response.getBody()).getCustomers();
        } catch (Exception ex) {
            LOGGER.log(Level.ALL, "An error occurred when parsing the customers response.", ex);

            throw new CustomerOperations.CustomerOperationsException("An error occurred when parsing the customers response.", ex);
        }
    }

    @Override
    public Customer getCustomer(String customerId) {
        assert customerId != null;

        Response response = restClient.executeGet("/v1/customers/" + customerId,
                null,
                null);

        if (response.getStatusCode() != 200) {
            throw new CustomerOperations.CustomerOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }

        try {
            return serializer.read(Customer.class, response.getBody());
        } catch (Exception ex) {
            LOGGER.log(Level.ALL, "An error occurred when parsing the customer response.", ex);

            throw new CustomerOperations.CustomerOperationsException("An error occurred when parsing the customer response.", ex);
        }
    }

    @Override
    public void addTestingCustomer(Customer customer) {
        assert customer != null;

        Response response = restClient.executePost("/v1/customers/testing",
                customer,
                null,
                null);

        if (response.getStatusCode() != 201) {
            throw new CustomerOperations.CustomerOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }
    }

    @Override
    public void addCustomer(Customer customer) {
        assert customer != null;

        Response response = restClient.executePost("/v1/customers/active",
                customer,
                null,
                null);

        if (response.getStatusCode() != 201) {
            throw new CustomerOperations.CustomerOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }
    }

    @Override
    public void modifyCustomer(Customer customer) {
        assert customer != null;

        Response response = restClient.executePut("/v1/customers/" + customer.getId(),
                customer,
                null,
                null);

        if (response.getStatusCode() != 204) {
            throw new CustomerOperations.CustomerOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }
    }

    @Override
    public void deleteCustomer(String customerId) {
        assert customerId != null;

        Response response = restClient.executeDelete("/v1/customers/" + customerId,
                null,
                null);

        if (response.getStatusCode() != 204) {
            throw new CustomerOperations.CustomerOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }
    }
}

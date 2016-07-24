/*
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

import com.finicityclient.exception.FinicityException;
import com.finicityclient.type.Customer;
import com.finicityclient.type.CustomerType;

import java.util.List;

/**
 * Interface for Customer operations, the API for which can be found <a href="https://developer.finicity.com/admin/docs/#/customers">here</a>.
 */
public interface CustomerOperations {
    /**
     * Find all customers enrolled by the current partner, where the search text is found in the customer's username or
     * any combination of firstName and lastName fields. If no search text is provided, return all customers. See <a
     * href="https://finicity.zendesk.com/hc/en-us/articles/202460545">Handling Spaces in Queries</a> to search for
     * multiple words. See <a href="https://finicity.zendesk.com/hc/en-us/articles/201703569-Handling-Dates-and-Times">Handling
     * Dates and Times</a> to understand how timestamps are used in this API.
     * <p>
     * Success: HTTP 200 (OK)
     *
     * @param search   Query: Text to match (leave empty to return all customers)
     * @param username Query: Username for exact match
     * @param start    Query: Starting index for this page of results (default is 1)
     * @param limit    Query: Maximum number of entries for this page of results (default is 25)
     * @param type     Query: testing or active (default is active)
     * @return All the customers matching the given query.
     *
     * @throws FinicityException An error occurred when interaction with the API
     */
    List<Customer> getCustomers(String search, String username, Integer start, Integer limit, CustomerType type);

    /**
     * Get details for an enrolled customer. See <a href="https://finicity.zendesk.com/hc/en-us/articles/201703569-Handling-Dates-and-Times">Handling
     * Dates and Times</a> to understand how timestamps are used in this API.
     * <p>
     * Success: HTTP 200 (OK)
     *
     * @param customerId (required) ID of the customer
     * @return The customer matching the given ID.
     *
     * @throws FinicityException An error occurred when interaction with the API
     */
    Customer getCustomer(String customerId);

    /**
     * Enroll a testing customer. A testing customer may only register accounts with FinBank (institution ID 101732) or
     * FinBank - Subaccounts (institution ID 101806). See Testing Accounts for more details. See <a
     * href="https://finicity.zendesk.com/hc/en-us/articles/201703569-Handling-Dates-and-Times">Handling Dates and
     * Times</a> to understand how timestamps are used in this API.
     * <p>
     * Success: HTTP 201 (Created)
     *
     * @param customer (required) The customer to be created.
     * @throws FinicityException An error occurred when interaction with the API
     */
    void addTestingCustomer(Customer customer);

    /**
     * This service is not available from the Test Drive.
     * <p>
     * Enroll an active customer (the actual owner of one or more real-world accounts). The customer's account
     * transactions will be refreshed every night. See <a href="https://finicity.zendesk.com/hc/en-us/articles/201703569-Handling-Dates-and-Times">Handling
     * Dates and Times</a> to understand how timestamps are used in this API.
     * <p>
     * Success: HTTP 201 (Created)
     *
     * @param customer (required) The customer to be created.
     * @throws FinicityException An error occurred when interaction with the API
     */
    void addCustomer(Customer customer);

    /**
     * Modify the details for an enrolled customer.
     * <p>
     * Success: HTTP 204 (No Content)
     *
     * @param customer (required) The customer data to be modified.
     * @throws FinicityException An error occurred when interaction with the API
     */
    void modifyCustomer(Customer customer);

    /**
     * Completely remove a customer from the system. This will remove the customer and all associated accounts,
     * transactions, and aggregation support tickets.
     * <p>
     * USE THIS SERVICE CAREFULLY! It will perform the operation immediately, without pausing for confirmation!
     * <p>
     * Success: HTTP 204 (No Content)
     *
     * @param customerId (required) The ID matching the customer to be deleted.
     * @throws FinicityException An error occurred when interaction with the API
     */
    void deleteCustomer(String customerId);

    /**
     * An error has occurred when processing an operation in {@link CustomerOperations}.
     */
    class CustomerOperationsException extends FinicityException {
        public CustomerOperationsException(String msg, Exception cause) {
            super(msg, cause);
        }

        public CustomerOperationsException(String msg) {
            super(msg);
        }
    }
}

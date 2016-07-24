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

import com.finicityclient.component.Token;
import com.finicityclient.component.rest.Parameter;
import com.finicityclient.component.rest.Response;
import com.finicityclient.component.rest.RestClient;
import com.finicityclient.type.Sort;
import com.finicityclient.type.Transaction;
import com.finicityclient.type.Transactions;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Default implementation of {@link TransactionOperations}.
 */
public class DefaultTransactionOperations extends DefaultOperations implements TransactionOperations {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(DefaultTransactionOperations.class));

    /**
     * Construct a client for Transaction operations.
     *
     * @param restClient A REST client for operations.
     * @param appKey     Finicity appKey.
     * @param token      Finicity authentication token.
     */
    public DefaultTransactionOperations(RestClient restClient, String appKey, Token token) {
        super(restClient, appKey, token);
    }

    @Override
    public List<Transaction> getTransactions(String customerId, Long fromDate, Long toDate, Integer start, Integer limit, Sort sort, Boolean includePending) {
        assert customerId != null;
        assert fromDate != null;
        assert toDate != null;

        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter("fromDate", fromDate.toString()));
        parameters.add(new Parameter("toDate", toDate.toString()));
        if (start != null) {
            parameters.add(new Parameter("start", start.toString()));
        }
        if (limit != null) {
            parameters.add(new Parameter("limit", limit.toString()));
        }
        if (sort != null) {
            parameters.add(new Parameter("sort", sort.toString()));
        }
        if (includePending != null) {
            parameters.add(new Parameter("includePending", includePending.toString()));
        }

        Response response = restClient.executeGet("/v2/customers/" + customerId + "/transactions",
                parameters,
                null);

        if (response.getStatusCode() != 200) {
            throw new TransactionOperations.TransactionOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }

        try {
            return serializer.read(Transactions.class, response.getBody()).getTransactions();
        } catch (Exception ex) {
            LOGGER.log(Level.ALL, "An error occurred when parsing the transactions response.", ex);

            throw new TransactionOperations.TransactionOperationsException("An error occurred when parsing the transactions response.", ex);
        }
    }

    @Override
    public List<Transaction> getAccountTransactions(String customerId, String accountId, Long fromDate, Long toDate, Integer start, Integer limit, Sort sort, Boolean includePending) {
        assert customerId != null;
        assert accountId != null;
        assert fromDate != null;
        assert toDate != null;

        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter("fromDate", fromDate.toString()));
        parameters.add(new Parameter("toDate", toDate.toString()));
        if (start != null) {
            parameters.add(new Parameter("start", start.toString()));
        }
        if (limit != null) {
            parameters.add(new Parameter("limit", limit.toString()));
        }
        if (sort != null) {
            parameters.add(new Parameter("sort", sort.toString()));
        }
        if (includePending != null) {
            parameters.add(new Parameter("includePending", includePending.toString()));
        }

        Response response = restClient.executeGet("/v2/customers/" + customerId + "/accounts/" + accountId + "/transactions",
                parameters,
                null);

        if (response.getStatusCode() != 200) {
            throw new TransactionOperations.TransactionOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }

        try {
            return serializer.read(Transactions.class, response.getBody()).getTransactions();
        } catch (Exception ex) {
            LOGGER.log(Level.ALL, "An error occurred when parsing the transactions response.", ex);

            throw new TransactionOperations.TransactionOperationsException("An error occurred when parsing the transactions response.", ex);
        }
    }

    @Override
    public Transaction getTransaction(String customerId, String transactionId) {
        assert customerId != null;
        assert transactionId != null;

        Response response = restClient.executeGet("/v2/customers/" + customerId + "/transactions/" + transactionId,
                null,
                null);

        if (response.getStatusCode() != 200) {
            throw new TransactionOperations.TransactionOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }

        try {
            return serializer.read(Transaction.class, response.getBody());
        } catch (Exception ex) {
            LOGGER.log(Level.ALL, "An error occurred when parsing the transaction response.", ex);

            throw new TransactionOperations.TransactionOperationsException("An error occurred when parsing the transaction response.", ex);
        }
    }
}

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

package com.github.alexdlaird.operation;

import com.github.alexdlaird.component.Token;
import com.github.alexdlaird.component.rest.Response;
import com.github.alexdlaird.component.rest.RestClient;
import com.github.alexdlaird.type.transaction.Transaction;
import com.github.alexdlaird.type.tx_push.Subscription;
import com.github.alexdlaird.type.tx_push.Subscriptions;

import java.util.List;

/**
 * Default implementation of {@link TxPushOperations}.
 */
public class DefaultTxPushOperations extends DefaultOperations implements TxPushOperations {
    /**
     * Construct a client for TxPush operations.
     *
     * @param restClient A REST client for operations.
     * @param appKey     Finicity appKey.
     * @param token      Finicity authentication token.
     */
    public DefaultTxPushOperations(RestClient restClient, String appKey, Token token) {
        super(restClient, appKey, token);
    }

    @Override
    public List<Subscription> enableTxPushNotifications(String customerId, String accountId, Subscription subscription) {
        assert customerId != null;
        assert accountId != null;

        Response response = restClient.executePost("/v1/customers/" + customerId + "/accounts/" + accountId + "/txpush",
                subscription,
                null,
                null);

        if (response.getStatusCode() != 201) {
            throw new TxPushOperations.TxPushOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }

        try {
            return serializer.read(Subscriptions.class, response.getBody()).getSubscriptions();
        } catch (Exception ex) {
            throw new TransactionOperations.TransactionOperationsException("An error occurred when parsing the subscriptions response.", ex);
        }
    }

    @Override
    public void disableTxPushNotifications(String customerId, String accountId) {
        assert customerId != null;
        assert accountId != null;

        Response response = restClient.executeDelete("/v1/customers/" + customerId + "/accounts/" + accountId + "/txpush",
                null,
                null);

        if (response.getStatusCode() != 204) {
            throw new TxPushOperations.TxPushOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }
    }

    @Override
    public void deleteTxPushSubscription(String customerId, String subscriptionId) {
        assert customerId != null;
        assert subscriptionId != null;

        Response response = restClient.executeDelete("/v1/customers/" + customerId + "/subscriptions/" + subscriptionId + "",
                null,
                null);

        if (response.getStatusCode() != 204) {
            throw new TxPushOperations.TxPushOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }
    }

    @Override
    public Transaction addTransactionForTestingAccount(String customerId, String accountId, Transaction transaction) {
        assert customerId != null;
        assert accountId != null;

        Response response = restClient.executePost("/v1/customers/" + customerId + "/accounts/" + accountId + "/transactions",
                transaction,
                null,
                null);

        if (response.getStatusCode() != 201) {
            throw new TxPushOperations.TxPushOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }

        try {
            return serializer.read(Transaction.class, response.getBody());
        } catch (Exception ex) {
            throw new TransactionOperations.TransactionOperationsException("An error occurred when parsing the transaction response.", ex);
        }
    }
}

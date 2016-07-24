package com.finicityclient.operation;

import com.finicityclient.component.Token;
import com.finicityclient.component.rest.Response;
import com.finicityclient.component.rest.RestClient;
import com.finicityclient.type.transaction.Transaction;
import com.finicityclient.type.tx_push.Subscription;
import com.finicityclient.type.tx_push.Subscriptions;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Default implementation of {@link TxPushOperations}.
 */
public class DefaultTxPushOperations extends DefaultOperations implements TxPushOperations {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(DefaultTxPushOperations.class));

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
            LOGGER.log(Level.ALL, "An error occurred when parsing the subscriptions response.", ex);

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
            LOGGER.log(Level.ALL, "An error occurred when parsing the transaction response.", ex);

            throw new TransactionOperations.TransactionOperationsException("An error occurred when parsing the transaction response.", ex);
        }
    }
}

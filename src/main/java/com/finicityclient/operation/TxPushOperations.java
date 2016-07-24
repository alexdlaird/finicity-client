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
import com.finicityclient.type.transaction.Transaction;
import com.finicityclient.type.tx_push.Subscription;

import java.util.List;

/**
 * Interface for TxPush operations, the API for which can be found <a href="https://developer.finicity.com/admin/docs/#/txpush_services">here</a>.
 */
public interface TxPushOperations {
    /**
     * Register a webhook URL to receive TxPUSH notifications related to the given account. Notifications are sent
     * whenever the account balance changes or new transactions are posted for the account. The webhook URL must be
     * secure (https) for any real-world account.
     * <p>
     * Success: HTTP 201 (Created)
     *
     * @param customerId   ID of the customer.
     * @param accountId    ID of the account.
     * @param subscription CALLBACK_URL: The URL of the client app's webhook for TxPUSH notifications.
     * @return Subscription details.
     *
     * @throws FinicityException An error occurred when interaction with the API
     */
    List<Subscription> enableTxPushNotifications(String customerId, String accountId, Subscription subscription);

    /**
     * Delete all subscriptions associated with the indicated account. No more notifications will be sent for these
     * events.
     * <p>
     * Success: HTTP 204 (No Content)
     *
     * @param customerId ID of the customer.
     * @param accountId  ID of the account.
     * @throws FinicityException An error occurred when interaction with the API
     */
    void disableTxPushNotifications(String customerId, String accountId);

    /**
     * Delete a specific subscription for a class of events (account or transaction events) for an account. No more
     * notifications will be sent for these events.
     * <p>
     * Success: HTTP 204 (No Content)
     *
     * @param customerId     ID of the customer.
     * @param subscriptionId ID of the subscription.
     * @throws FinicityException An error occurred when interaction with
     */
    void deleteTxPushSubscription(String customerId, String subscriptionId);

    /**
     * Inject a transaction into the transaction list for a testing account. This allows an app to trigger TxPUSH
     * notifications for the account in order to test the app’s webhook code. This service is only supported for testing
     * accounts (accounts on institution 101732 or 101806).
     * <p>
     * See Handling Dates and Times to understand how timestamps are used in this API.
     * <p>
     * Success: HTTP 201 (Created)
     *
     * @param customerId  ID of the customer.
     * @param accountId   ID of the account.
     * @param transaction AMOUNT: The transaction amount. DESCRIPTION: The transaction payee or memo. POSTED_DATE: The
     *                    transaction posted date (epoch, optional). TRANSACTION_DATE: The transaction date (epoch,
     *                    optional).
     * @return The newly created Transaction.
     *
     * @throws FinicityException An error occurred when interaction with
     */
    Transaction addTransactionForTestingAccount(String customerId, String accountId, Transaction transaction);

    /**
     * An error has occurred when processing an operation in {@link TxPushOperations}.
     */
    class TxPushOperationsException extends FinicityException {
        public TxPushOperationsException(String msg) {
            super(msg);
        }
    }
}

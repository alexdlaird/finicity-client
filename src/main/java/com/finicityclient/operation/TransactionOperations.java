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
import com.finicityclient.type.Sort;
import com.finicityclient.type.transaction.Transaction;

import java.util.List;

/**
 * Interface for Transaction operations, the API for which can be found <a href="https://developer.finicity.com/admin/docs/#/transactions">here</a>.
 */
public interface TransactionOperations {
    /**
     * Get all transactions available for this customer within the given date range, across all accounts. This service
     * supports paging and sorting by transactionDate, with a maximum of 1000 transactions per request. See <a
     * href="https://finicity.zendesk.com/hc/en-us/articles/201703569-Handling-Dates-and-Times">Handling Dates and
     * Times</a> to understand how timestamps are used in this API.
     * <p>
     * Success: HTTP 200 (OK)
     *
     * @param customerId     (required) ID of the customer
     * @param fromDate       (required) Query: Starting point for the date range
     * @param toDate         (required) Query: Ending point for the date range (maximum range is 190 days)
     * @param start          Query: Starting index for this page of results (default is 1)
     * @param limit          Query: Maximum number of entries for this page of results (default and max is 1000)
     * @param sort           Query: Sort order: desc or asc. Default is desc for descending order (newest transactions
     *                       are on page 1); also allows asc for ascending order (oldest transactions are on page 1).
     * @param includePending Query: 'true' to include pending transactions
     * @return A list of transactions matching the query.
     *
     * @throws FinicityException An error occurred when interaction with the API
     */
    List<Transaction> getTransactions(String customerId, Long fromDate, Long toDate, Integer start, Integer limit, Sort sort, Boolean includePending);

    /**
     * Get all transactions available for this customer account within the given date range. This service supports
     * paging and sorting by transactionDate, with a maximum of 1000 transactions per request. See <a
     * href="https://finicity.zendesk.com/hc/en-us/articles/201703569-Handling-Dates-and-Times">Handling Dates and
     * Times</a> to understand how timestamps are used in this API.
     * <p>
     * Success: HTTP 200 (OK)
     *
     * @param customerId     (required) ID of the customer
     * @param accountId      (required) ID of the account
     * @param fromDate       (required) Query: Starting point for the date range
     * @param toDate         (required) Query: Ending point for the date range (maximum range is 190 days)
     * @param start          Query: Starting index for this page of results (default is 1)
     * @param limit          Query: Maximum number of entries for this page of results (default and max is 1000)
     * @param sort           Query: Sort order: desc or asc. Default is desc for descending order (newest transactions
     *                       are on page 1); also allows asc for ascending order (oldest transactions are on page 1).
     * @param includePending Query: 'true' to include pending transactions
     * @return A list of transactions matching the query.
     *
     * @throws FinicityException An error occurred when interaction with the API
     */
    List<Transaction> getAccountTransactions(String customerId, String accountId, Long fromDate, Long toDate, Integer start, Integer limit, Sort sort, Boolean includePending);

    /**
     * Get details for the specified transaction. See <a href="https://finicity.zendesk.com/hc/en-us/articles/201703569-Handling-Dates-and-Times">Handling
     * Dates and Times</a> to understand how timestamps are used in this API.
     * <p>
     * Success: HTTP 200 (OK)
     *
     * @param customerId    (required) ID of the customer
     * @param transactionId (required) ID of the transaction
     * @return The transaction matching the ID given.
     *
     * @throws FinicityException An error occurred when interaction with the API
     */
    Transaction getTransaction(String customerId, String transactionId);

    /**
     * An error has occurred when processing an operation in {@link TransactionOperations}.
     */
    class TransactionOperationsException extends FinicityException {
        public TransactionOperationsException(String msg, Exception cause) {
            super(msg, cause);
        }

        public TransactionOperationsException(String msg) {
            super(msg);
        }
    }
}

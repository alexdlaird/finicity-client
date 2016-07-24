/**
 * Copyright (c) 2016 Alex Laird
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.finicityclient.type.operation;

import com.finicityclient.type.exception.FinicityException;
import com.finicityclient.type.type.*;

import java.util.List;

public interface AccountOperations {
    /**
     * Discover and activate all accounts available using the specified credentials at the given institution, without performing aggregation.
     *
     * See <a href="https://finicity.zendesk.com/hc/en-us/articles/201718789">Add an Account</a> to understand this process.
     * See <a href="https://finicity.zendesk.com/hc/en-us/articles/201750779">Account Types</a> for a list of supported account types.
     *
     * HTTP status of 200 means all accounts have been added.
     * HTTP status of 203 means the response contains an MFA challenge. Go to Add All Accounts (with MFA Answers).
     *
     * @param customerId    (required) ID of the customer
     * @param institutionId (required) ID of the institution
     * @param accounts      (required) Elements from institution login form, with each field's actual FIELD_VALUE for the account's credentials.
     * @return Either a list of {@link Account}s or a list of {@link MfaChallengeResponse}es.
     * @throws FinicityException An error occurred when interaction with the API
     */
    List<? extends AccountResponse> addAllAccounts(String customerId, String institutionId, AccountLoginForm accounts);

    /**
     * Send MFA answers for an earlier challenge during Add All Accounts.
     *
     * See <a href="https://finicity.zendesk.com/hc/en-us/articles/201718789">Add an Account</a> to understand this process.
     * See <a href="https://finicity.zendesk.com/hc/en-us/articles/201750779">Account Types</a> for a list of supported account types.
     *
     * HTTP status of 200 means all accounts have been added.
     * HTTP status of 203 means the response contains another MFA challenge. Repeat Add All Accounts (with MFA Answers).
     *
     * @param mfaSession    (required) HTTP Header: MFA-Session header returned in previous MFA challenge
     * @param customerId    (required) ID of the customer
     * @param institutionId (required) ID of the institution
     * @param accounts      (required) Challenges from the challenge, inserting the MFA_ANSWER value.
     * @return Either a list of {@link Account}s or a list of {@link MfaChallengeResponse}es.
     * @throws FinicityException An error occurred when interaction with the API
     */
    List<? extends AccountResponse> addAllAccountsMfa(String mfaSession, String customerId, String institutionId, AccountMfaChallenge accounts);

    /**
     * Discover all accounts owned by the specified customer at the given institution. Use Discover Customer Accounts and Activate Customer Accounts if you need to select specific accounts for activation. Otherwise, use Add All Acounts instead.
     *
     * See <a href="https://finicity.zendesk.com/hc/en-us/articles/201718789">Add an Account</a> to understand this process.
     * See <a href="https://finicity.zendesk.com/hc/en-us/articles/201750779">Account Types</a> for a list of supported account types.
     *
     * HTTP status of 200 means discovery was successful. Go to Activate Customer Accounts.
     * HTTP status of 203 means the response contains an MFA challenge. Go to Discover Customer Accounts (with MFA Answers).
     *
     * @param customerId    (required) ID of the customer
     * @param institutionId (required) ID of the institution
     * @param accounts      (required) Elements from institution login form, with each field's actual FIELD_VALUE for the account's credentials.
     * @return Either a list of {@link Account}s or a list of {@link MfaChallengeResponse}es.
     * @throws FinicityException An error occurred when interaction with the API
     */
    List<? extends AccountResponse> discoverAccounts(String customerId, String institutionId, AccountLoginForm accounts);

    /**
     * Send MFA answers for an earlier challenge during account discovery.
     *
     * See <a href="https://finicity.zendesk.com/hc/en-us/articles/201718789">Add an Account</a> to understand this process.
     * See <a href="https://finicity.zendesk.com/hc/en-us/articles/201750779">Account Types</a> for a list of supported account types.
     *
     * HTTP status of 200 means discovery was successful. Go to Activate Customer Accounts.
     * HTTP status of 203 means the response contains another MFA challenge. Repeat Discover Customer Accounts (with MFA Answers).
     *
     * @param mfaSession    (required) HTTP Header: MFA-Session header returned in previous MFA challenge
     * @param customerId    (required) ID of the customer
     * @param institutionId (required) ID of the institution
     * @param accounts      (required) Challenges from the challenge, inserting the MFA_ANSWER value.
     * @return Either a list of {@link Account}s or a list of {@link MfaChallengeResponse}es.
     * @throws FinicityException An error occurred when interaction with the API
     */
    List<? extends AccountResponse> discoverAccountsMfa(String mfaSession, String customerId, String institutionId, AccountMfaChallenge accounts);

    /**
     * Enable Finicity aggregation for the specified accounts, but do not actually perform aggregation. This service returns quickly, does not perform aggregation, and never returns an MFA challenge.
     *
     * Account transactions will be refreshed every night. An account can be refreshed at any time using the various endpoints for Refresh Customer Accounts.
     *
     * See <a href="https://finicity.zendesk.com/hc/en-us/articles/201718909-Step-6-Refresh-an-Account">Refresh an Account</a> to understand this process.
     * See <a href="https://finicity.zendesk.com/hc/en-us/articles/201750779-Account-Types">Account Types</a> for a list of supported account types.
     * See <a href="https://finicity.zendesk.com/hc/en-us/articles/201750879">Handling Aggregation Status Codes</a> to understand the &lt;aggregationStatusCode&gt; for a specific account.
     * See <a href="https://finicity.zendesk.com/hc/en-us/articles/201703569-Handling-Dates-and-Times">Handling Dates and Times</a> to understand how timestamps are used in this API.
     *
     * HTTP status of 200 means the activation operation is complete, and the account details are in the body of the response. Go to Refresh Customer Account.
     * This service will never return an MFA challenge (HTTP 203).
     *
     * @param customerId    (required) ID of the customer
     * @param institutionId (required) ID of the institution
     * @param accounts      (required) Elements from Discovery response for the accounts to be activated. ACCOUNT_TYPE cannot be 'unknown'.
     * @return A list of {@link Account}s.
     * @throws FinicityException An error occurred when interaction with the API
     */
    List<Account> activateAccounts(String customerId, String institutionId, Accounts accounts);

    /**
     * Connect to the account's financial institution and refresh all transaction data for the specified account (and all other accounts activated on the same institution with the same credentials). This is an interactive refresh, so MFA challenges may be required.
     *
     * See <a href="https://finicity.zendesk.com/hc/en-us/articles/201718909-Step-6-Refresh-an-Account">Refresh an Account</a> to understand this process.
     * See <a href="https://finicity.zendesk.com/hc/en-us/articles/201750779-Account-Types">Account Types</a> for a list of supported account types.
     * See <a href="https://finicity.zendesk.com/hc/en-us/articles/201750879">Handling Aggregation Status Codes</a> to understand the &lt;aggregationStatusCode&gt; for a specific account.
     * See <a href="https://finicity.zendesk.com/hc/en-us/articles/201703569-Handling-Dates-and-Times">Handling Dates and Times</a> to understand how timestamps are used in this API.
     *
     * HTTP status of 200 means the refresh operation is complete, and the account details are in the body of the response.
     * HTTP status of 203 means the response contains an MFA challenge. Go to Refresh Customer Accounts (with MFA Answers).
     *
     * @param customerId (required) ID of the customer
     * @param accountId  (required) ID of the account
     * @return Either a {@link Account} or a {@link MfaChallengeResponse}.
     * @throws FinicityException An error occurred when interaction with the API
     */
    List<? extends AccountResponse> refreshAccount(String customerId, String accountId);

    /**
     * Send MFA answers for an earlier challenge during account refresh.
     *
     * See <a href="https://finicity.zendesk.com/hc/en-us/articles/201718909-Step-6-Refresh-an-Account">Refresh an Account</a> to understand this process.
     * See <a href="https://finicity.zendesk.com/hc/en-us/articles/201750779-Account-Types">Account Types</a> for a list of supported account types.
     * See <a href="https://finicity.zendesk.com/hc/en-us/articles/201750879">Handling Aggregation Status Codes</a> to understand the &lt;aggregationStatusCode&gt; for a specific account.
     * See <a href="https://finicity.zendesk.com/hc/en-us/articles/201703569-Handling-Dates-and-Times">Handling Dates and Times</a> to understand how timestamps are used in this API.
     *
     * HTTP status of 200 means the refresh operation is complete, and the account details are in the body of the response.
     * HTTP status of 203 means the response contains another MFA challenge. Repeat Refresh Customer Accounts (with MFA Answers).
     *
     * @param mfaSession    (required) HTTP Header: MFA-Session header returned in previous MFA challenge
     * @param customerId    (required) ID of the customer
     * @param accountId     (required) ID of the account
     * @param mfaChallenges (required) Challenges from the challenge, inserting the MFA_ANSWER value.
     * @return Either a {@link Account} or a {@link MfaChallengeResponse}.
     * @throws FinicityException An error occurred when interaction with the API
     */
    List<? extends AccountResponse> refreshAccountMfa(String mfaSession, String customerId, String accountId, MfaChallengeRequest mfaChallenges);

    /**
     * Connect to all of the customer's financial institutions and refresh the transaction data for all of the customer's accounts. This is a non-interactive refresh, so any MFA challenge will cause that account to fail with an &lt;aggregationStatusCode&gt; value of 185 or 187.
     *
     * See <a href="https://finicity.zendesk.com/hc/en-us/articles/201750779-Account-Types">Account Types</a> for a list of supported account types.
     * See <a href="https://finicity.zendesk.com/hc/en-us/articles/201750879">Handling Aggregation Status Codes</a> to understand the &lt;aggregationStatusCode&gt; for a specific account.
     * See <a href="https://finicity.zendesk.com/hc/en-us/articles/201703569-Handling-Dates-and-Times">Handling Dates and Times</a> to understand how timestamps are used in this API.
     *
     * HTTP status of 200 means the overall refresh operation is complete, and the account details are in the body of the response.
     *
     * @param customerId (required) ID of the customer
     * @return A list of {@link Account}s.
     * @throws FinicityException An error occurred when interaction with the API
     */
    List<Account> refreshAccounts(String customerId);

    /**
     * Get details for all Finicity accounts owned by the specified customer.
     * See <a href="https://finicity.zendesk.com/hc/en-us/articles/201703569-Handling-Dates-and-Times">Handling Dates and Times</a> to understand how timestamps are used in this API.
     *
     * Success: HTTP 200 (OK)
     *
     * @param customerId (required) ID of the customer
     * @return A list of Accounts for the given {@link Customer}
     * @throws FinicityException An error occurred when interaction with the API
     */
    List<Account> getAccounts(String customerId);

    /**
     * Get details for all Finicity accounts owned by the specified customer at the specified institution.
     * See <a href="https://finicity.zendesk.com/hc/en-us/articles/201703569-Handling-Dates-and-Times">Handling Dates and Times</a> to understand how timestamps are used in this API.
     *
     * Success: HTTP 200 (OK)
     *
     * @param customerId    (required) ID of the customer
     * @param institutionId (required) ID of the institution
     * @return A list of Accounts for the given {@link Customer} and {@link Institution}
     * @throws FinicityException An error occurred when interaction with the API
     */
    List<Account> getAccounts(String customerId, String institutionId);

    /**
     * Get details for the specified account.
     * See <a href="https://finicity.zendesk.com/hc/en-us/articles/201703569-Handling-Dates-and-Times">Handling Dates and Times</a> to understand how timestamps are used in this API.
     *
     * Success: HTTP 200 (OK)
     *
     * @param customerId (required) ID of the customer
     * @param accountId  (required) ID of the account
     * @return An Account matching the given criteria
     * @throws FinicityException An error occurred when interaction with the API
     */
    Account getAccount(String customerId, String accountId);

    /**
     * Modify some details for the specified account.
     *
     * Success: HTTP 204 (No Content)
     *
     * @param customerId (required) ID of the customer
     * @param accountId  (required) ID of the account
     * @param account    (required) The account data to be modified
     * @throws FinicityException An error occurred when interaction with the API
     */
    void modifyAccount(String customerId, String accountId, Account account);

    /**
     * Remove the specified account from Finicity Aggregation.
     *
     * Success: HTTP 204 (No Content)
     *
     * @param customerId (required) ID of the customer
     * @param accountId  (required) ID of the account
     * @throws FinicityException An error occurred when interaction with the API
     */
    void deleteAccount(String customerId, String accountId);

    /**
     * Get the login fields for the specified account.
     *
     * Success: HTTP 200 (OK)
     *
     * @param customerId (required) ID of the customer
     * @param accountId  (required) ID of the account
     * @return A LoginForm for the {@link Account}
     * @throws FinicityException An error occurred when interaction with the API
     */
    LoginForm getAccountLoginForm(String customerId, String accountId);

    /**
     * Change the values stored for the specified account's login fields.
     *
     * Success: HTTP 204 No Content
     *
     * @param customerId       (required) ID of the customer
     * @param accountId        (required) ID of the account
     * @param accountLoginForm (required) FIELD_ID: Field ID from Get Institution Login Form. VALUE: New value to store for the field.
     * @throws FinicityException An error occurred when interaction with the API
     */
    void modifyAccountCredentials(String customerId, String accountId, LoginForm accountLoginForm);

    class AccountOperationsException extends RuntimeException {
        public AccountOperationsException(String msg, Exception cause) {
            super(msg, cause);
        }

        public AccountOperationsException(String msg) {
        }
    }
}
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
import com.github.alexdlaird.type.account.Account;
import com.github.alexdlaird.type.account.AccountLoginForm;
import com.github.alexdlaird.type.account.AccountMfaChallenge;
import com.github.alexdlaird.type.account.AccountResponse;
import com.github.alexdlaird.type.account.Accounts;
import com.github.alexdlaird.type.account.MfaChallengeRequest;
import com.github.alexdlaird.type.account.MfaChallengeResponse;
import com.github.alexdlaird.type.account.MfaChallenges;
import com.github.alexdlaird.type.institution.LoginForm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Default implementation of {@link AccountOperations}.
 */
public class DefaultAccountOperations extends DefaultOperations implements AccountOperations {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(DefaultAccountOperations.class));

    /**
     * Construct a client for Account operations.
     *
     * @param restClient A REST client for operations.
     * @param appKey     Finicity appKey.
     * @param token      Finicity authentication token.
     */
    public DefaultAccountOperations(final RestClient restClient, final String appKey, final Token token) {
        super(restClient, appKey, token);
    }

    private List<? extends AccountResponse> processAccountResponses(final Response response) {
        try {
            if (response.getStatusCode() == 200) {
                LOGGER.log(Level.FINE, "Parsing Account response");

                return serializer.read(Accounts.class, response.getBody()).getAccounts();
            } else if (response.getStatusCode() == 203) {
                LOGGER.log(Level.FINE, "Parsing MFA Challenge response");

                return serializeMfaChallenges(response);
            } else {
                throw new AccountOperations.AccountOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
            }
        } catch (Exception ex) {
            throw new AccountOperations.AccountOperationsException("An error occurred when parsing the accounts response.", ex);
        }
    }

    private List<? extends AccountResponse> serializeMfaChallenges(final Response response) throws Exception {
        final List<MfaChallengeResponse> mfaChallengeResponses = serializer.read(MfaChallenges.class, response.getBody()).getMfaChallenges();
        for (MfaChallengeResponse mfaChallengeResponse : mfaChallengeResponses) {
            mfaChallengeResponse.setSession(response.getHeaderFields().get("MFA-Session").get(0));
        }
        return mfaChallengeResponses;
    }

    @Override
    public List<? extends AccountResponse> addAllAccounts(final String customerId, final String institutionId,
                                                          final AccountLoginForm accounts) {
        assert customerId != null;
        assert institutionId != null;
        assert accounts != null;

        final Response response = restClient.executePost("/v1/customers/" + customerId + "/institutions/" + institutionId + "/accounts/addall",
                accounts,
                null,
                null);

        return processAccountResponses(response);
    }

    @Override
    public List<? extends AccountResponse> addAllAccountsMfa(final String mfaSession, final String customerId,
                                                             final String institutionId,
                                                             final AccountMfaChallenge accounts) {
        assert mfaSession != null;
        assert customerId != null;
        assert institutionId != null;
        assert accounts != null;

        final Map<String, String> additionalHeaders = new HashMap<>();
        additionalHeaders.put("MFA-Session", mfaSession);

        final Response response = restClient.executePost("/v1/customers/" + customerId + "/institutions/" + institutionId + "/accounts/addall/mfa",
                accounts,
                null,
                additionalHeaders);

        return processAccountResponses(response);
    }

    @Override
    public List<? extends AccountResponse> discoverAccounts(final String customerId, final String institutionId,
                                                            final AccountLoginForm accounts) {
        assert customerId != null;
        assert institutionId != null;
        assert accounts != null;

        final Response response = restClient.executePost("/v1/customers/" + customerId + "/institutions/" + institutionId + "/accounts",
                accounts,
                null,
                null);

        return processAccountResponses(response);
    }

    @Override
    public List<? extends AccountResponse> discoverAccountsMfa(final String mfaSession, final String customerId,
                                                               final String institutionId,
                                                               final AccountMfaChallenge accounts) {
        assert mfaSession != null;
        assert customerId != null;
        assert institutionId != null;
        assert accounts != null;

        final Map<String, String> additionalHeaders = new HashMap<>();
        additionalHeaders.put("MFA-Session", mfaSession);

        final Response response = restClient.executePost("/v1/customers/" + customerId + "/institutions/" + institutionId + "/accounts/mfa",
                accounts,
                null,
                additionalHeaders);

        return processAccountResponses(response);
    }

    @Override
    public List<Account> activateAccounts(final String customerId, final String institutionId,
                                          final Accounts accounts) {
        assert customerId != null;
        assert institutionId != null;
        assert accounts != null;

        final Response response = restClient.executePut("/v2/customers/" + customerId + "/institutions/" + institutionId + "/accounts",
                accounts,
                null,
                null);

        try {
            return serializer.read(Accounts.class, response.getBody()).getAccounts();
        } catch (Exception ex) {
            throw new AccountOperations.AccountOperationsException("An error occurred when parsing the accounts response.", ex);
        }
    }

    @Override
    public List<? extends AccountResponse> refreshAccount(final String customerId, final String accountId) {
        assert customerId != null;
        assert accountId != null;

        final Response response = restClient.executePost("/v1/customers/" + customerId + "/accounts/" + accountId,
                null,
                null,
                null);

        return processAccountResponses(response);
    }

    @Override
    public List<? extends AccountResponse> refreshAccountMfa(final String mfaSession, final String customerId,
                                                             final String accountId,
                                                             final MfaChallengeRequest mfaChallenges) {
        assert mfaSession != null;
        assert customerId != null;
        assert accountId != null;
        assert mfaChallenges != null;

        final Map<String, String> headers = new HashMap<>();
        headers.put("MFA-Session", mfaSession);

        final Response response = restClient.executePost("/v1/customers/" + customerId + "/accounts/" + accountId,
                mfaChallenges,
                null,
                headers);

        return processAccountResponses(response);
    }

    @Override
    public List<Account> refreshAccounts(final String customerId) {
        assert customerId != null;

        final Response response = restClient.executePost("/v1/customers/" + customerId + "/accounts",
                null,
                null,
                null);

        try {
            return serializer.read(Accounts.class, response.getBody()).getAccounts();
        } catch (Exception ex) {
            throw new AccountOperations.AccountOperationsException("An error occurred when parsing the accounts response.", ex);
        }
    }

    @Override
    public List<Account> getAccounts(final String customerId) {
        assert customerId != null;

        final Response response = restClient.executeGet("/v1/customers/" + customerId + "/accounts",
                null,
                null);

        if (response.getStatusCode() != 200) {
            throw new AccountOperations.AccountOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }

        try {
            return serializer.read(Accounts.class, response.getBody()).getAccounts();
        } catch (Exception ex) {
            throw new AccountOperations.AccountOperationsException("An error occurred when parsing the accounts response.", ex);
        }
    }

    @Override
    public List<Account> getAccounts(final String customerId, final String institutionId) {
        assert customerId != null;
        assert institutionId != null;

        final Response response = restClient.executeGet("/v1/customers/" + customerId + "/institutions/" + institutionId + "/accounts",
                null,
                null);

        if (response.getStatusCode() != 200) {
            throw new AccountOperations.AccountOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }

        try {
            return serializer.read(Accounts.class, response.getBody()).getAccounts();
        } catch (Exception ex) {
            throw new AccountOperations.AccountOperationsException("An error occurred when parsing the accounts response.", ex);
        }
    }

    @Override
    public Account getAccount(final String customerId, final String accountId) {
        assert customerId != null;
        assert accountId != null;

        final Response response = restClient.executeGet("/v1/customers/" + customerId + "/accounts/" + accountId,
                null,
                null);

        if (response.getStatusCode() != 200) {
            throw new AccountOperations.AccountOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }

        try {
            return serializer.read(Account.class, response.getBody());
        } catch (Exception ex) {
            throw new AccountOperations.AccountOperationsException("An error occurred when parsing the account response.", ex);
        }
    }

    @Override
    public void modifyAccount(final String customerId, final String accountId, final Account account) {
        assert customerId != null;
        assert accountId != null;
        assert account != null;

        final Response response = restClient.executePut("/v1/customers/{customerId}/accounts/{accountId}",
                account,
                null,
                null);

        if (response.getStatusCode() != 204) {
            throw new AccountOperations.AccountOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }
    }

    @Override
    public void deleteAccount(final String customerId, final String accountId) {
        assert customerId != null;
        assert accountId != null;

        final Response response = restClient.executeDelete("/v1/customers/" + customerId + "/accounts/" + accountId,
                null,
                null);

        if (response.getStatusCode() != 204) {
            throw new AccountOperations.AccountOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }
    }

    @Override
    public LoginForm getAccountLoginForm(final String customerId, final String accountId) {
        assert customerId != null;
        assert accountId != null;

        final Response response = restClient.executeGet("/v1/customers/" + customerId + "/accounts/" + accountId + "/loginForm",
                null,
                null);

        if (response.getStatusCode() != 200) {
            throw new AccountOperations.AccountOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }

        try {
            return serializer.read(LoginForm.class, response.getBody());
        } catch (Exception ex) {
            throw new AccountOperations.AccountOperationsException("An error occurred when parsing the login form response.", ex);
        }
    }

    @Override
    public void modifyAccountCredentials(final String customerId, final String accountId,
                                         final LoginForm accountLoginForm) {
        assert customerId != null;
        assert accountId != null;
        assert accountLoginForm != null;

        final Response response = restClient.executePut("/v1/customers/{customerId}/accounts/{accountId}/loginForm",
                accountLoginForm,
                null,
                null);

        if (response.getStatusCode() != 204) {
            throw new AccountOperations.AccountOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }
    }
}

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

package com.finicityclient.type.operation;

import com.finicityclient.type.component.Token;
import com.finicityclient.type.component.rest.Response;
import com.finicityclient.type.component.rest.RestClient;
import com.finicityclient.type.type.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultAccountOperations extends DefaultOperations implements AccountOperations {

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(DefaultAccountOperations.class));

    public DefaultAccountOperations(RestClient restClient, String appKey, Token token) {
        super(restClient, appKey, token);
    }

    private List<? extends AccountResponse> processAccountResponses(Response response) {
        try {
            if (response.getStatusCode() == 200) {
                return serializer.read(Accounts.class, response.getBody()).getAccounts();
            } else if (response.getStatusCode() == 203) {
                return serializeMfaChallenges(response);
            } else {
                throw new AccountOperations.AccountOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
            }
        } catch (Exception ex) {
            LOGGER.log(Level.ALL, "An error occurred when parsing the accounts response.", ex);

            throw new AccountOperations.AccountOperationsException("An error occurred when parsing the accounts response.", ex);
        }
    }

    private List<? extends AccountResponse> serializeMfaChallenges(Response response) throws Exception {
        List<MfaChallengeResponse> mfaChallengeResponses = serializer.read(MfaChallenges.class, response.getBody()).getMfaChallenges();
        for (MfaChallengeResponse mfaChallengeResponse : mfaChallengeResponses) {
            mfaChallengeResponse.setSession(response.getHeaderFields().get("MFA-Session").get(0));
        }
        return mfaChallengeResponses;
    }

    @Override
    public List<? extends AccountResponse> addAllAccounts(String customerId, String institutionId, AccountLoginForm accounts) {
        assert customerId != null;
        assert institutionId != null;
        assert accounts != null;

        Response response = restClient.executePost("/v1/customers/" + customerId + "/institutions/" + institutionId + "/accounts/addall",
                accounts,
                null,
                null);

        return processAccountResponses(response);
    }

    @Override
    public List<? extends AccountResponse> addAllAccountsMfa(String mfaSession, String customerId, String institutionId, AccountMfaChallenge accounts) {
        assert mfaSession != null;
        assert customerId != null;
        assert institutionId != null;
        assert accounts != null;

        Map<String, String> additionalHeaders = new HashMap<>();
        additionalHeaders.put("MFA-Session", mfaSession);

        Response response = restClient.executePost("/v1/customers/" + customerId + "/institutions/" + institutionId + "/accounts/addall/mfa",
                accounts,
                null,
                additionalHeaders);

        return processAccountResponses(response);
    }

    @Override
    public List<? extends AccountResponse> discoverAccounts(String customerId, String institutionId, AccountLoginForm accounts) {
        assert customerId != null;
        assert institutionId != null;
        assert accounts != null;

        Response response = restClient.executePost("/v1/customers/" + customerId + "/institutions/" + institutionId + "/accounts",
                accounts,
                null,
                null);

        return processAccountResponses(response);
    }

    @Override
    public List<? extends AccountResponse> discoverAccountsMfa(String mfaSession, String customerId, String institutionId, AccountMfaChallenge accounts) {
        assert mfaSession != null;
        assert customerId != null;
        assert institutionId != null;
        assert accounts != null;

        Map<String, String> additionalHeaders = new HashMap<>();
        additionalHeaders.put("MFA-Session", mfaSession);

        Response response = restClient.executePost("/v1/customers/" + customerId + "/institutions/" + institutionId + "/accounts/mfa",
                accounts,
                null,
                additionalHeaders);

        return processAccountResponses(response);
    }

    @Override
    public List<Account> activateAccounts(String customerId, String institutionId, Accounts accounts) {
        assert customerId != null;
        assert institutionId != null;
        assert accounts != null;

        Response response = restClient.executePut("/v2/customers/" + customerId + "/institutions/" + institutionId + "/accounts",
                accounts,
                null,
                null);

        try {
            return serializer.read(Accounts.class, response.getBody()).getAccounts();
        } catch (Exception ex) {
            LOGGER.log(Level.ALL, "An error occurred when parsing the accounts response.", ex);

            throw new AccountOperations.AccountOperationsException("An error occurred when parsing the accounts response.", ex);
        }
    }

    @Override
    public List<? extends AccountResponse> refreshAccount(String customerId, String accountId) {
        assert customerId != null;
        assert accountId != null;

        Response response = restClient.executePost("/v1/customers/" + customerId + "/accounts/" + accountId,
                null,
                null,
                null);

        return processAccountResponses(response);
    }

    @Override
    public List<? extends AccountResponse> refreshAccountMfa(String mfaSession, String customerId, String accountId, MfaChallengeRequest mfaChallenges) {
        assert mfaSession != null;
        assert customerId != null;
        assert accountId != null;
        assert mfaChallenges != null;

        Map<String, String> headers = new HashMap<>();
        headers.put("MFA-Session", mfaSession);

        Response response = restClient.executePost("/v1/customers/" + customerId + "/accounts/" + accountId,
                mfaChallenges,
                null,
                headers);

        return processAccountResponses(response);
    }

    @Override
    public List<Account> refreshAccounts(String customerId) {
        assert customerId != null;

        Response response = restClient.executePost("/v1/customers/" + customerId + "/accounts",
                null,
                null,
                null);

        try {
            return serializer.read(Accounts.class, response.getBody()).getAccounts();
        } catch (Exception ex) {
            LOGGER.log(Level.ALL, "An error occurred when parsing the accounts response.", ex);

            throw new AccountOperations.AccountOperationsException("An error occurred when parsing the accounts response.", ex);
        }
    }

    @Override
    public List<Account> getAccounts(String customerId) {
        assert customerId != null;

        Response response = restClient.executeGet("/v1/customers/" + customerId + "/accounts",
                null,
                null);

        if (response.getStatusCode() != 200) {
            throw new AccountOperations.AccountOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }

        try {
            return serializer.read(Accounts.class, response.getBody()).getAccounts();
        } catch (Exception ex) {
            LOGGER.log(Level.ALL, "An error occurred when parsing the accounts response.", ex);

            throw new AccountOperations.AccountOperationsException("An error occurred when parsing the accounts response.", ex);
        }
    }

    @Override
    public List<Account> getAccounts(String customerId, String institutionId) {
        assert customerId != null;
        assert institutionId != null;

        Response response = restClient.executeGet("/v1/customers/" + customerId + "/institutions/" + institutionId + "/accounts",
                null,
                null);

        if (response.getStatusCode() != 200) {
            throw new AccountOperations.AccountOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }

        try {
            return serializer.read(Accounts.class, response.getBody()).getAccounts();
        } catch (Exception ex) {
            LOGGER.log(Level.ALL, "An error occurred when parsing the accounts response.", ex);

            throw new AccountOperations.AccountOperationsException("An error occurred when parsing the accounts response.", ex);
        }
    }

    @Override
    public Account getAccount(String customerId, String accountId) {
        assert customerId != null;
        assert accountId != null;

        Response response = restClient.executeGet("/v1/customers/" + customerId + "/accounts/" + accountId,
                null,
                null);

        if (response.getStatusCode() != 200) {
            throw new AccountOperations.AccountOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }

        try {
            return serializer.read(Account.class, response.getBody());
        } catch (Exception ex) {
            LOGGER.log(Level.ALL, "An error occurred when parsing the account response.", ex);

            throw new AccountOperations.AccountOperationsException("An error occurred when parsing the account response.", ex);
        }
    }

    @Override
    public void modifyAccount(String customerId, String accountId, Account account) {
        assert customerId != null;
        assert accountId != null;
        assert account != null;

        Response response = restClient.executePut("/v1/customers/{customerId}/accounts/{accountId}",
                account,
                null,
                null);

        if (response.getStatusCode() != 204) {
            throw new AccountOperations.AccountOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }
    }

    @Override
    public void deleteAccount(String customerId, String accountId) {
        assert customerId != null;
        assert accountId != null;

        Response response = restClient.executeDelete("/v1/customers/" + customerId + "/accounts/" + accountId,
                null,
                null);

        if (response.getStatusCode() != 204) {
            throw new AccountOperations.AccountOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }
    }

    @Override
    public LoginForm getAccountLoginForm(String customerId, String accountId) {
        assert customerId != null;
        assert accountId != null;

        Response response = restClient.executeGet("/v1/customers/" + customerId + "/accounts/" + accountId + "/loginForm",
                null,
                null);

        if (response.getStatusCode() != 200) {
            throw new AccountOperations.AccountOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }

        try {
            return serializer.read(LoginForm.class, response.getBody());
        } catch (Exception ex) {
            LOGGER.log(Level.ALL, "An error occurred when parsing the login form response.", ex);

            throw new AccountOperations.AccountOperationsException("An error occurred when parsing the login form response.", ex);
        }
    }

    @Override
    public void modifyAccountCredentials(String customerId, String accountId, LoginForm accountLoginForm) {
        assert customerId != null;
        assert accountId != null;
        assert accountLoginForm != null;

        Response response = restClient.executePut("/v1/customers/{customerId}/accounts/{accountId}/loginForm",
                accountLoginForm,
                null,
                null);

        if (response.getStatusCode() != 204) {
            throw new AccountOperations.AccountOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }
    }
}

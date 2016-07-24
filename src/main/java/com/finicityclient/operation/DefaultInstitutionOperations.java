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

import com.finicityclient.component.StringUtils;
import com.finicityclient.component.Token;
import com.finicityclient.component.rest.Parameter;
import com.finicityclient.component.rest.Response;
import com.finicityclient.component.rest.RestClient;
import com.finicityclient.type.Institution;
import com.finicityclient.type.InstitutionDetails;
import com.finicityclient.type.Institutions;
import com.finicityclient.type.LoginForm;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Default implementation of {@link InstitutionOperations}.
 */
public class DefaultInstitutionOperations extends DefaultOperations implements InstitutionOperations {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(DefaultInstitutionOperations.class));

    /**
     * Construct a client for Institution operations.
     *
     * @param restClient A REST client for operations.
     * @param appKey     Finicity appKey.
     * @param token      Finicity authentication token.
     */
    public DefaultInstitutionOperations(RestClient restClient, String appKey, Token token) {
        super(restClient, appKey, token);
    }

    @Override
    public List<Institution> getInstitutions(String search, Integer start, Integer limit) {
        List<Parameter> parameters = new ArrayList<>();
        if (StringUtils.isNotBlank(search)) {
            parameters.add(new Parameter("search", search));
        }
        if (start != null) {
            parameters.add(new Parameter("start", start.toString()));
        }
        if (limit != null) {
            parameters.add(new Parameter("limit", limit.toString()));
        }

        Response response = restClient.executeGet("/v1/institutions",
                parameters,
                null);

        if (response.getStatusCode() != 200) {
            throw new InstitutionOperations.InstitutionOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }

        try {
            return serializer.read(Institutions.class, response.getBody()).getInstitutions();
        } catch (Exception ex) {
            LOGGER.log(Level.ALL, "An error occurred when parsing the transactions response.", ex);

            throw new InstitutionOperations.InstitutionOperationsException("An error occurred when parsing the institutions response.", ex);
        }
    }

    @Override
    public Institution getInstitution(String institutionId) {
        assert institutionId != null;

        Response response = restClient.executeGet("/v1/institutions/" + institutionId,
                null,
                null);

        if (response.getStatusCode() != 200) {
            throw new InstitutionOperations.InstitutionOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }

        try {
            return serializer.read(Institution.class, response.getBody());
        } catch (Exception ex) {
            LOGGER.log(Level.ALL, "An error occurred when parsing the institution response.", ex);

            throw new InstitutionOperations.InstitutionOperationsException("An error occurred when parsing the institution response.", ex);
        }
    }

    @Override
    public InstitutionDetails getInstitutionDetails(String institutionId) {
        assert institutionId != null;

        Response response = restClient.executeGet("/v1/institutions/" + institutionId + "/details",
                null,
                null);

        if (response.getStatusCode() != 200) {
            throw new InstitutionOperations.InstitutionOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }

        try {
            return serializer.read(InstitutionDetails.class, response.getBody());
        } catch (Exception ex) {
            LOGGER.log(Level.ALL, "An error occurred when parsing the institution response.", ex);

            throw new InstitutionOperations.InstitutionOperationsException("An error occurred when parsing the institution response.", ex);
        }
    }

    @Override
    public LoginForm getInstitutionLoginForm(String institutionId) {
        assert institutionId != null;

        Response response = restClient.executeGet("/v1/institutions/" + institutionId + "/loginForm",
                null,
                null);

        if (response.getStatusCode() != 200) {
            throw new InstitutionOperations.InstitutionOperationsException("Invalid response: " + response.getStatusCode() + ", " + response.getBody());
        }

        try {
            return serializer.read(LoginForm.class, response.getBody());
        } catch (Exception ex) {
            LOGGER.log(Level.ALL, "An error occurred when parsing the institution response.", ex);

            throw new InstitutionOperations.InstitutionOperationsException("An error occurred when parsing the institution response.", ex);
        }
    }
}

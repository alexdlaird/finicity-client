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

import com.finicityclient.type.exception.FinicityException;
import com.finicityclient.type.type.Institution;
import com.finicityclient.type.type.InstitutionDetails;
import com.finicityclient.type.type.LoginForm;

import java.util.List;

public interface InstitutionOperations {
    /**
     * Return all financial institutions that contain the search text in the institution’s &lt;name&gt;, &lt;urlHomeApp&gt;, or &lt;urlLogonApp&gt; fields. Return all institutions if no query parameters are provided or if the search string is a single asterisk *.
     *
     * See <a href="https://finicity.zendesk.com/hc/en-us/articles/202460545">Handling Spaces in Queries</a> to search for multiple words.
     *
     * Success: HTTP 200 (OK)
     *
     * @param search Query: Text to match, or * to return all supported institutions
     * @param start  Query: Starting index for this page of results (default is 1, ignored if search=*)
     * @param limit  Query: Maximum number of entries for this page of results (default is 25, ignored if search=*)
     * @return An institution matching the query
     * @throws FinicityException An error occurred when interaction with the API
     */
    List<Institution> getInstitutions(String search, Integer start, Integer limit);

    /**
     * Get details for the specified institution.
     *
     * Success: HTTP 200 (OK)
     *
     * @param institutionId (required) ID of the institution
     * @return An institution matching the ID given.
     * @throws FinicityException An error occurred when interaction with the API
     */
    Institution getInstitution(String institutionId);

    /**
     * Get expanded details (including address information and loginForm) for the specified institution.
     *
     * Success: HTTP 200 (OK)
     *
     * @param institutionId (required) ID of the institution
     * @return Details for the institution matching the ID given.
     * @throws FinicityException An error occurred when interaction with the API
     */
    InstitutionDetails getInstitutionDetails(String institutionId);

    /**
     * Get the login form fields required for Discover Customer Accounts. The form is typically displayed to a customer to obtain credentials to access the customer's accounts at this institution.
     *
     * Success: HTTP 200 (OK)
     *
     * @param institutionId (required) ID of the institution
     * @return The login form fields for the institution matching the ID given.
     * @throws FinicityException An error occurred when interaction with the API
     */
    LoginForm getInstitutionLoginForm(String institutionId);

    class InstitutionOperationsException extends RuntimeException {
        public InstitutionOperationsException(String msg, Exception cause) {
            super(msg, cause);
        }

        public InstitutionOperationsException(String msg) {
            super(msg);
        }
    }
}

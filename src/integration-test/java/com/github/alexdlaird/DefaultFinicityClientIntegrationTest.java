package com.github.alexdlaird;

import com.github.alexdlaird.component.StringUtils;
import com.github.alexdlaird.type.account.*;
import com.github.alexdlaird.type.customer.Customer;
import com.github.alexdlaird.type.institution.InstitutionDetails;
import com.github.alexdlaird.type.institution.LoginForm;
import com.github.alexdlaird.type.transaction.Transaction;
import com.github.alexdlaird.type.transaction.TransactionStatus;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class DefaultFinicityClientIntegrationTest {

    private final static String TEST_INSTITUTION_ID = "101732";

    private String appKey;

    private String partnerId;

    private String partnerSecret;

    private DefaultFinicityClient finicityClient;

    @Before
    public void setUp() throws Exception {
        appKey = System.getProperty("finicityDevAppKey");
        partnerId = System.getProperty("finicityDevPartnerId");
        partnerSecret = System.getProperty("finicityDevPartnerSecret");

        if (StringUtils.isBlank(appKey) || StringUtils.isBlank(partnerId) || StringUtils.isBlank(partnerSecret)) {
            throw new Exception("Integration tests can only be run if these variables are set in gradle.properties: finicityDevAppKey, finicityDevPartnerId, finicityDevPartnerSecret");
        }

        finicityClient = DefaultFinicityClient.getInstance(appKey, partnerId, partnerSecret);
    }

    @Test
    public void endToEndTest() {
        Customer customer = givenCustomer();
        AccountLoginForm accountLoginForm = givenAuthenticationDetails();
        List<Account> accounts = givenAccounts(customer, accountLoginForm);
        // This test transaction is for pusher callbacks, it won't be returned in the list of test transactions below
        Transaction transaction = givenTransaction(customer, accounts);

        List<Transaction> transactions = finicityClient.getTransactionOperations().getTransactions(customer.getId(), ((System.currentTimeMillis() - 259200000) / 1000), System.currentTimeMillis() / 1000, null, null, null, true);

        // Test transactions are generated periodically, so there aren't any that will be returned here
        assertEquals(0, transactions.size());

        // Burn it down
        for (Account account : accounts) {
            finicityClient.getAccountOperations().deleteAccount(customer.getId(), account.getId());
        }
    }

    public Customer givenCustomer() {
        // Get your API credentials from https://developer.finicity.com/admin
        // Note: If a Customer already exists, use the code believe to simply retrieve that Customer on integration
//        Customer customer = new Customer("test-username",
//                "First Name",
//                "Last Name");
//        return finicityClient.getCustomerOperations().addTestingCustomer(customer);
        return finicityClient.getCustomerOperations().getCustomer("6325322");
    }

    public AccountLoginForm givenAuthenticationDetails() {
        InstitutionDetails institutionDetails = finicityClient.getInstitutionOperations().getInstitutionDetails(TEST_INSTITUTION_ID);

        // Build an authentication form with details from Finicity's tutorial; try different authentication schemes by using
        // different values from the tutorial at https://finicity.zendesk.com/hc/en-us/articles/201750869-Testing-Accounts
        LoginForm loginForm = institutionDetails.getLoginForm();
        loginForm.getLoginField().get(0).setValue("tfa_text");
        loginForm.getLoginField().get(1).setValue("go");
        return new AccountLoginForm(loginForm);
    }

    public List<Account> givenAccounts(Customer customer, AccountLoginForm accountLoginForm) {
        // Attempt to add the Accounts, you will get MFA challenges back
        List<? extends AccountResponse> accountResponses = finicityClient.getAccountOperations().addAllAccounts(customer.getId(),
                TEST_INSTITUTION_ID,
                accountLoginForm);

        // Ideally you'd loop here (as there may be more than one set of MFA challenges), but for test Accounts, we know there
        // won't be a rechallenge
        if (accountResponses.size() > 0 &&
                accountResponses.get(0) instanceof MfaChallengeResponse)

        {
            MfaChallengeResponse mfaChallengeResponse = ((List<MfaChallengeResponse>) accountResponses).get(0);
            // If you'd like to test multiple MFA challenges, set this answer to "mfa" and the test API will respond with
            // another challenge
            mfaChallengeResponse.getQuestions().get(0).setAnswer("success");
            MfaChallengeRequest mfaChallengeRequest = new MfaChallengeRequest((mfaChallengeResponse.getQuestions()));

            AccountMfaChallenge accountMfaChallenge = new AccountMfaChallenge(Collections.singletonList(mfaChallengeRequest));

            // Readd the Accounts with MFA authentication answers
            accountResponses = finicityClient.getAccountOperations().addAllAccountsMfa(mfaChallengeResponse.getSession(),
                    customer.getId(),
                    TEST_INSTITUTION_ID,
                    accountMfaChallenge);
        }

        // Now that we have responded with MFA answers, the response this time will be a list of Accounts
        return (List<Account>) accountResponses;
    }

    public Transaction givenTransaction(Customer customer, List<Account> accounts) {
        Transaction transactionRequest = new Transaction(24.0, "description", System.currentTimeMillis(), System.currentTimeMillis());

        Transaction transaction = finicityClient.getTxPushOperations().addTransactionForTestingAccount(customer.getId(), accounts.get(0).getId(), transactionRequest);

        assertNotNull(transaction.getId());
        assertNotNull(transaction.getCreatedDate());
        assertEquals(TransactionStatus.PENDING, transaction.getStatus());

        return transaction;
    }
}

[![Build Status](https://travis-ci.org/alexdlaird/finicity-client.svg)](https://travis-ci.org/alexdlaird/finicity-client)

# Finicity Client

A Finicity Client in Java for communicating with the [Finicity API](https://developer.finicity.com/admin/docs).

 **NOTE:** This SDK, while functional, was never fully finished (stability/efficiency improvements, relying on
 CompletableFutures, more robust testing, etc.), as Finicity eventually made their own APIs compatible with Intuit's
 SDK. They document the official supported way to connect to their APIs [here](https://community.finicity.com/s/article/208775606-Finicity-Setup#java_config),
 and Intuit's SDK can be found [here](https://developer.intuit.com/docs/00_quickbooks_online/2_build/40_sdks/02_java/0002_install_the_java_sdk).
 This is not an unsupported, legacy repository.

## Install It

Finicity Client is available on Maven Central, so it's as easy as including it as a dependency in your build
management system. Click on the badge below to see snippets to include the latest version in build tool.

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.alexdlaird/finicityclient/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.alexdlaird/finicityclient/)

## Use It

To get started, you'll need a Finicity Developer account, which you can sign up for [here](https://www.finicity.com/signup).

Second, you'll need a live Developer App. You can find your app details and credentials [here](https://developer.finicity.com/admin).

The following code, which retrieves a FinicityClient instance, adds a test Customer, and adds for that Customer all
available Accounts (with MFA) for the test Institution (per this [Finicity tutorial](https://finicity.zendesk.com/hc/en-us/articles/201750869-Testing-Accounts)), should get you started:

```java
// Get your API credentials from https://developer.finicity.com/admin
String appKey = "FINICITY_APP_KEY";
String partnerId = "FINICITY_PARTNER_ID";
String partnerSecret = "FINICITY_PARTNER_SECRET";

FinicityClient finicityClient = DefaultFinicityClient.getInstance(appKey, partnerId, partnerSecret);
```

And there you go! You now have an instance of a client with an authenticated token, which you can use to easily
communicate with the API. Here are some simple examples of API interactions:

```java
// This is the test Institution ID defined by Finicity
String testInstitutionId = "101732";

// Create a test Customer for development
Customer customer = new Customer("test-username",
        "First Name",
        "Last Name");
customer = finicityClient.getCustomerOperations().addTestingCustomer(customer);
// InstitutionDetails allow you to see login fields needed for an institution
InstitutionDetails institutionDetails = finicityClient.getInstitutionOperations().getInstitutionDetails(testInstitutionId);
```

This first example illustrates adding accounts with multi-factory authentication (MFA). Note that Finicity's test
account with credentials "tfa_text" and "go" will always return an MFA request.

```java
// Build an authentication form with details from Finicity's tutorial; try different authentication schemes by using
// different values from the tutorial at https://finicity.zendesk.com/hc/en-us/articles/201750869-Testing-Accounts
LoginForm loginForm = institutionDetails.getLoginForm();
loginForm.getLoginField().get(0).setValue("tfa_text");
loginForm.getLoginField().get(1).setValue("go");
AccountLoginForm accountLoginForm = new AccountLoginForm(loginForm);

// Attempt to add the Accounts, but you will get MFA challenges back
accountResponses = finicityClient.getAccountOperations().addAllAccounts(customer.getId(),
        testInstitutionId,
        accountLoginForm);

// Finicity's test Institution will likely continually return MFA challenges to you so you can test an integration
// that supports multiple MFA challenges; your integration will need to catch subsequent requests and respond to their
// answers as well
while (accountResponses.size() > 0 &&
        accountResponses.get(0) instanceof MfaChallengeResponse) {
    MfaChallengeResponse mfaChallengeResponse = ((List<MfaChallengeResponse>) accountResponses).get(0);
    // If you'd like to test multiple MFA challenges, set this answer to "mfa" and the test API will respond with
    // another challenge
    mfaChallengeResponse.getQuestions().get(0).setAnswer("success");
    MfaChallengeRequest mfaChallengeRequest = new MfaChallengeRequest((mfaChallengeResponse.getQuestions()));

    AccountMfaChallenge accountMfaChallenge = new AccountMfaChallenge(Collections.singletonList(mfaChallengeRequest));

    // Attempt to readd Account, you may receive another MFA challenge
    accountResponses = finicityClient.getAccountOperations().addAllAccountsMfa(mfaChallengeResponse.getSession(),
            customer.getId(),
            testInstitutionId,
            accountMfaChallenge);
}

// Yay, all MFA challenges have been answered!
List<Account> accounts = (List<Account>) accountResponses;
```

If you want to interact with Transactions, use a basic authentication flow to avoid MFA challenges when in development.

```java
LoginForm loginForm = institutionDetails.getLoginForm();
loginForm.getLoginField().get(0).setValue("demo");
loginForm.getLoginField().get(1).setValue("go");
AccountLoginForm accountLoginForm = new AccountLoginForm(loginForm);

// Add all the test Accounts
accounts = (List<Account>) finicityClient.getAccountOperations().addAllAccounts(customer.getId(),
        testInstitutionId,
        accountLoginForm);

// You can force an Account to refresh with the latest Transactions, but this is usually unnecessary
for (Account account : accounts) {
    try {
        finicityClient.getAccountOperations().refreshAccount(customer.getId(), account.getId());
    } catch (RestClient.RestClientException ex) {
        // Doh! Check the exception response details
    }
}

// Finicity adds a few Transactions each day to the test Accounts
Long from = ((System.currentTimeMillis() - 259200000) / 1000);
Long to = System.currentTimeMillis() / 1000;
List<Transaction> transactions = finicityClient.getTransactionOperations().getTransactions(customer.getId(), from, to, null, null, null, true);
for (Transaction transaction : transactions) {
    System.out.println(transaction.getId() + ":" + transaction.getAccountId() + ":" + transaction.getAmount());
}
```

## Build It / Contribute

Need functionality not supported out of the box, or just feeling ambitious? Clone the repository and get your hands
dirty with the code! When your changes are implemented and ready to be used, execute the following:

```
./gradlew buildAll
```

This will generate both useful Javadoc as well as a dependency JAR in `build/libs`, which you can include as a local
dependency in your own project.

But don't stop there! Extending this client is almost certainly something the rest of us would benefit from, so if you
build something useful, please submit a pull request to the `develop` branch.

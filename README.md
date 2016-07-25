[![Build Status](https://travis-ci.org/alexdlaird/finicity-client.svg)](https://travis-ci.org/alexdlaird/finicity-client)

# Finicity Client

A Finicity Client in Java for communicating with the [Finicity API](https://developer.finicity.com/admin/docs).

This client is currently usable, but the first fully-functional, stable release is still in development. You can,
however, clone the source and build it yourself. The `master` branch is pseudo-stable, and when an official release is
made available on Maven, we'll update this `README`.

## Install It

Finicity Client is available on Maven Central, so it's as easy as including it as a depenendency in your build
management system.

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.alexdlaird/finicityclient/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.alexdlaird/finicityclient/)

## Use It

To get started, you'll need a Finicity Developer account, which you can sign up for [here](https://www.finicity.com/signup).

Second, you'll need a live Developer App. You can find your app details and credentials [here](https://developer.finicity.com/admin).

The following code, which retrieves a FinicityClient instance, adds a test Customer, and adds for that Customer all
available Accounts (with MFA) for the test Institution (per this [Finicity tutorial](https://finicity.zendesk.com/hc/en-us/articles/201750869-Testing-Accounts)), should get you started:

```java
// This is the test Institution ID defined by Finicity
String appKey = "FINICITY_APP_KEY";
String partnerId = "FINICITY_PARTNER_ID";
String partnerSecret = "FINICITY_PARTNER_SECRET";
String testInstitutionId = "101732";

FinicityClient finicityClient = DefaultFinicityClient.getInstance(appKey, partnerId, partnerSecret);

// Get your API credentials from https://developer.finicity.com/admin
Customer customer = new Customer("test-username",
        "First Name",
        "Last Name");
customer = finicityClient.getCustomerOperations().addTestingCustomer(customer);
InstitutionDetails institutionDetails = finicityClient.getInstitutionOperations().getInstitutionDetails(testInstitutionId);

// Build an authentication form with details from Finicity's tutorial; try different authentication schemes by using
// different values from the tutorial at https://finicity.zendesk.com/hc/en-us/articles/201750869-Testing-Accounts
LoginForm loginForm = institutionDetails.getLoginForm();
loginForm.getLoginField().get(0).setValue("tfa_text");
loginForm.getLoginField().get(1).setValue("go");
AccountLoginForm accountLoginForm = new AccountLoginForm(loginForm);

// Attempt to add the Accounts, you will get MFA challenges back
accountResponses = finicityClient.getAccountOperations().addAllAccounts(customer.getId(),
        testInstitutionId,
        accountLoginForm);

// Ideally you'd loop here (as there may be more than one set of MFA challenges), but for test Accounts, we know there
// won't be a rechallenge
if (accountResponses.size() > 0 &&
        accountResponses.get(0) instanceof MfaChallengeResponse) {
    MfaChallengeResponse mfaChallengeResponse = ((List<MfaChallengeResponse>) accountResponses).get(0);
    // If you'd like to test multiple MFA challenges, set this answer to "mfa" and the test API will respond with
    // another challenge
    mfaChallengeResponse.getQuestions().get(0).setAnswer("success");
    MfaChallengeRequest mfaChallengeRequest = new MfaChallengeRequest((mfaChallengeResponse.getQuestions()));
    
    AccountMfaChallenge accountMfaChallenge = new AccountMfaChallenge(Collections.singletonList(mfaChallengeRequest));

    // Readd the Accounts with MFA authentication answers
    accountResponses = finicityClient.getAccountOperations().addAllAccountsMfa(mfaChallengeResponse.getSession(),
            customer.getId(),
            testInstitutionId,
            accountMfaChallenge);
}

// Now that we have responded with MFA answers, the response this time will be a list of Accounts
List<Account> accounts = (List<Account>) accountResponses;
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
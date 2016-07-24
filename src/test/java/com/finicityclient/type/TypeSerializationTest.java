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

package com.finicityclient.type;

import com.finicityclient.type.component.FinicityPersister;
import com.finicityclient.type.type.*;
import org.junit.Ignore;
import org.junit.Test;
import org.simpleframework.xml.Serializer;

import java.io.StringWriter;

import static junit.framework.TestCase.assertEquals;

public class TypeSerializationTest {

    private final Serializer serializer = new FinicityPersister();

    @Test
    public void testAccountsSerialization() throws Exception {
        String xml = "<accounts found=\"0\" displaying=\"0\" moreAvailable=\"false\">\n" +
                "   <account>\n" +
                "      <id>2083</id>\n" +
                "      <number>8000008888</number>\n" +
                "      <name>Auto Loan</name>\n" +
                "      <type>loan</type>\n" +
                "      <status>active</status>\n" +
                "      <balance>-1234.56</balance>\n" +
                "      <aggregationStatusCode>125</aggregationStatusCode>\n" +
                "      <customerId>41442</customerId>\n" +
                "      <institutionId>101732</institutionId>\n" +
                "      <balanceDate>1421996400</balanceDate>\n" +
                "      <aggregationSuccessDate>1421996400</aggregationSuccessDate>\n" +
                "      <aggregationAttemptDate>1423239602</aggregationAttemptDate>\n" +
                "      <createdDate>1415255907</createdDate>\n" +
                "      <lastUpdatedDate>1422467353</lastUpdatedDate>\n" +
                "   </account>\n" +
                "</accounts>";
        Accounts obj = serializer.read(Accounts.class, xml);

        StringWriter writer = new StringWriter();
        serializer.write(obj, writer);
        assertEquals(xml, writer.toString());
    }

    @Test
    public void testAccountLoginFormSerialization() throws Exception {
        String xml = "<accounts>\n" +
                "   <credentials>\n" +
                "      <loginField>\n" +
                "         <id>FIELD_ID_1</id>\n" +
                "         <name>FIELD_NAME_1</name>\n" +
                "         <value>FIELD_VALUE_1</value>\n" +
                "         <displayOrder>0</displayOrder>\n" +
                "         <mask>false</mask>\n" +
                "         <valueLengthMin>0</valueLengthMin>\n" +
                "         <valueLengthMax>0</valueLengthMax>\n" +
                "      </loginField>\n" +
                "      <loginField>\n" +
                "         <id>FIELD_ID_2</id>\n" +
                "         <name>FIELD_NAME_2</name>\n" +
                "         <value>FIELD_VALUE_2</value>\n" +
                "         <displayOrder>0</displayOrder>\n" +
                "         <mask>false</mask>\n" +
                "         <valueLengthMin>0</valueLengthMin>\n" +
                "         <valueLengthMax>0</valueLengthMax>\n" +
                "      </loginField>\n" +
                "   </credentials>\n" +
                "</accounts>";

        AccountLoginForm obj = serializer.read(AccountLoginForm.class, xml);

        StringWriter writer = new StringWriter();
        serializer.write(obj, writer);
        assertEquals(xml, writer.toString());
    }

    @Test
    public void testAccountMfaChallengeSerialization() throws Exception {
        String xml = "<accounts>\n" +
                "   <mfaChallenges>\n" +
                "      <questions class=\"java.util.ArrayList\">\n" +
                "         <question>\n" +
                "            <text>Name of your first pet:</text>\n" +
                "            <answer>MFA_ANSWER</answer>\n" +
                "         </question>\n" +
                "      </questions>\n" +
                "   </mfaChallenges>\n" +
                "</accounts>";

        AccountMfaChallenge obj = serializer.read(AccountMfaChallenge.class, xml);

        StringWriter writer = new StringWriter();
        serializer.write(obj, writer);
        assertEquals(xml, writer.toString());
    }

    @Test
    public void testAccountMfaChallengeRequestSerialization() throws Exception {
        String xml = "<mfaChallenges>\n" +
                "   <questions class=\"java.util.ArrayList\">\n" +
                "      <question>\n" +
                "         <text>Name of your first pet:</text>\n" +
                "         <answer>MFA_ANSWER</answer>\n" +
                "      </question>\n" +
                "   </questions>\n" +
                "</mfaChallenges>";

        MfaChallengeRequest obj = serializer.read(MfaChallengeRequest.class, xml);

        StringWriter writer = new StringWriter();
        serializer.write(obj, writer);
        assertEquals(xml, writer.toString());
    }

    @Test
    public void testCustomersSerialization() throws Exception {
        String xml = "<customers found=\"0\" displaying=\"0\" moreAvailable=\"false\">\n" +
                "   <customer>\n" +
                "      <id>41442</id>\n" +
                "      <username>rsmith</username>\n" +
                "      <firstName>Ron</firstName>\n" +
                "      <lastName>Smith</lastName>\n" +
                "      <type>active</type>\n" +
                "      <createdDate>1412792539</createdDate>\n" +
                "   </customer>\n" +
                "</customers>";
        Customers obj = serializer.read(Customers.class, xml);

        StringWriter writer = new StringWriter();
        serializer.write(obj, writer);
        assertEquals(xml, writer.toString());
    }

    @Test
    public void testInstitutionsSerialization() throws Exception {
        String xml = "<institutions found=\"0\" displaying=\"0\" moreAvailable=\"false\">\n" +
                "   <institution>\n" +
                "      <id>11863</id>\n" +
                "      <name>Clearfield Bank &amp; Trust Co</name>\n" +
                "      <accountTypeDescription>Banking</accountTypeDescription>\n" +
                "      <urlHomeApp>https://www.clearfieldbankandtrust.com/</urlHomeApp>\n" +
                "      <urlLogonApp>https://www.netteller.com/clearfieldbankandtrust/login.cfm</urlLogonApp>\n" +
                "      <phone>814-765-7551</phone>\n" +
                "      <currency>USD</currency>\n" +
                "      <email>support@cbtfinancial.com</email>\n" +
                "      <specialText>Please enter your Clearfield Bank &amp; Trust Co ONLINE24 Internet Banking ID and ONLINE24 Internet Banking Password required for login.</specialText>\n" +
                "      <address>\n" +
                "         <addressLine1>11 N. Second Street</addressLine1>\n" +
                "         <addressLine2>PO Box 171</addressLine2>\n" +
                "         <city>Clearfield</city>\n" +
                "         <state>PA</state>\n" +
                "         <postalCode>16830</postalCode>\n" +
                "         <country>USA</country>\n" +
                "      </address>\n" +
                "   </institution>\n" +
                "</institutions>";
        Institutions obj = serializer.read(Institutions.class, xml);

        StringWriter writer = new StringWriter();
        serializer.write(obj, writer);
        assertEquals(xml, writer.toString());
    }

    @Test
    public void testInstitutionDetailsSerialization() throws Exception {
        String xml = "<institutionDetails>\n" +
                "   <institution>\n" +
                "      <id>11863</id>\n" +
                "      <name>Clearfield Bank &amp; Trust Co</name>\n" +
                "      <accountTypeDescription>Banking</accountTypeDescription>\n" +
                "      <urlHomeApp>https://www.clearfieldbankandtrust.com/</urlHomeApp>\n" +
                "      <urlLogonApp>https://www.netteller.com/clearfieldbankandtrust/login.cfm</urlLogonApp>\n" +
                "      <phone>814-765-7551</phone>\n" +
                "      <currency>USD</currency>\n" +
                "      <email>support@cbtfinancial.com</email>\n" +
                "      <specialText>Please enter your Clearfield Bank &amp; Trust Co ONLINE24 Internet Banking ID and ONLINE24 Internet Banking Password required for login.</specialText>\n" +
                "      <address>\n" +
                "         <addressLine1>11 N. Second Street</addressLine1>\n" +
                "         <addressLine2>PO Box 171</addressLine2>\n" +
                "         <city>Clearfield</city>\n" +
                "         <state>PA</state>\n" +
                "         <postalCode>16830</postalCode>\n" +
                "         <country>USA</country>\n" +
                "      </address>\n" +
                "   </institution>\n" +
                "   <loginForm>\n" +
                "      <loginField>\n" +
                "         <id>11863001</id>\n" +
                "         <name>ID</name>\n" +
                "         <displayOrder>1</displayOrder>\n" +
                "         <mask>false</mask>\n" +
                "         <description>ONLINE24 Internet Banking ID</description>\n" +
                "         <valueLengthMin>0</valueLengthMin>\n" +
                "         <valueLengthMax>0</valueLengthMax>\n" +
                "      </loginField>\n" +
                "      <loginField>\n" +
                "         <id>11863002</id>\n" +
                "         <name>PIN</name>\n" +
                "         <displayOrder>2</displayOrder>\n" +
                "         <mask>true</mask>\n" +
                "         <description>ONLINE24 Internet Banking Password</description>\n" +
                "         <valueLengthMin>0</valueLengthMin>\n" +
                "         <valueLengthMax>0</valueLengthMax>\n" +
                "      </loginField>\n" +
                "   </loginForm>\n" +
                "</institutionDetails>";

        InstitutionDetails obj = serializer.read(InstitutionDetails.class, xml);

        StringWriter writer = new StringWriter();
        serializer.write(obj, writer);
        assertEquals(xml, writer.toString());
    }

    @Test
    public void testPartnerAccessSerialization() throws Exception {
        String xml = "<access>\n" +
                "   <token>ACCESS_TOKEN</token>\n" +
                "</access>";
        PartnerAccess obj = serializer.read(PartnerAccess.class, xml);

        StringWriter writer = new StringWriter();
        serializer.write(obj, writer);
        assertEquals(xml, writer.toString());
    }

    @Test
    public void testTransactionsSerialization() throws Exception {
        String xml = "<transactions found=\"0\" displaying=\"0\" moreAvailable=\"false\">\n" +
                "   <transaction>\n" +
                "      <accountId>2055</accountId>\n" +
                "      <amount>-124.99</amount>\n" +
                "      <bonusAmount>0.0</bonusAmount>\n" +
                "      <createdDate>1422272248</createdDate>\n" +
                "      <customerId>41442</customerId>\n" +
                "      <description>CLICKDESK CA</description>\n" +
                "      <escrowAmount>0.0</escrowAmount>\n" +
                "      <feeAmount>0.0</feeAmount>\n" +
                "      <id>84293</id>\n" +
                "      <institutionTransactionId>0000237637</institutionTransactionId>\n" +
                "      <interestAmount>0.0</interestAmount>\n" +
                "      <postedDate>1422082800</postedDate>\n" +
                "      <principalAmount>0.0</principalAmount>\n" +
                "      <status>active</status>\n" +
                "      <subaccount>\n" +
                "         <name>J Green</name>\n" +
                "         <number>XXXX-XXXXXX-23687</number>\n" +
                "      </subaccount>\n" +
                "      <transactionDate>1422082800</transactionDate>\n" +
                "      <unitQuantity>0.0</unitQuantity>\n" +
                "      <unitValue>0.0</unitValue>\n" +
                "      <categorization>\n" +
                "         <normalizedPayeeName>CLICKDESK CA</normalizedPayeeName>\n" +
                "         <category>Unknown</category>\n" +
                "      </categorization>\n" +
                "   </transaction>\n" +
                "</transactions>";
        Transactions obj = serializer.read(Transactions.class, xml);

        StringWriter writer = new StringWriter();
        serializer.write(obj, writer);
        assertEquals(xml, writer.toString());
    }

    @Test
    public void testTextMfaChallengesSerialization() throws Exception {
        String xml = "<mfaChallenges found=\"0\" displaying=\"0\" moreAvailable=\"false\">\n" +
                "   <questions>\n" +
                "      <question>\n" +
                "         <text>Enter the name of your first pet:</text>\n" +
                "      </question>\n" +
                "   </questions>\n" +
                "</mfaChallenges>";
        MfaChallenges obj = serializer.read(MfaChallenges.class, xml);

        StringWriter writer = new StringWriter();
        serializer.write(obj, writer);
        assertEquals(xml, writer.toString());
    }

    @Test
    public void testImageMfaChallengesSerialization() throws Exception {
        String xml = "<mfaChallenges found=\"0\" displaying=\"0\" moreAvailable=\"false\">\n" +
                "   <questions>\n" +
                "      <question>\n" +
                "         <text>Enter image text</text>\n" +
                "         <image>data:image/png;base64,/9j...9k=</image>\n" +
                "      </question>\n" +
                "   </questions>\n" +
                "</mfaChallenges>";
        MfaChallenges obj = serializer.read(MfaChallenges.class, xml);

        StringWriter writer = new StringWriter();
        serializer.write(obj, writer);
        assertEquals(xml, writer.toString());
    }

    @Test
    public void testMultiTextMfaChallengesSerialization() throws Exception {
        String xml = "<mfaChallenges found=\"0\" displaying=\"0\" moreAvailable=\"false\">\n" +
                "   <questions>\n" +
                "      <question>\n" +
                "         <text>Which high school did you attend?</text>\n" +
                "         <choice value=\"Jefferson\">Jefferson</choice>\n" +
                "         <choice value=\"Wilson\">Wilson</choice>\n" +
                "         <choice value=\"Washington\">Washington</choice>\n" +
                "      </question>\n" +
                "   </questions>\n" +
                "</mfaChallenges>";
        MfaChallenges obj = serializer.read(MfaChallenges.class, xml);

        StringWriter writer = new StringWriter();
        serializer.write(obj, writer);
        assertEquals(xml, writer.toString());
    }

    @Test
    public void testMultiImageMfaChallengesSerialization() throws Exception {
        String xml = "<mfaChallenges found=\"0\" displaying=\"0\" moreAvailable=\"false\">\n" +
                "   <questions>\n" +
                "      <question>\n" +
                "         <text>Choose your favorite sport</text>\n" +
                "         <imageChoice value=\"hockey\">data:image/png;base64,/9j/4AAQ...Z//Z</imageChoice>\n" +
                "         <imageChoice value=\"soccer\">data:image/png;base64,/9j/4AA...f//Z</imageChoice>\n" +
                "         <imageChoice value=\"cricket\">data:image/png;base64,/9j/4AA...QB//2Q==</imageChoice>\n" +
                "         <imageChoice value=\"f1\">data:image/png;base64,/9j/4AAQ...MAx//9k=</imageChoice>\n" +
                "      </question>\n" +
                "   </questions>\n" +
                "</mfaChallenges>";
        MfaChallenges obj = serializer.read(MfaChallenges.class, xml);

        StringWriter writer = new StringWriter();
        serializer.write(obj, writer);
        assertEquals(xml, writer.toString());
    }
}

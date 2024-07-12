package ep2024.bwV.tools;
import ep2024.bwV.entities.Cliente;

import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailgunSender {
    private String apiKey;
    private String domainName;

    public MailgunSender(@Value("${mailgun.apikey}") String apiKey, @Value("${mailgun.domainname}")String domainName) {
        this.apiKey = apiKey;
        this.domainName = domainName;
    }

    public void sendRegistrationEmail(Cliente recipient){
        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/"+ this.domainName + "/messages")
                .basicAuth("api", this.apiKey)
                .queryString("from", "tizianosanseverino0@gmail.com")
                .queryString("to", recipient.getEmail())
                .queryString("subject", "Registrazione completata con successo!")
                .queryString("text", "Complimenti " + recipient.getNomeContatto() + " per esserti registrato con successo")
                .asJson();

        System.out.println(response.getBody());
    }
}




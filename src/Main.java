import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Usuario usuario = new Usuario();

        System.out.print("Digite o Usuario: ");
        String nome = sc.nextLine();
        usuario.setNome(nome);

        String endereco = "https://api.github.com/users/" + usuario.getNome();

        System.out.println(endereco);

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endereco))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 404) {
                throw new ErroConsultaGitHubException("Usuário não encontrado no GitHub.");
            }

            String json = response.body();
            Gson gson = new Gson();
            DataRecord record = gson.fromJson(json, DataRecord.class);

            System.out.println(record);


        } catch (ErroConsultaGitHubException e) {
            System.out.println(e.getMessage());
        } catch (IOException | InterruptedException e) {
            System.out.println("Ocorreu um erro na consulta ao GitHub: " + e.getMessage());
        }
    }
}
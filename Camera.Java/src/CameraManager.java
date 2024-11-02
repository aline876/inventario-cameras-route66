//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import camera.xlsx.Workbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class CameraManager {
    private static final String NOME_ARQUIVO = "cameras.csv";
    private static final List<Camera> cameras = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        carregarCameras();
        int opcao;

        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha pendente

            switch (opcao) {
                case 1 -> cadastrarCamera();
                case 2 -> pesquisarCamera();
                case 3 -> System.out.println("Encerrando o programa...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 3);
    }

    private static void exibirMenu() {
        System.out.println("\n=== Almoxarifado de Câmeras ===");
        System.out.println("1. Cadastrar câmera");
        System.out.println("2. Pesquisar câmera");
        System.out.println("3. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void cadastrarCamera() {
        System.out.print("Marca: ");
        String marca = scanner.nextLine();
        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();
        System.out.print("Número de série: ");
        String numeroSerie = scanner.nextLine();
        System.out.print("Código de barras: ");
        String codigoBarras = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        System.out.print("Quantidade: ");
        int quantidade = scanner.nextInt();
        System.out.print("Valor (R$): ");
        double valor = scanner.nextDouble();
        scanner.nextLine(); // Consumir a quebra de linha

        cameras.add(new Camera(marca, modelo, numeroSerie, codigoBarras, senha, quantidade, valor));
        salvarCameras();
        System.out.println("Câmera cadastrada com sucesso!");
    }

    private static void pesquisarCamera() {
        System.out.print("Digite o número de série ou código de barras da câmera: ");
        String pesquisa = scanner.nextLine();
        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();

        boolean encontrada = false;
        for (Camera cam : cameras) {
            if ((cam.getNumeroSerie().equalsIgnoreCase(pesquisa) || cam.getCodigoBarras().equalsIgnoreCase(pesquisa))
                    && cam.getSenha().equals(senha)) {
                System.out.println("Câmera encontrada: " + cam);
                encontrada = true;
                break;
            }
        }

        if (!encontrada) {
            System.out.println("Câmera não encontrada ou senha incorreta.");
        }
    }

    private static void salvarCameras() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOME_ARQUIVO))) {
            for (Camera cam : cameras) {
                writer.write(cam.toLine());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar as câmeras: " + e.getMessage());
        }
    }

    private static void carregarCameras() {
        try (BufferedReader reader = new BufferedReader(new FileReader(NOME_ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                String marca = dados[0];
                String modelo = dados[1];
                String numeroSerie = dados[2];
                String codigoBarras = dados[3];
                String senha = dados[4];
                int quantidade = Integer.parseInt(dados[5]);
                double valor = Double.parseDouble(dados[6]);
                cameras.add(new Camera(marca, modelo, numeroSerie, codigoBarras, senha, quantidade, valor));
            }
        } catch (IOException e) {
            System.out.println("Nenhum arquivo encontrado. O estoque está vazio.");
        }
    }
}

class Camera {
    private final String marca;
    private final String modelo;
    private final String numeroSerie;
    private final String codigoBarras;
    private final String senha;
    private final int quantidade;
    private final double valor;

    public Camera(String marca, String modelo, String numeroSerie, String codigoBarras, String senha, int quantidade, double valor) {
        this.marca = marca;
        this.modelo = modelo;
        this.numeroSerie = numeroSerie;
        this.codigoBarras = codigoBarras;
        this.senha = senha;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public String getModelo() {
        return modelo;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public String getSenha() {
        return senha;
    }

    public String toLine() {
        return marca + "," + modelo + "," + numeroSerie + "," + codigoBarras + "," + senha + "," + quantidade + "," + valor;
    }

    @Override
    public String toString() {
        return marca + " " + modelo + " - Número de Série: " + numeroSerie + " - Código de Barras: " + codigoBarras + " - Quantidade: " + quantidade + " - Valor: R$" + valor;
    }
}




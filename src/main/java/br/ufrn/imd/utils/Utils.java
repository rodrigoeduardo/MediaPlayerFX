package br.ufrn.imd.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A classe Utils fornece métodos utilitários para manipulação de arquivos .txt.
 */
public class Utils {

    /**
     * Lê o conteúdo de um arquivo e retorna cada linha como um elemento em uma lista de strings.
     * @param fileName O caminho do arquivo a ser lido.
     * @return Uma lista contendo as linhas do arquivo.
     * @throws IOException Se ocorrer um erro durante a leitura do arquivo.
     */
    public static List<String> lerArquivo(String fileName) throws IOException {
        File file = new File(fileName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;

        List<String> output = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            output.add(line);
        }

        br.close();
        fr.close();

        return output;
    }

    /**
     * Escreve uma linha no final de um arquivo.
     * @param fileName O caminho do arquivo a ser escrito.
     * @param linha Linha a ser escrita no arquivo.
     * @throws IOException Se ocorrer um erro durante a escrita no arquivo.
     */
    public static void escreverLinhaArquivo(String fileName, String linha) throws IOException {
        File file = new File(fileName);
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(linha);
        bw.newLine();

        bw.close();
        fw.close();
    }

    /**
     * Remove uma linha de um arquivo com base no valor da primeira coluna, em separação por vírgulas.
     * @param fileName O nome do arquivo a ser modificado.
     * @param linhaParaRemover O valor a ser procurado e removido na primeira coluna.
     * @throws IOException Se ocorrer um erro durante a leitura ou escrita do arquivo.
     */
    public static void removerPorNome(String fileName, String linhaParaRemover) throws IOException {
        File inputFile = new File(fileName);
        File tempFile = new File("tempFile.txt");

        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));

        String linhaAtual;

        boolean textoAchado = false;

        while ((linhaAtual = br.readLine()) != null) {
            String[] values = linhaAtual.split(",");

            if (values[0].equals(linhaParaRemover)) {
                textoAchado = true;
                continue;
            }
            bw.write(linhaAtual + "\n");
        }

        bw.close();
        br.close();

        if (!textoAchado) {
            System.out.println("Texto não encontrado.");
            tempFile.delete();
            return;
        }

        if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
            System.out.println("Erro ao deletar ou renomear arquivo.");
        }
    }
}

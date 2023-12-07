package br.ufrn.imd.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {
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

    public static void escreverLinhaArquivo(String fileName, String linha) throws IOException {
        File file = new File(fileName);
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(linha);
        bw.newLine();
        
        bw.close();
        fw.close();
    }

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

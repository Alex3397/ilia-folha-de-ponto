package br.com.ilia.digital.folhadeponto.repositories.local;

import br.com.ilia.digital.folhadeponto.objects.Allocation;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Path;

/**
 * Classe de Repositório de Alocações em arquivos locais
 * @since 2022-03-23 17:27
 */

@Component
public class LocalAllocationsRepository {

    /**
     * Função para salvar Alocações em arquivos locais
     * @since 2022-03-23 17:27
     */
    @SneakyThrows
    public static void saveAllocation(Allocation newAllocation, String day, String month, String year) {
        String date = year + "-" + month + "-" + day;

        checkIfFileExists(date);

        Path allocationPath = System.getProperty("os.name").contains("Windows") ? Path.of(System.getProperty("user.dir"), "\\config\\allocation" + date) : Path.of(System.getProperty("user.dir"), "/config/allocation" + date);
        File allocationFile = new File(String.valueOf(allocationPath));

        FileOutputStream allocation = new FileOutputStream(String.valueOf(allocationFile));
        ObjectOutputStream allocationOutput = new ObjectOutputStream(allocation);

        allocationOutput.writeObject(newAllocation);
        allocationOutput.flush();
        allocationOutput.close();
    }

    /**
     * Função para recuperar dados de Alocações em arquivos locais
     * @since 2022-03-23 17:27
     */
    @SneakyThrows
    public static Allocation getAllocation(String date) {
        checkIfFileExists(date);
        Path allocationPath = System.getProperty("os.name").contains("Windows") ? Path.of(System.getProperty("user.dir"), "\\config\\allocation" + date) : Path.of(System.getProperty("user.dir"), "/config/allocation" + date);
        FileInputStream allocationFile = new FileInputStream(String.valueOf(allocationPath));
        ObjectInputStream allocationInput = new ObjectInputStream(allocationFile);

        Allocation allocation = (Allocation) allocationInput.readObject();

        allocationInput.close();
        allocationFile.close();

        return allocation;
    }

    /**
     * Função para verificar se arquivos locais existem e retornar boolean
     * @since 2022-03-23 17:27
     */
    public static boolean fileExists(String date) {
        if (System.getProperty("os.name").contains("Windows")) {
            return new File(System.getProperty("user.dir"), "\\config\\allocation" + date).exists();
        } else {
            return new File(System.getProperty("user.dir"), "/config/allocation" + date).exists();
        }
    }

    /**
     * Função para verificar se arquivos locais existem e criar os arquivos se não existirem
     * @since 2022-03-23 17:27
     */
    @SneakyThrows
    public static void checkIfFileExists(String date) {
        if (!fileExists(date)) {
            Path allocationPath;
            allocationPath = System.getProperty("os.name").contains("Windows") ? Path.of(System.getProperty("user.dir"), "\\config\\allocation" + date) : Path.of(System.getProperty("user.dir"), "/config/allocation" + date);
            File allocationFile = new File(String.valueOf(allocationPath));

            //noinspection ResultOfMethodCallIgnored
            allocationFile.getParentFile().mkdirs();
            Allocation allocation = new Allocation();

            FileOutputStream allocationStream = new FileOutputStream(String.valueOf(allocationFile));
            ObjectOutputStream allocationOutput = new ObjectOutputStream(allocationStream);

            allocationOutput.writeObject(allocation);
            allocationOutput.flush();
            allocationOutput.close();
        }
    }
}

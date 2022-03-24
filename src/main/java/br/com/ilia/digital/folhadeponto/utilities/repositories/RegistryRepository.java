package br.com.ilia.digital.folhadeponto.utilities.repositories;

import br.com.ilia.digital.folhadeponto.objects.Registry;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Path;

/**
 * Repositório para Registro de horas de um Empregado
 *
 * @since 2022-03-23 17:27
 */

@Component
public class RegistryRepository {

    @SneakyThrows
    public static void saveRegistry(Registry newRegistry, String day, String month, String year) {
        String date = year + "-" + month + "-" + day;

        checkIfFileExists(date);

        Path registryPath = System.getProperty("os.name").contains("Windows") ? Path.of(System.getProperty("user.dir"), "\\config\\registry" + date) : Path.of(System.getProperty("user.dir"), "/config/registry" + date);
        File registryFile = new File(String.valueOf(registryPath));

        FileOutputStream registry = new FileOutputStream(String.valueOf(registryFile));
        ObjectOutputStream registryOutput = new ObjectOutputStream(registry);

        registryOutput.writeObject(newRegistry);
        registryOutput.flush();
        registryOutput.close();
    }

    @SneakyThrows
    public static Registry getRegistry(String date) {
        checkIfFileExists(date);
        Path registryPath = System.getProperty("os.name").contains("Windows") ? Path.of(System.getProperty("user.dir"), "\\config\\registry" + date) : Path.of(System.getProperty("user.dir"), "/config/registry" + date);
        FileInputStream registryFile = new FileInputStream(String.valueOf(registryPath));
        ObjectInputStream registryInput = new ObjectInputStream(registryFile);

        Registry registry = (Registry) registryInput.readObject();

        registryInput.close();
        registryFile.close();

        return registry;
    }

    public static boolean fileExists(String date) {
        if (System.getProperty("os.name").contains("Windows")) {
            return new File(System.getProperty("user.dir"), "\\config\\registry" + date).exists();
        } else {
            return new File(System.getProperty("user.dir"), "/config/registry" + date).exists();
        }
    }

    @SneakyThrows
    public static void checkIfFileExists(String date) {
        if (!fileExists(date)) {
            Path registryPath;
            registryPath = System.getProperty("os.name").contains("Windows") ? Path.of(System.getProperty("user.dir"), "\\config\\registry" + date) : Path.of(System.getProperty("user.dir"), "/config/registry" + date);
            File registryFile = new File(String.valueOf(registryPath));

            //noinspection ResultOfMethodCallIgnored
            registryFile.getParentFile().mkdirs();
            Registry registry = new Registry();

            FileOutputStream registryStream = new FileOutputStream(String.valueOf(registryFile));
            ObjectOutputStream registryOutput = new ObjectOutputStream(registryStream);

            registryOutput.writeObject(registry);
            registryOutput.flush();
            registryOutput.close();
        }
    }
}

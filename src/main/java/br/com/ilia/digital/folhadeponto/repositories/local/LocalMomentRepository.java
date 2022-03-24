package br.com.ilia.digital.folhadeponto.repositories.local;

import br.com.ilia.digital.folhadeponto.objects.Moment;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de Repositório de Batidas em arquivos locais
 * @since 2022-03-23 17:27
 */

@Component
public class LocalMomentRepository {

    /**
     * Função para salvar Batidas em arquivos locais
     * @since 2022-03-23 17:27
     */
    @SneakyThrows
    public static void saveMoment(Moment newMoment) {
        String date = newMoment.getYear() + "-" + newMoment.getMonth() + "-" + newMoment.getDay();

        checkIfFileExists(date);

        Path momentsPath = System.getProperty("os.name").contains("Windows") ? Path.of(System.getProperty("user.dir"), "\\config\\workDay" + date) : Path.of(System.getProperty("user.dir"), "/config/workDay" + date);
        File momentsFile = new File(String.valueOf(momentsPath));

        List<Moment> momentList = getMoments(date);
        momentList.add(newMoment);

        FileOutputStream moments = new FileOutputStream(String.valueOf(momentsFile));
        ObjectOutputStream momentsOutput = new ObjectOutputStream(moments);

        momentsOutput.writeObject(momentList);
        momentsOutput.flush();
        momentsOutput.close();
    }

    /**
     * Função para recuperar dados de Batidas em arquivos locais
     * @since 2022-03-23 17:27
     */
    @SneakyThrows
    public static List<Moment> getMoments(String date) {
        checkIfFileExists(date);
        Path momentsPath = System.getProperty("os.name").contains("Windows") ? Path.of(System.getProperty("user.dir"), "\\config\\workDay" + date) : Path.of(System.getProperty("user.dir"), "/config/workDay" + date);
        FileInputStream momentsFile = new FileInputStream(String.valueOf(momentsPath));
        ObjectInputStream momentsInput = new ObjectInputStream(momentsFile);

        List<Moment> momentList = (List<Moment>) momentsInput.readObject();

        momentsInput.close();
        momentsFile.close();

        return momentList;
    }

    /**
     * Função para verificar se arquivos locais existem e retornar boolean
     * @since 2022-03-23 17:27
     */
    public static boolean fileExists(String date) {
        if (System.getProperty("os.name").contains("Windows")) {
            return new File(System.getProperty("user.dir"), "\\config\\workDay" + date).exists();
        } else {
            return new File(System.getProperty("user.dir"), "/config/workDay" + date).exists();
        }
    }

    /**
     * Função para verificar se arquivos locais existem e criar os arquivos se não existirem
     * @since 2022-03-23 17:27
     */
    @SneakyThrows
    public static void checkIfFileExists(String date) {
        if (!fileExists(date)) {
            Path momentsPath;
            momentsPath = System.getProperty("os.name").contains("Windows") ? Path.of(System.getProperty("user.dir"), "\\config\\workDay" + date) : Path.of(System.getProperty("user.dir"), "/config/workDay" + date);
            File momentsFile = new File(String.valueOf(momentsPath));

            //noinspection ResultOfMethodCallIgnored
            momentsFile.getParentFile().mkdirs();
            List<Moment> momentList = new ArrayList<>();

            FileOutputStream moments = new FileOutputStream(String.valueOf(momentsFile));
            ObjectOutputStream momentsOutput = new ObjectOutputStream(moments);

            momentsOutput.writeObject(momentList);
            momentsOutput.flush();
            momentsOutput.close();
        }
    }

    /**
     * Função para buscar Batidas de acordo com data e hora nos arquivos locais
     * @since 2022-03-23 17:27
     */
    @SneakyThrows
    public static Moment findByDateTime(String dateTime, String date) {
        List<Moment> momentList = getMoments(date);

        for (Moment moment : momentList) {
            if (moment.getDataHora().equals(dateTime)) return moment;
        }

        return null;
    }

    /**
     * Função para buscar Lista de horários de Batidas de acordo com data e hora nos arquivos locais
     * @since 2022-03-23 17:27
     */
    public static List<String> getSchedules(String date) {
        List<Moment> momentList = getMoments(date);
        List<String> schedules = new ArrayList<>();

        for (Moment moment : momentList) {
            schedules.add(moment.getDataHora().split("T")[1]);
        }
        return schedules;
    }
}

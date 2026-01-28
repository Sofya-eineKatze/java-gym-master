package ru.yandex.practicum.gym;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.List;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        if (mondaySessions.size() != 1) {
            System.out.println("ОШИБКА");
        }
        //Проверить, что за вторник не вернулось занятий
        List<TrainingSession> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        if (!tuesdaySessions.isEmpty()) {
            System.out.println("ОШИБКА");
        }
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        if (mondaySessions.size() != 1) {
            System.out.println("ОШИБКА");
        }
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        List<TrainingSession> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        if (thursdaySessions.size() != 2) {
            System.out.println("ОШИБКА");
        } else {
            // Проверяем порядок: первое должно быть в 13:00, второе в 20:00
            if (!thursdaySessions.get(0).equals(thursdayChildTrainingSession) ||
                    !thursdaySessions.get(1).equals(thursdayAdultTrainingSession)) {
                System.out.println("ОШИБКА");
            }
        }
        // Проверить, что за вторник не вернулось занятий
        List<TrainingSession> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        if (!tuesdaySessions.isEmpty()) {
            System.out.println("ОШИБКА");
        }
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        List<TrainingSession> sessionsAt1300 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        if (sessionsAt1300.size() != 1) {
            System.out.println("ОШИБКА");
        }
        //Проверить, что за понедельник в 14:00 не вернулось занятий
        List<TrainingSession> sessionsAt1400 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        if (!sessionsAt1400.isEmpty()) {
            System.out.println("ОШИБКА");
        }
    }
    @Test
    void testGetCountByCoachesOneCoach() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Иванов", "Иван", "Иванович");
        Group group = new Group("Йога", Age.ADULT, 60);

        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(14, 0)));

        //Проверить, что вернулся один тренер с двумя тренировками
        List<Coach.TrainingStats> stats = timetable.getCountByCoaches();
        if (stats.size() != 1) {
            System.out.println("ОШИБКА");
        }
        if (stats.get(0).getTrainingCount() != 2) {
            System.out.println("ОШИБКА");
        }
    }

    @Test
    void testGetCountByCoachesMultipleCoachesSorted() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Иванов", "Иван", "Иванович");
        Coach coach2 = new Coach("Петров", "Петр", "Петрович");

        Group group = new Group("Йога", Age.ADULT, 60);

        // Тренер 1: 2 тренировки
        timetable.addNewTrainingSession(new TrainingSession(group, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1,
                DayOfWeek.WEDNESDAY, new TimeOfDay(14, 0)));

        // Тренер 2: 1 тренировка
        timetable.addNewTrainingSession(new TrainingSession(group, coach2,
                DayOfWeek.TUESDAY, new TimeOfDay(11, 0)));

        //Проверить, что вернулось два тренера в правильном порядке
        List<Coach.TrainingStats> stats = timetable.getCountByCoaches();
        if (stats.size() != 2) {
            System.out.println("ОШИБКА");
        }
        //Проверить порядок: сначала тренер с 2 тренировками, потом с 1
        if (stats.get(0).getTrainingCount() != 2 || stats.get(1).getTrainingCount() != 1) {
            System.out.println("ОШИБКА");
        }
    }

    @Test
    void testGetCountByCoachesEmpty() {
        Timetable timetable = new Timetable();

        //Проверить, что для пустого расписания возвращается пустой список
        List<Coach.TrainingStats> stats = timetable.getCountByCoaches();
        if (!stats.isEmpty()) {
            System.out.println("ОШИБКА");
        }
    }

}

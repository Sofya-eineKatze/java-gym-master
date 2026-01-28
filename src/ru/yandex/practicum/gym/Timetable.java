package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {
    private HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable;

    public Timetable() {
        timetable = new HashMap<>();
        // Инициализируем все дни недели
        for (DayOfWeek day : DayOfWeek.values()) {
            timetable.put(day, new TreeMap<>());
        }
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        // Получаем TreeMap для указанного дня
        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(day);

        // Получаем список тренировок для данного времени
        List<TrainingSession> sessions = daySchedule.get(time);

        // Если списка еще нет, создаем новый
        if (sessions == null) {
            sessions = new ArrayList<>();
            daySchedule.put(time, sessions);
        }

        // Добавляем тренировку в список для данного времени
        sessions.add(trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(dayOfWeek);
        List<TrainingSession> allSessions = new ArrayList<>();

        // Проверяем, есть ли расписание для этого дня
        if (daySchedule == null) {
            return allSessions;
        }

        // Используем navigableKeySet() для получения ключей в отсортированном порядке
        for (TimeOfDay time : daySchedule.navigableKeySet()) {
            List<TrainingSession> sessions = daySchedule.get(time);
            if (sessions != null) {
                allSessions.addAll(sessions);
            }
        }

        return allSessions;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(dayOfWeek);

        // Проверяем, есть ли расписание для этого дня
        if (daySchedule == null) {
            return new ArrayList<>();
        }

        // Получаем список тренировок для данного времени
        List<TrainingSession> sessions = daySchedule.get(timeOfDay);

        // Если списка нет, возвращаем пустой список
        if (sessions == null) {
            return new ArrayList<>();
        }

        return sessions;
    }

    public List<Coach.TrainingStats> getCountByCoaches() {
        Map<Coach, Integer> coachCounter = new HashMap<>();

        for (TreeMap<TimeOfDay, List<TrainingSession>> daySchedule : timetable.values()) {
            for (List<TrainingSession> sessions : daySchedule.values()) {
                for (TrainingSession session : sessions) {
                    Coach coach = session.getCoach();
                    coachCounter.put(coach, coachCounter.getOrDefault(coach, 0) + 1);
                }
            }
        }

        List<Coach.TrainingStats> result = new ArrayList<>();

        for (Map.Entry<Coach, Integer> entry : coachCounter.entrySet()) {
            result.add(new Coach.TrainingStats(entry.getKey(), entry.getValue()));
        }

        result.sort((c1, c2) -> c2.getTrainingCount() - c1.getTrainingCount());

        return result;
    }
}
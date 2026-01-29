package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {
    private HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable;
    private HashMap<DayOfWeek, List<TrainingSession>> allSessionsByDay;

    public Timetable() {
        timetable = new HashMap<>();
        allSessionsByDay = new HashMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            timetable.put(day, new TreeMap<>());
            allSessionsByDay.put(day, new ArrayList<>());
        }
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(day);
        List<TrainingSession> sessions = daySchedule.get(time);

        if (sessions == null) {
            sessions = new ArrayList<>();
            daySchedule.put(time, sessions);
        }

        sessions.add(trainingSession);
        allSessionsByDay.get(day).add(trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return new ArrayList<>(allSessionsByDay.get(dayOfWeek));
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
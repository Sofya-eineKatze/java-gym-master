package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {
    private final HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable;
    private final HashMap<DayOfWeek, List<TrainingSession>> allSessionsByDay;

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
        List<TrainingSession> dayList = allSessionsByDay.get(day);

        dayList.clear();

        for (List<TrainingSession> slot : daySchedule.values()) {
            dayList.addAll(slot);
        }
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return Collections.unmodifiableList(allSessionsByDay.get(dayOfWeek));
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(dayOfWeek);

        if (daySchedule == null) {
            return Collections.emptyList();
        }

        List<TrainingSession> sessions = daySchedule.get(timeOfDay);

        if (sessions == null) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(sessions);
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
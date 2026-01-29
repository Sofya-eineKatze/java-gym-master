package ru.yandex.practicum.gym;

import java.util.Objects;

public class Coach {

    //фамилия
    private String surname;
    //имя
    private String name;
    //отчество
    private String middleName;

    public Coach(String surname, String name, String middleName) {
        this.surname = surname;
        this.name = name;
        this.middleName = middleName;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public static class TrainingStats {
        private final Coach coach;
        private final int trainingCount;

        public TrainingStats(Coach coach, int trainingCount) {
            this.coach = coach;
            this.trainingCount = trainingCount;
        }

        public Coach getCoach() {
            return coach;
        }

        public int getTrainingCount() {
            return trainingCount;
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coach coach = (Coach) o;
        return Objects.equals(surname, coach.surname) &&
                Objects.equals(name, coach.name) &&
                Objects.equals(middleName, coach.middleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(surname, name, middleName);
    }
}

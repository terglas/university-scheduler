package com.example.universityscheduler.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Subject subject;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Teacher teacher;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "group_schedule",
            inverseJoinColumns = @JoinColumn(name = "schedule_id", referencedColumnName = "id"),
            joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"))
    private List<Group> groups;
    private String room;
    private Week week;
    private LocalTime startTime;
    private LocalTime endTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Schedule schedule = (Schedule) o;
        return id != null && Objects.equals(id, schedule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, room, week, startTime, endTime);
    }

    public enum Week {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY
    }
}

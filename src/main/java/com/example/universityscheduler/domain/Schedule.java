package com.example.universityscheduler.domain;

import com.example.universityscheduler.model.SessionType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@FieldNameConstants
@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private SessionType sessionType;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Subject subject;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Teacher teacher;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(name = "group_schedule",
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
            joinColumns = @JoinColumn(name = "schedule_id", referencedColumnName = "id"))
    private List<Group> groups;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Room room;
    @Enumerated(EnumType.STRING)
    private Week week;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Schedule schedule = (Schedule) o;
        return id != null && Objects.equals(id, schedule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, week, startTime, endTime);
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

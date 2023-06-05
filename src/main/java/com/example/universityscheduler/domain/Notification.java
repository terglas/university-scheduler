package com.example.universityscheduler.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@FieldNameConstants
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String deviceToken;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(name = "notification_group",
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
            joinColumns = @JoinColumn(name = "notification_id", referencedColumnName = "id"))
    private List<Group> groups;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Notification that = (Notification) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, deviceToken);
    }
}

package org.example.taskflow.domain.activity.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.taskflow.common.entity.BaseEntity;

@Entity
@Table(name = "activity_types")
@Getter
@NoArgsConstructor
public class ActivityType extends BaseEntity {

    @Column(name = "type_code", unique = true, nullable = false, length = 50)
    private String typeCode;

    @Column(name = "description", nullable = false)
    private String description;

}

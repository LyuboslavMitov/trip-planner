package com.lubo.trip.tripplanner.model;

import lombok.*;
import org.apache.tomcat.jni.Local;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "trips")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Trip {
    @Id
    private String id;
    @NonNull
    @NotNull
    private String name;
    @NonNull
    @NotNull
    private String destination;

    //TODO: Json format on those??
    @NonNull
    @NotNull
    private LocalDateTime startDate;
    @NonNull
    @NotNull
    private LocalDateTime endDate;

    private String description;

    //TODO: Potential functionality
    private Boolean isPublic=false;

    //Relations
    @NonNull
    @NotNull
    private String ownerId;

    @NonNull
    @NotNull
    private List<String> participantsNames;


    @NonNull
    @NotNull
    private List<String> participantsId;

}

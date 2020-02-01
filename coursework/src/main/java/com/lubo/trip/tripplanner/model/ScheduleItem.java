package com.lubo.trip.tripplanner.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "schedule-items")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class ScheduleItem {
    @Id
    private String id;
    @NonNull
    @NotNull
    private String name;
    @NonNull
    @NotNull
    private String location;
    @NonNull
    @NotNull
    private LocalDateTime date;

    private Double duration;
    private String description;
    private Integer dayOfTrip;

    //Relations
    @NonNull
    @NotNull
    private String tripId;

    @NonNull
    @NotNull
    private List<String> participantsId;

}

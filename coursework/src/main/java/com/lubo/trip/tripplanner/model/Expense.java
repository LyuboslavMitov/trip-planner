package com.lubo.trip.tripplanner.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "expenses")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Expense {
    @Id
    private String id;
    @NonNull
    @NotNull
    private String name;
    @NonNull
    @NotNull
    private BigDecimal amount;

    @NonNull
    @NotNull
    private LocalDateTime dateOfExpense;
    private String description;


    //Relations
    @NonNull
    @NotNull
    private String tripId;

    @NonNull
    @NotNull
    private String username;
}

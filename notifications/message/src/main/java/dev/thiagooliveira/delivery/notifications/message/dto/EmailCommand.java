package dev.thiagooliveira.delivery.notifications.message.dto;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class EmailCommand implements Serializable {
    @NotNull
    private final String emailTo;

    @NotNull
    private final String title;

    @NotNull
    private final String content;
}

package entelect.training.incubator.spring.notification.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto implements Serializable {
//    {
//        "phoneNumber": "+27801234567",
//            "message": "Molo Air: Confirming flight ABS123 booked for Jane Doe on 05-06-2021."
//    }
    private String phoneNumber;
    private String message;
}

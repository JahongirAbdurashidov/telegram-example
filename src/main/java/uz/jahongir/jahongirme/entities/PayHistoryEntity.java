package uz.jahongir.jahongirme.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.jahongir.jahongirme.enums.ChatState;
import uz.jahongir.jahongirme.enums.Locale;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pay_histories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PayHistoryEntity extends AbstractEntity {

    @JoinColumn(name = "chat_id")
    @ManyToOne
    private ChatEntity chat;

    @Column(name = "title")
    private String title;

    @Column(name = "sum")
    private Long sum;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "data", columnDefinition = "text")
    private String data;
}

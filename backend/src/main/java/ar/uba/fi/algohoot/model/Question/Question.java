package ar.uba.fi.algohoot.model.Question;

import java.util.*;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "questions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String topic;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private QuestionType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "scoring_mode", nullable = false, length = 50)
    private ScoringMode scoringMode;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String statement;
    
    @Column(name = "correct_answer", nullable = false, columnDefinition = "TEXT")
    private String correctAnswer;
    
    @Column(name = "explanation_text", columnDefinition = "TEXT")
    private String explanationText;
    
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("order ASC")
    @Builder.Default
    private List<QuestionOption> options = new ArrayList<>();
    
    public void addOption(QuestionOption option) {
        options.add(option);
        option.setQuestion(this);
    }
}

package com.example.firstproject.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;

@Entity // DB가 해당 객체를 인식가능 (해당 클래스로 XP)
@AllArgsConstructor
@ToString
@NoArgsConstructor // 디폴트 생성자를 추가
@Getter
public class Article {

    @Id // 대표값 지정. like 주민등록번호
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성 전략
    private Long id;

    @Column
    private String title;

    @Column
    private String content;


    public void patch(Article article) {
        if(article.title !=null);
            this.title = article.title;
        if(article.title !=null);
            this.content = article.content;

    }
}



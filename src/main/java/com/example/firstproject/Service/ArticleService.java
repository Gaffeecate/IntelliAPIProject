package com.example.firstproject.Service;
import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;


    public List<Article> index() {
        return (List<Article>) articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();

//        // 사용자가 전달해준 값에 아티클 아이디가 포함되어 있다면
//        if (article.getId() != null) {
//            return null;
//        }
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        // 1: DTO -> 엔티티
        Article article = dto.toEntity();
        log.info("id: {}, article: {}", id, article.toString());

        // 2: 타겟 조회
        Article target = articleRepository.findById(id).orElse(null);

        // 3: 잘못된 요청 처리
        if (target == null || id != article.getId()) {
            // 400, 잘못된 요청 응답!
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
            return null;
        }

        // 4: 업데이트 및 정상 응답(200)
        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;
    }


    public Article delete(Long id) {
        // 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);

        // 잘못된 요청 처리
        if (target == null) {
            return null;
        }
        // 대상 삭제
        articleRepository.delete(target);
        return target;
    }


    @Transactional// 해당 메소드를 트랜젝션을 묶는다. 메소드가 시행되기 전으로 롤백된다.

    public List<Article> createArticles(List<ArticleForm> dtos) {
        // dto 묶음을 entity 묶음으로 변환
        List<Article> articleList = dtos.stream()
                        .map(dto -> dto.toEntity())
                        .collect(Collectors.toList());
        // entity 묶음을 DB로 저장
        articleList.stream()
                .forEach(article -> articleRepository.save(article));



        // 결과값 반환
        return articleList;
    }

}

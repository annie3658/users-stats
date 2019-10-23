package com.library.user.stats.application.service;

import ch.qos.logback.classic.Logger;
import com.library.user.stats.application.config.BooksConfig;
import com.library.user.stats.application.dto.BookDTO;
import com.library.user.stats.application.dto.CoverDTO;
import com.library.user.stats.application.dto.UserStatsDTO;
import com.library.user.stats.application.entity.UserStats;
import com.library.user.stats.application.repository.UserStatsRepository;
import com.library.user.stats.application.util.DTOUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class UserStatsService {

    private UserStatsRepository repository;
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(UserStatsService.class);
    private DTOUtil dtoUtil = new DTOUtil();
    private WebClient.Builder webClientBuilder;
    private BooksConfig config;
    @Autowired
    public UserStatsService(UserStatsRepository repository, BooksConfig config, WebClient.Builder webClientBuilder) {
        this.repository = repository;
        this.config = config;
        this.webClientBuilder = webClientBuilder;
    }

    private UserStats findById(String id){
        Optional<UserStats> userStats = repository.findById(id);
        if (userStats.isPresent()) {
            return userStats.get();
        }
        LOGGER.warn("The UserStats with id {} was not found", id);
        return null;
    }

    public List <UserStatsDTO> findUserStatsByUserId(String id){
        List <UserStats> stats = repository.findUserStatsByUserId(id);
        if(stats.isEmpty()){
            LOGGER.warn("Stats with user id {} {} were not found", id);
        }
        return stats.stream()
                .map(stat -> dtoUtil.userStatsToDTO(stat))
                .collect(Collectors.toList());
    }

    public List <UserStatsDTO> findUserStatsByBookId(String id){
        List <UserStats> stats = repository.findUserStatsByBookId(id);
        if(stats.isEmpty()){
            LOGGER.warn("Stats with book id {} {} were not found", id);
        }
        return stats.stream()
                .map(stat -> dtoUtil.userStatsToDTO(stat))
                .collect(Collectors.toList());
    }

    public List<UserStatsDTO> getAll(){
        List<UserStats> userStats = repository.findAll();
        if(userStats.isEmpty()){
            LOGGER.warn("The userStats list is empty");
        }
        return userStats.stream()
                .map(cover -> dtoUtil.userStatsToDTO(cover))
                .collect(Collectors.toList());
    }

    public UserStatsDTO insert(UserStatsDTO userStatsDTO){

        return dtoUtil.userStatsToDTO(repository.insert(dtoUtil.dtoToUserStats(userStatsDTO)));
    }

    public UserStatsDTO update(UserStatsDTO userStatsDTO){

        return dtoUtil.userStatsToDTO(repository.save(dtoUtil.dtoToUserStats(userStatsDTO)));
    }

    public void delete(String id){
        LOGGER.info("Deleted UserStats with id: " + id);
        repository.deleteById(id);

    }

    private Double updateBookRating(String id){
        List <UserStatsDTO> stats = findUserStatsByBookId(id);

       return (stats.stream()
                .map(x -> x.getRating().getValue())
                .reduce(0d, Double::sum))/stats.size();
    }

    public BookDTO getBookById(String id){
        Double newRating = updateBookRating(id);

        BookDTO book = webClientBuilder.build()
                .get()
                .uri(config.getUrl() + id)
                .retrieve()
                .bodyToMono(BookDTO.class)
                .switchIfEmpty(Mono.empty())
                .block();

        book.setRating(newRating);

        WebClient.RequestBodySpec uri = webClientBuilder.build()
                .post()
                .uri(config.getBook())
                .accept(APPLICATION_JSON);

        BodyInserter<BookDTO, ReactiveHttpOutputMessage> inserter
                = BodyInserters.fromObject(book);

        WebClient.ResponseSpec response = uri
                .body(inserter)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .acceptCharset(Charset.forName("UTF-8"))
                .ifNoneMatch("*")
                .ifModifiedSince(ZonedDateTime.now())
                .retrieve();

        //return response.bodyToMono(BookDTO.class).block();
        return book;
    }

}

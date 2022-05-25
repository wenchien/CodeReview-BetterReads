package io.jdevelop.restcontrollers;

import java.util.List;
import java.util.stream.Collectors;

import com.datastax.oss.driver.shaded.guava.common.base.Strings;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import io.jdevelop.beans.SearchResult;
import io.jdevelop.beans.SearchResultBook;
import reactor.core.publisher.Mono;

@Controller
public class SearchController {

    private final WebClient webClient;

    @Value("${external.url.bookCover}")
    private String COVER_SEARCH_URL;// move this

    // Make this static and just do eager initialization / static initialization
    public SearchController(WebClient.Builder webClientBuilder) {
        this.webClient =
        webClientBuilder
            .exchangeStrategies(
                ExchangeStrategies.builder()
                .codecs(configurer -> configurer
                .defaultCodecs()
                .maxInMemorySize(16 * 1024 * 1024)).build())
            .baseUrl("http://openlibrary.org/search.json")
            .build();
    }

    @GetMapping(value = "/search")
    public String getSearchResults(@RequestParam String query, Model model) {
        Mono<SearchResult> searchResultMono = this.webClient.get()
            .uri("?q={query}", query)
            .retrieve()
            .bodyToMono(SearchResult.class);

        SearchResult searchResultMonoBlock = searchResultMono.block();

        // extract to private method
        List<SearchResultBook> limitedSearchResults = searchResultMonoBlock.getDocs()
            .stream()
            .limit(10) // move to config
            .map(bookResult -> {
                bookResult.setKey(bookResult.getKey().replace("/works/", ""));
                String coverId = bookResult.getCover_i();
                if (!Strings.isNullOrEmpty(coverId)) {
                    coverId = COVER_SEARCH_URL + coverId + "-M.jpg";
                } else {
                    coverId = "/images/img_not_found.webp";
                }
                bookResult.setCover_i(coverId);
                return bookResult;
            })
            .collect(Collectors.toList());

        model.addAttribute("searchResults", limitedSearchResults);

        return "search";
    }

}

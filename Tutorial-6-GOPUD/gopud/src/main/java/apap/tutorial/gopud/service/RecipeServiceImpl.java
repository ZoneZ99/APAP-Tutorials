package apap.tutorial.gopud.service;

import apap.tutorial.gopud.rest.RecipeResponse;
import apap.tutorial.gopud.rest.Setting;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final WebClient webClientSpoonacular;

    public RecipeServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientSpoonacular = webClientBuilder.baseUrl(Setting.spoonacularUrl).build();
    }

    @Override
    public Mono<RecipeResponse> retrieveRecipesWithExcludedIngredient(String excludedIngredient) {
        return webClientSpoonacular.get()
                .uri("/recipes/search?apiKey=" + System.getenv("SPOONACULAR_APIKEY")
                        + "&excludeIngredients=" + excludedIngredient)
                .retrieve()
                .bodyToMono(RecipeResponse.class);
    }
}

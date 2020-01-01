package apap.tutorial.gopud.service;

import apap.tutorial.gopud.rest.RecipeResponse;
import reactor.core.publisher.Mono;

public interface RecipeService {

    Mono<RecipeResponse> retrieveRecipesWithExcludedIngredient(String excludedIngredient);
}

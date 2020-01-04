package apap.tutorial.gopud.restcontroller;


import apap.tutorial.gopud.rest.RecipeResponse;
import apap.tutorial.gopud.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class RecipeRestController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping(value = "/recipes")
    private Mono<RecipeResponse> retrieveRecipes(
            @RequestParam(value = "excludeIngredient")
                    String excludedIngredient) {

        return recipeService.retrieveRecipesWithExcludedIngredient(excludedIngredient);
    }
}

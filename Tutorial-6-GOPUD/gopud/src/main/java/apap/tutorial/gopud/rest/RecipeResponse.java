package apap.tutorial.gopud.rest;

import java.io.Serializable;
import java.util.List;

public class RecipeResponse implements Serializable {

    private List<RecipeDetail> results;
    private String baseUri;
    private Integer offset;
    private Integer number;
    private Long totalResults;
    private Integer processingTimeMs;
    private Long expires;
    private Boolean isStale;

    public RecipeResponse() {}

    public RecipeResponse(List<RecipeDetail> results, String baseUri, Integer offset, Integer number,
                          Long totalResults, Integer processingTimeMs, Long expires, Boolean isStale) {
        this.results = results;
        this.baseUri = baseUri;
        this.offset = offset;
        this.number = number;
        this.totalResults = totalResults;
        this.processingTimeMs = processingTimeMs;
        this.expires = expires;
        this.isStale = isStale;
    }

    public List<RecipeDetail> getResults() {
        return results;
    }

    public void setResults(List<RecipeDetail> results) {
        this.results = results;
    }

    public String getBaseUri() {
        return baseUri;
    }

    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getProcessingTimeMs() {
        return processingTimeMs;
    }

    public void setProcessingTimeMs(Integer processingTimeMs) {
        this.processingTimeMs = processingTimeMs;
    }

    public Long getExpires() {
        return expires;
    }

    public void setExpires(Long expires) {
        this.expires = expires;
    }

    public Boolean getStale() {
        return isStale;
    }

    public void setStale(Boolean stale) {
        isStale = stale;
    }
}

package org.openapitools.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "")
public class PagedCocktailLightResponse  {
  
  @SerializedName("hasNext")
  private Boolean hasNext = null;
  @SerializedName("nextStart")
  private Integer nextStart = null;
  @SerializedName("cocktails")
  private List<CocktailLightDTO> cocktails = null;

  /**
   **/
  @ApiModelProperty(value = "")
  public Boolean getHasNext() {
    return hasNext;
  }
  public void setHasNext(Boolean hasNext) {
    this.hasNext = hasNext;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public Integer getNextStart() {
    return nextStart;
  }
  public void setNextStart(Integer nextStart) {
    this.nextStart = nextStart;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public List<CocktailLightDTO> getCocktails() {
    return cocktails;
  }
  public void setCocktails(List<CocktailLightDTO> cocktails) {
    this.cocktails = cocktails;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PagedCocktailLightResponse pagedCocktailLightResponse = (PagedCocktailLightResponse) o;
    return (this.hasNext == null ? pagedCocktailLightResponse.hasNext == null : this.hasNext.equals(pagedCocktailLightResponse.hasNext)) &&
        (this.nextStart == null ? pagedCocktailLightResponse.nextStart == null : this.nextStart.equals(pagedCocktailLightResponse.nextStart)) &&
        (this.cocktails == null ? pagedCocktailLightResponse.cocktails == null : this.cocktails.equals(pagedCocktailLightResponse.cocktails));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (this.hasNext == null ? 0: this.hasNext.hashCode());
    result = 31 * result + (this.nextStart == null ? 0: this.nextStart.hashCode());
    result = 31 * result + (this.cocktails == null ? 0: this.cocktails.hashCode());
    return result;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class PagedCocktailLightResponse {\n");
    
    sb.append("  hasNext: ").append(hasNext).append("\n");
    sb.append("  nextStart: ").append(nextStart).append("\n");
    sb.append("  cocktails: ").append(cocktails).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}

package com.ctc.demo.emulator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class APIResponse {

	   @Schema(required = true, description = "Return Result")

	   private String returnResult = null;


	   @JsonProperty("return_result")
	   public String getReturnResult() {
	     return returnResult;
	   }

	   public void setReturnDesc(String returnResult) {
	     this.returnResult = returnResult;
	   }


	   @Override
	   public String toString() {
	     StringBuilder sb = new StringBuilder();
	     sb.append("class APIResponse {\n");
	     sb.append("    returnResult: ").append(toIndentedString(returnResult)).append("\n");
	     sb.append("}");
	     return sb.toString();
	   }

	   /**
	    * Convert the given object to string with each line indented by 4 spaces
	    * (except the first line).
	    */
	   private static String toIndentedString(java.lang.Object o) {
	     if (o == null) {
	       return "null";
	     }
	     return o.toString().replace("\n", "\n    ");
	   }
}

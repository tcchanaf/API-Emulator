package com.ctc.demo.emulator.client.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.ctc.demo.emulator.model.APIRequest;
import com.ctc.demo.emulator.model.APIResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Path("/")
public interface TestRequestToClientApi  {

    /**
     * Test Client API
     *
     */
    @POST
    @Path("/testClientApi")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @Operation(summary = "Test Client API", tags={  })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "simple", content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))) })
    public APIResponse testClientApi(APIRequest request);

}
